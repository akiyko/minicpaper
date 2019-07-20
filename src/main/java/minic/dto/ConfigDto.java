package minic.dto;

public class ConfigDto {
    public String type;
    public ConfigParamsDto params;
//      "type": "start_game",
//              "params": {
//        "x_cells_count": 31,
//                "y_cells_count": 31,
//                "speed": 5,
//                "width": 30


    @Override
    public String toString() {
        return "minic.dto.ConfigDto{" +
                "type='" + type + '\'' +
                ", params=" + params +
                '}';
    }
}
