package minic.dto;//"type": "tick",
//        "params": {
//        "players": {
//        "2": {
//        "score": 0,
//        "direction": "left",
//        "territory": [

public class TickDto {
    public String type;

    public ParamsDto params;

    @Override
    public String toString() {
        return "minic.dto.TickDto{" +
                "type='" + type + '\'' +
                ", params=" + params +
                '}';
    }
}
