package it.unibo.radarSystem22_s4.appl.proxy;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22_s4.comm.ApplMessage;
import it.unibo.radarSystem22_s4.comm.ProtocolType;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.proxy.ProxyAsClient;
import it.unibo.radarSystem22_s4.comm.utils.CommUtils;

public class LedProxy extends ProxyAsClient implements ILed {

    protected static IApplMessage turnOnLed;
    protected static IApplMessage turnOffLed;
    protected static IApplMessage getLedState;

    public LedProxy(String name, String host, String entry, ProtocolType protocol) {
        super(name, host, entry, protocol);
        turnOnLed   = CommUtils.buildDispatch(name, "cmd", "on", "led");
        turnOffLed  = CommUtils.buildDispatch(name, "cmd", "off", "led");
        getLedState = CommUtils.buildRequest(name,"ask", "getState", "led");
    }

    @Override
    public void turnOn() {
        if(protocol == ProtocolType.tcp){
            sendCommandOnConnection(turnOnLed.toString());
        }
        //Other protocol can be implemented
    }

    @Override
    public void turnOff() {
        if(protocol == ProtocolType.tcp){
            sendCommandOnConnection(turnOffLed.toString());
        }
    }

    @Override
    public boolean getState() {
        String answer = "";
        if(protocol == ProtocolType.tcp) {
            String reply = sendRequestOnConnection(getLedState.toString());
            answer = new ApplMessage(reply).msgContent();
        }
        return answer.equals("true");
    }
}
