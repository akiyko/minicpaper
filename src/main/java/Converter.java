/**
 * @author akiyko
 * @since 7/19/2019.
 */
public class Converter {
    public static ConfigParamsDto configParamsDto;

    static {
        //default
        configParamsDto = new ConfigParamsDto();
        configParamsDto.x_cells_count = 31;
        configParamsDto.y_cells_count = 31;
        configParamsDto.speed = 5;
        configParamsDto.width = 30;
//        "x_cells_count": 31,
//                "y_cells_count": 31,
//                "speed": 5,
//                "width": 30

    }


    public static int xToI(int x) {
        return x / configParamsDto.width;
    }

    public static int yToJ(int y) {
        return y / configParamsDto.width;
    }

    public static Position xyToij(int[] position) {
        return Position.of(xToI(position[0]), yToJ(position[1]));
    }

    public static int playerNum(String key) {
        switch (key) {
            case "i":
                return 0;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
        }
        System.err.println("Player key undefined:" + key);

        return -1;
    }

}
