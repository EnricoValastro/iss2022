package it.unibo.radarSystem22.sprint2a.handler;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;

public class SonarApplHandler extends ApplMsgHandler{

	private ISonar sonar;
	
	public SonarApplHandler(String name, ISonar sonar) {
		super(name);
		this.sonar = sonar;
		
	}

	@Override
	public void elaborate(String message, Interaction conn) {
		String asw;
		
		switch(message) {
		case "getDistance":
			IDistance d = sonar.getDistance();
			asw = ""+d.getVal();
			this.sendAnswerToClient(asw, conn);
			break;
		case "isActive":
			asw = ""+sonar.isActive();
			this.sendAnswerToClient(asw, conn);
			break;
		case "activate":
			sonar.activate();
			break;
		case "deactivate":
			sonar.deactivate();
			break;
		}
		
	}

}
