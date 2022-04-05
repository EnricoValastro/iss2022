package it.unibo.radarSystem22.domain.concrete;


import java.io.IOException;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.models.LedModel;

public class LedConcrete extends LedModel implements ILed {
	
	private Runtime rt  = Runtime.getRuntime();
	
	@Override
	protected void ledAction(boolean state) {
		try {
			if(state) {
				System.out.println("LedConcrete |	TurnOn Led");
				rt.exec("sudo bash led25GpioTurnOn.sh");
			}
			else {
				System.out.println("LedConcrete |	TurnOff Led");
				rt.exec("sudo bash led25GpioTurnOff.sh");
			}
		}catch(IOException e) {
			System.out.println("LedConcrete | ERROR " +  e.getMessage());
		}
		
		
		
	}


}
