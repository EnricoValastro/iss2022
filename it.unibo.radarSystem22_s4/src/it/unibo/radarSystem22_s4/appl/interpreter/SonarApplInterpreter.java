package it.unibo.radarSystem22_s4.appl.interpreter;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplInterpreter;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;

public class SonarApplInterpreter implements IApplInterpreter {

    private ISonar sonar;

    public SonarApplInterpreter(ISonar sonar){
        this.sonar = sonar;
    }

    @Override
    public String elaborate(IApplMessage message) {
        return null;
    }
}
