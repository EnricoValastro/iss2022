package it.unibo.radarSystem22_s4.appl.proxy;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22_s4.comm.ApplMessage;
import it.unibo.radarSystem22_s4.comm.ProtocolType;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.proxy.ProxyAsClient;
import it.unibo.radarSystem22_s4.comm.utils.CommUtils;

public class SonarProxy extends ProxyAsClient implements ISonar {

    protected static IApplMessage sonarActivate;
    protected static IApplMessage sonarDeactivate;
    protected static IApplMessage sonarIsActive;
    protected static IApplMessage getDistance;

    public SonarProxy(String name, String host, String entry, ProtocolType protocol) {
        super(name, host, entry, protocol);
        sonarActivate        = CommUtils.buildDispatch(name, "cmd", "activate", "sonar");
        sonarDeactivate      = CommUtils.buildDispatch(name, "cmd", "deactivate", "sonar");
        getDistance          = CommUtils.buildRequest(name, "ask", "getDistance","sonar");
        sonarIsActive        = CommUtils.buildRequest(name, "ask", "isActive",   "sonar");

    }

    @Override
    public void activate() {
        if(protocol == ProtocolType.tcp){
            sendCommandOnConnection(sonarActivate.toString());
        }
    }

    @Override
    public void deactivate() {
        if(protocol == ProtocolType.tcp){
            sendCommandOnConnection(sonarDeactivate.toString());
        }
    }

    @Override
    public IDistance getDistance() {
        String answer = "";
        if(protocol == ProtocolType.tcp){
            String reply = sendRequestOnConnection(getDistance.toString());
            answer = new ApplMessage(reply).msgContent();
        }
        return new Distance(Integer.parseInt(answer));
    }

    @Override
    public boolean isActive() {
        String answer = "";
        if(protocol == ProtocolType.tcp){
            String reply = sendRequestOnConnection(sonarIsActive.toString());
            answer = new ApplMessage(reply).msgContent();
        }
        return (answer.equals("true"));
    }
}
