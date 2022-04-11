package it.unibo.radarSystem22.sprint3.handler;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.sprint3.interpreters.IApplInterpreters;
import it.unibo.radarSystem22.sprint3.interpreters.LedApplInterpreters;

public class LedApplHandler extends ApplMsgHandler {

    private IApplInterpreters ledInterpreter;

    public LedApplHandler(String name, ILed led) {

        super(name);
        ledInterpreter = new LedApplInterpreters(led);

    }

    @Override
    public void elaborate(String msg, Interaction conn) {

        if(msg.equals("getState")){
            sendAnswerToClient(ledInterpreter.elaborate(msg),conn);
        }
        else{
            ledInterpreter.elaborate(msg);
        }

    }

}
