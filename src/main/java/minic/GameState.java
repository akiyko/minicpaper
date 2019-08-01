package minic;

import minic.dto.*;
import minic.simulate.FillResult;
import minic.simulate.FollowTraceResult;

import java.util.*;
import java.util.function.Consumer;

public class GameState {
    public Cell[][] cells;
    public ConfigDto configDto;
    public final Map<Integer, Position> playersInitialPos = new HashMap<>();
    public final Map<Integer, Integer> playersTraceLen = new HashMap<>();

    public static GameState emptyField(ConfigDto configDto) {
        GameState gameState = new GameState();
        gameState.cells = new Cell[configDto.params.x_cells_count][];
        gameState.configDto = configDto;

        for (int i = 0; i < gameState.cells.length; i++) {
            gameState.cells[i] = new Cell[configDto.params.y_cells_count];
        }

        for (int i = 0; i < gameState.cells.length; i++) {
            for (int j = 0; j < gameState.cells[i].length; j++) {
                gameState.cells[i][j] = Cell.empty();
            }
        }

        return gameState;
    }

    public void computeTraceLens() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {

                if(cells[i][j].tracePlayerNum >=0 ) {
                    playersTraceLen.putIfAbsent(cells[i][j].tracePlayerNum, 0);
                    playersTraceLen.put(cells[i][j].tracePlayerNum, playersTraceLen.get(cells[i][j].tracePlayerNum) + 1);
                }
            }
        }
    }

    public GameState withPlayer(ConfigDto configDto,
                                int playerNum,
                                Position[] territory,
                                Position[] trace,
                                Position playerPosition,
                                Direction playerDirection) {
        for (Position t : territory) {
            updateCell(t, c -> {
                c.terrPlayerNum = playerNum;
            });
        }
        for (Position t : trace) {
            updateCell(t, c -> {
                c.tracePlayerNum = playerNum;
            });
        }
        updateCell(playerPosition, c -> {
            c.playerDirection = playerDirection;
            c.playernum = playerNum;
        });

        playersInitialPos.put(playerNum, playerPosition);

        return this;
    }

    public static GameState fromTick(TickDto tickDto, ConfigDto configDto) {
        GameState gameState = emptyField(configDto);

        addPlayers(tickDto, gameState);
        addBonuses(tickDto, gameState);

        gameState.computeTraceLens();

        return gameState;
    }

    static void addPlayers(TickDto tickDto, GameState gameState) {
        for (Map.Entry<String, PlayerDto> playerDtoEntry : tickDto.params.players.entrySet()) {
            int playerNum = Converter.playerNum(playerDtoEntry.getKey());

            gameState.updateCell(playerDtoEntry.getValue().position, c -> {
                c.playernum = playerNum;
                c.playerDirection = playerDtoEntry.getValue().direction;
                if(c.playerDirection == null || c.playerDirection == Direction.none) {
                    c.playerDirection = Direction.randomNotNone();//now easier to do like this
                }

                c.realXy = playerDtoEntry.getValue().position;
            });

            for (int[] terr : playerDtoEntry.getValue().territory) {
                gameState.updateCell(terr, c -> {
                    c.terrPlayerNum = playerNum;
                });
            }

            for (int[] traceTerr : playerDtoEntry.getValue().lines) {
                gameState.updateCell(traceTerr, c -> {
                    c.tracePlayerNum = playerNum;
                });
            }

            gameState.playersInitialPos.put(playerNum, gameState.findPlayer(playerNum).get());
        }
    }


    static void addBonuses(TickDto tickDto, GameState gameState) {
        for (MapBonusDto bonus : tickDto.params.bonuses) {
            gameState.updateCell(bonus.position, c -> {
                c.bonus = bonus.type;
            });
        }
    }


    public void updateCell(int[] position, Consumer<Cell> updateFunction) {
        Position cp = Converter.xyToij(position);
        updateFunction.accept(cells[cp.i][cp.j]);
    }

    public void updateCell(Position cp, Consumer<Cell> updateFunction) {
        updateFunction.accept(cells[cp.i][cp.j]);
    }

    public Cell at(Position cp) {
        return cells[cp.i][cp.j];
    }

    public Optional<Position> findPlayer(int playerNum) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].playernum == playerNum) {
                    return Optional.of(Position.of(i, j));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Direction> playerDirection(int playerNum) {
        return findPlayer(playerNum).map(pos -> at(pos).playerDirection);
    }

    public Set<Position> findEnclosed(Position beforeClose, int playerNum) {
        FollowTraceResult ftr = followTraceStartingFrom(beforeClose, playerNum);
        Set<Position> enclosed = new HashSet<>();

        if(!ftr.traceCells.isEmpty()) {
            enclosed.addAll(ftr.traceCells);//trace will be always ours
            Set<Position> notCovered = new HashSet<>(ftr.neighborNonTerritoryCells);
            while(!notCovered.isEmpty()) {
                FillResult fr = fillStartFrom(notCovered.iterator().next(), playerNum);
                if(fr.enclosed) {
                    enclosed.addAll(fr.filledPositions);
                }
                notCovered.removeAll(fr.filledPositions);
            }
        }

        return enclosed;
    }


    public FillResult fillStartFrom(Position startPosition, int playerNum) {
        FillResult fillResult = new FillResult();

        Set<Position> thisIterationFilledPositions = new HashSet<>();
        thisIterationFilledPositions.add(startPosition);
        List<Position> nextIterationFilledCells = new ArrayList<>();
        while (fillResult.enclosed && !thisIterationFilledPositions.isEmpty()) {
            for (Position tracePosition : thisIterationFilledPositions) {
                List<Position> neighborCells = tracePosition.neighboors();
                if(neighborCells.size() < 4) {
                    fillResult.enclosed = false;
                    break;
                }
                for (Position neighboorPosition : neighborCells) {
                    if (!fillResult.filledPositions.contains(neighboorPosition)) {
                        if (at(neighboorPosition).terrPlayerNum != playerNum
                                && at(neighboorPosition).tracePlayerNum != playerNum) {
                            nextIterationFilledCells.add(neighboorPosition);
                        }
                    }
                }

            }
            fillResult.filledPositions.addAll(thisIterationFilledPositions);
            thisIterationFilledPositions.clear();
            thisIterationFilledPositions.addAll(nextIterationFilledCells);
            nextIterationFilledCells.clear();
        }

        return fillResult;
    }

    public FollowTraceResult followTraceStartingFrom(Position lastTracePoint, int playerNum) {
        FollowTraceResult ftr = new FollowTraceResult();

        //check correct position passed
        if (at(lastTracePoint).tracePlayerNum != playerNum) {
            return ftr;
        }

        Set<Position> thisIterationTracePositions = new HashSet<>();
        thisIterationTracePositions.add(lastTracePoint);
        List<Position> nextIterationTraceCells = new ArrayList<>();
        while (!thisIterationTracePositions.isEmpty()) {
            for (Position tracePosition : thisIterationTracePositions) {
                for (Position neighboorPosition : tracePosition.neighboors()) {
                    if (!ftr.traceCells.contains(neighboorPosition)) {
                        if (at(neighboorPosition).tracePlayerNum == playerNum) {//trace
                            nextIterationTraceCells.add(neighboorPosition);
                        } else if (at(neighboorPosition).terrPlayerNum != playerNum //cell candidate to fill
                                && at(neighboorPosition).playernum != playerNum) {
                            ftr.neighborNonTerritoryCells.add(neighboorPosition);
                        }
                    }
                }

            }
            ftr.traceCells.addAll(thisIterationTracePositions);
            thisIterationTracePositions.clear();
            thisIterationTracePositions.addAll(nextIterationTraceCells);
            nextIterationTraceCells.clear();
        }

        return ftr;
    }

    @Override
    public GameState clone() {
        GameState gameState = new GameState();
        gameState.configDto = this.configDto;
        gameState.cells = new Cell[configDto.params.x_cells_count][];
        gameState.playersInitialPos.putAll(playersInitialPos);
        gameState.playersTraceLen.putAll(playersTraceLen);

        for (int i = 0; i < cells.length; i++) {
            gameState.cells[i] = new Cell[configDto.params.y_cells_count];
        }

        for (int i = 0; i < gameState.cells.length; i++) {
            for (int j = 0; j < gameState.cells[i].length; j++) {
                gameState.cells[i][j] = cells[i][j].clone();
            }
        }

        return gameState;
    }
}
