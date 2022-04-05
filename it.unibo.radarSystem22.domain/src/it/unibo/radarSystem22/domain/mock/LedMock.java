package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.interfaces.*;
import it.unibo.radarSystem22.domain.models.LedModel;

public class LedMock extends LedModel implements ILed{

	@Override
	protected void ledAction(boolean state) {
		if(state) {
			System.out.println("LedMock	|	TurnOn Led");
		}
		else {
			System.out.println("LedMock	|	TurnOff Led");
		}
		
		
	}
	

}
