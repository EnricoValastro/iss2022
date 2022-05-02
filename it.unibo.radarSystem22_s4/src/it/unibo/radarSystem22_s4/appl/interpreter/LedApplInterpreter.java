package it.unibo.radarSystem22_s4.appl.interpreter;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplInterpreter;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.utils.CommUtils;
import org.json.JSONObject;

public class LedApplInterpreter implements IApplInterpreter {

    protected ILed led;

    public LedApplInterpreter(ILed led){
        this.led = led;
    }

    @Override
    public String elaborate(IApplMessage message) {
        String answer = null;
        if(message.isRequest()){
            answer = elabRequest(message);
        }
        else
            elabCommand(message);
        return answer;
    }

    private void elabCommand(IApplMessage message){
        String cont = message.msgContent();
        if(cont.equals("on")){
            led.turnOn();
        }
        else if(cont.equals("off")){
            led.turnOff();
        }
        else{
            System.out.println("LedApplInterpreter   |   Error: command unknown");
        }
    }

    private String elabRequest(IApplMessage message){
        String cont = message.msgContent();
        if(cont.equals("getState")){
            String answ = ""+led.getState();
            return CommUtils.prepareReply(message, answ).toString();
        }
        else{
            System.out.println("LedApplInterpreter   |   Error: request unknown");
            return "Request Unknown";
        }
    }
}
