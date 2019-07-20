package minic.dto;

public class ConfigParamsDto {
    public int x_cells_count;
    public int y_cells_count;
    public int speed;
    public int width;

//        "params": {
//        "x_cells_count": 31,
//                "y_cells_count": 31,
//                "speed": 5,
//                "width": 30


    @Override
    public String toString() {
        return "minic.dto.ConfigParamsDto{" +
                "x_cells_count=" + x_cells_count +
                ", y_cells_count=" + y_cells_count +
                ", speed=" + speed +
                ", width=" + width +
                '}';
    }
}
