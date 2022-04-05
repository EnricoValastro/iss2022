package it.unibo.radarSystem22.sprint1.usecases;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.utility.RadarSystemConfig;

public class LedUsecase {
	
	public static void doUsecase(ILed led, IDistance d) {
		
		int distance = d.getVal();
		if(distance <= RadarSystemConfig.DLIMIT)
			led.turnOn();
		else
			led.turnOff();
	}
}
