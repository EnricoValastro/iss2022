package it.unibo.radarSystem22_s4.appl.handler;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22_s4.appl.interpreter.LedApplInterpreter;
import it.unibo.radarSystem22_s4.comm.ApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplInterpreter;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class LedApplHandler extends ApplMsgHandler {

    private IApplInterpreter ledInterpreter;

    public static IApplMsgHandler create(String name, ILed led){
        return new LedApplHandler(name, led);
    }

    public LedApplHandler(String name, ILed led) {
        super(name);
        ledInterpreter = new LedApplInterpreter(led);
    }

    @Override
    public void elaborate(IApplMessage message, Interaction2021 conn) {
        System.out.println(name+"   |   message handled: elaborating...");
        if(message.isRequest()){
            sendAnswerToClient(ledInterpreter.elaborate(message), conn);
        }
        else
            ledInterpreter.elaborate(message);
    }
}
