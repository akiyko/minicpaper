package minic.simulate;

import minic.dto.ConfigDto;

public class Speed {

    public Speed(int defaultSpeed, int currentSpeed, int bonusTicks) {
        this.defaultSpeed = defaultSpeed;
        this.currentSpeed = currentSpeed;
        this.bonusTicks = bonusTicks;
    }

    public static Speed defaultNormalSpeed(ConfigDto configDto) {
        return new Speed(configDto.params.speed, configDto.params.speed, 0);
    }


    public final int defaultSpeed;
    public final int currentSpeed ;
    public final int bonusTicks;

    public int getSpeed(int microTick) {
        return microTick > bonusTicks
                ? defaultSpeed
                : currentSpeed;
    }

}
