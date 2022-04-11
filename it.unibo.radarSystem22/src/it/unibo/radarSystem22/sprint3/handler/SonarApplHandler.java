package it.unibo.radarSystem22.sprint3.handler;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.sprint3.interpreters.SonarApplInterpreters;

public class SonarApplHandler extends ApplMsgHandler {
    private SonarApplInterpreters sonarInterpreter;

    public SonarApplHandler(String name, ISonar sonar) {
        super(name);
        sonarInterpreter = new SonarApplInterpreters(sonar);
    }

    @Override
    public void elaborate(String msg, Interaction conn) {

        if(msg.equals("isActive") || msg.equals("getDistance")) {
            sendAnswerToClient(sonarInterpreter.elaborate(msg), conn);
        }
        else{
            sonarInterpreter.elaborate(msg);
        }
    }
}
