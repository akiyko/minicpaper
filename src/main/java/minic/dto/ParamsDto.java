package minic.dto;

import java.util.List;
import java.util.Map;


public class ParamsDto {
    public Map<String, PlayerDto> players;

    public List<MapBonusDto> bonuses;

    public int tick_num;

    @Override
    public String toString() {
        return "minic.dto.ParamsDto{" +
                "players=" + players +
                ", bonuses=" + bonuses +
                ", tick_num=" + tick_num +
                '}';
    }
}
