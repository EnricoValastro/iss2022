package it.unibo.comm2022;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;

public abstract class ApplMsgHandler implements IApplMsgHandler{
	
	private String name;

	public ApplMsgHandler(String name) {
		this.name = name; 
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public abstract void elaborate(String message, Interaction conn);

	@Override
	public void sendMsgToClient(String message, Interaction conn) {
		try {
			conn.forward(message);
		} catch (Exception e) {
			System.out.println("ApplMsgHandler	|	sendMsgToClient error "+e.getMessage());
		}
	}

	@Override
	public void sendAnswerToClient(String message, Interaction conn) {
		try {
			conn.reply(message);
		} catch (Exception e) {
			System.out.println("ApplMsgHandler	|	sendAnswerToClient error "+e.getMessage());
		}
	}

}
