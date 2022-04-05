package it.unibo.radarSystem22.domain;

import static org.junit.Assert.assertTrue;
import org.junit.*;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.utility.BasicUtility;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;

public class TestLed {
	
	@Test
	public void testLedMock(){
		
		System.out.println("TestLedMock | Up");
		DomainSystemConfig.simulation = true;
		DomainSystemConfig.ledGui = true;
		
		ILed led = ComponentFactory.createLed();
		assertTrue(!led.getState());
		
		led.turnOn();
		assertTrue(led.getState());
		BasicUtility.delay(2000);
		led.turnOff();
		assertTrue(!led.getState());
		BasicUtility.delay(2000);
	
		
	}
	
}
