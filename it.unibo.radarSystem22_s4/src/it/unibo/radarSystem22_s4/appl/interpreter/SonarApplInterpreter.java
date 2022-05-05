package it.unibo.radarSystem22_s4.appl.interpreter;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplInterpreter;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.utils.CommUtils;

public class SonarApplInterpreter implements IApplInterpreter {

    private ISonar sonar;

    public SonarApplInterpreter(ISonar sonar){
        this.sonar = sonar;
    }

    /*
    @Override
    public String elaborate(IApplMessage message) {
        String cont = message.msgContent();
        String answ = null;
        switch (cont){
            case "activate":
                sonar.activate();
                break;
            case "deactivate":
                sonar.deactivate();
                break;
            case "isActive":
                answ = CommUtils.prepareReply(message, ""+sonar.isActive()).toString();
                break;
            case "getDistance":
                answ = CommUtils.prepareReply(message, ""+sonar.getDistance().getVal()).toString();
                break;
        }
        return answ;
    }
     */

    @Override
    public String elaborate(IApplMessage message){
        String answ = null;
        if(message.isDispatch()){
            elabCommand(message);
        }
        else if(message.isRequest()){
            answ = elabRequest(message);
        }
        return answ;
    }

    protected void elabCommand(IApplMessage message){
        String cont = message.msgContent();
        if(cont.equals("activate")){
            sonar.activate();
        }
        else if(cont.equals("deactivate")){
            sonar.deactivate();
        }
    }

    protected String elabRequest(IApplMessage message){
        String cont = message.msgContent();
        String asw = null;
        if(cont.equals("isActive")){
            asw = CommUtils.prepareReply(message, ""+sonar.isActive()).toString();
        }
        else if(cont.equals("getDistance")){
            asw =  CommUtils.prepareReply(message, ""+sonar.getDistance().getVal()).toString();
        }
        return asw;
    }
}
