package it.unibo.radarSystem22.sprint3.interpreters;

import it.unibo.radarSystem22.domain.interfaces.ILed;

public class LedApplInterpreters implements IApplInterpreters{

    private ILed led;

    public LedApplInterpreters(ILed led){
        this.led = led;
    }

    @Override
    public String elaborate(String message) {

        if(message.equals("getState")){
            return ""+led.getState();
        }
        else if(message.equals("on")) led.turnOn();
        else if(message.equals("off")) led.turnOff();
        return message+"_done";
    }
}
