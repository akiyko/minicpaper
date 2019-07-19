import java.util.Map;
import java.util.function.Consumer;

public class GameState {
    public Cell[][] cells;
    public ConfigDto configDto;

    public static GameState fromTick(TickDto tickDto, ConfigDto configDto) {
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

        addPlayers(tickDto, gameState);
        addBonuses(tickDto, gameState);

        return gameState;
    }

    static void addPlayers(TickDto tickDto, GameState gameState) {
        for (Map.Entry<String, PlayerDto> playerDtoEntry : tickDto.params.players.entrySet()) {
            int playerNum = Converter.playerNum(playerDtoEntry.getKey());

            gameState.updateCell(playerDtoEntry.getValue().position, c-> {
                c.playernum = playerNum;
                c.playerDirection = playerDtoEntry.getValue().direction;
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

    @Override
    public GameState clone()  {
        GameState gameState = new GameState();
        gameState.configDto = this.configDto;
        for (int i = 0; i < gameState.cells.length; i++) {
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
