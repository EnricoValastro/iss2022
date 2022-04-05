package it.unibo.radarSystem22.sprint2a.handler;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;
import it.unibo.radarSystem22.domain.interfaces.ILed;

public class LedApplHandler extends ApplMsgHandler {

	private ILed led;

	public LedApplHandler(String name, ILed led) {
		super(name);
		this.led = led;
	}

	@Override
	public void elaborate(String message, Interaction conn) {

		switch (message) {
		case "on":
			led.turnOn();
			break;
		case "off":
			led.turnOff();
			break;
		case "getState":
			String asw = ""+led.getState();
			this.sendAnswerToClient(asw, conn);
		}

	}

}
