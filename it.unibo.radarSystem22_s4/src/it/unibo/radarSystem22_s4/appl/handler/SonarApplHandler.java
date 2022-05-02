package it.unibo.radarSystem22_s4.appl.handler;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22_s4.appl.interpreter.SonarApplInterpreter;
import it.unibo.radarSystem22_s4.comm.ApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplInterpreter;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

public class SonarApplHandler extends ApplMsgHandler {

    private IApplInterpreter sonarInterpreter;

    public static IApplMsgHandler create(String name, ISonar sonar){
        return new SonarApplHandler(name,sonar);
    }

    public SonarApplHandler(String name, ISonar sonar) {
        super(name);
        sonarInterpreter = new SonarApplInterpreter(sonar);
    }

    @Override
    public void elaborate(IApplMessage message, Interaction2021 conn) {
        System.out.println(name+"   |   message handled: elaborating...");
        if(message.isRequest()){
            sendAnswerToClient(sonarInterpreter.elaborate(message),conn);
        }
        else{
            sonarInterpreter.elaborate(message);
        }
    }
}
