import java.util.Arrays;

public class PlayerBonusDto {
    public BonusType type;
    public int[] position;

    @Override
    public String toString() {
        return "PlayerBonusDto{" +
                "type=" + type +
                ", position=" + Arrays.toString(position) +
                '}';
    }
}
