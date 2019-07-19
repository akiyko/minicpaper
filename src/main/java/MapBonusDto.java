import java.util.Arrays;

public class MapBonusDto {
    public BonusType type;
    public int[] position;

    @Override
    public String toString() {
        return "MapBonusDto{" +
                "type=" + type +
                ", position=" + Arrays.toString(position) +
                '}';
    }
}
