//"type": "tick",
//        "params": {
//        "players": {
//        "2": {
//        "score": 0,
//        "direction": "left",
//        "territory": [

import java.util.List;

public class TickDto {
    public String type;

    public ParamsDto params;

    @Override
    public String toString() {
        return "TickDto{" +
                "type='" + type + '\'' +
                ", params=" + params +
                '}';
    }
}
