package it.unibo.radarSystem22_s4.comm;

import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

public abstract class ApplMsgHandler implements IApplMsgHandler {
	
	protected String name;
	
	public ApplMsgHandler(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public abstract void elaborate(IApplMessage message, Interaction2021 conn);

	@Override
	public void sendMsgToClient(String message, Interaction2021 conn) {
		try {
			conn.forward(message);
		} catch (Exception e) {
			System.out.println("ApplMsgHandler	|	sendMsgToClient Error: "+e.getMessage());
		}
	}

	@Override
	public void sendAnswerToClient(String message, Interaction2021 conn) {
		
		try {
			conn.reply(message);
		} catch (Exception e) {
			System.out.println("ApplMsgHandler	|	sendAnswerToClient Error: "+e.getMessage());
		}
	}

}
