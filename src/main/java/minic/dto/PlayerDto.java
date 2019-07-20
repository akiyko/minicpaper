package minic.dto;

import java.util.Arrays;
import java.util.List;


public class PlayerDto {
    public int score;
    public Direction direction;

    public int[][] territory;

    public int[][] lines;
    public int[] position;
    public List<PlayerBonusDto> bonuses;


    @Override
    public String toString() {
        return "minic.dto.PlayerDto{" +
                "score=" + score +
                ", direction=" + direction +
                ", territory=" + Arrays.toString(territory) +
                ", lines=" + Arrays.toString(lines) +
                ", position=" + Arrays.toString(position) +
                ", bonuses=" + bonuses +
                '}';
    }
}
