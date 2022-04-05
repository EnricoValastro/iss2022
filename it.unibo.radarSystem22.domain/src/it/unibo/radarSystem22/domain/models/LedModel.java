package it.unibo.radarSystem22.domain.models;

import it.unibo.radarSystem22.domain.concrete.LedConcrete;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.mock.LedMock;
import it.unibo.radarSystem22.domain.mock.LedMockWithGui;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;

public abstract class  LedModel implements ILed {
	private boolean state;
	
	//factory
	public static ILed create() {	
		ILed led;
		if(DomainSystemConfig.simulation) {
			led = createMock();
		}
		else{
			led = createConcrete();
		}
		
		return led;
	}
	
	private static ILed createMock() {
		System.out.println("LedModel	|	LedMock : created");
		if(DomainSystemConfig.ledGui)
			return LedMockWithGui.createLed();
		else
			return new LedMock();
	}
	private static ILed createConcrete() {
		System.out.println("LedModel	|	LedConcrete : created");
		return new LedConcrete();
	}
	
	
	@Override
	public void turnOn() {
		state = true;
		ledAction(state);
	}
	@Override
	public void turnOff() {
		state = false;
		ledAction(state);
	}
	@Override
	public boolean getState() {
		return state;
	}

	protected abstract void ledAction(boolean state);
	
}
