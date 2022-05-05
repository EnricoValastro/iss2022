package it.unibo.radarSystem22_s4.appl.usecases;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22_s4.appl.RadarSystemConfig;

public class LedUsecase {
    public static void doUseCase(ILed led, IDistance d) {
        try {
            if( d.getVal() < RadarSystemConfig.DLIMIT )
                led.turnOn();
            else
                led.turnOff();
        } catch (Exception e) {
            System.out.println("LedUsecase   |   Error: " + e.getMessage() );
        }
    }
}
