package it.unibo.comm2022.tcp;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;

public class TcpApplMessageHandler extends Thread{
	
	private Interaction conn;
	private IApplMsgHandler appHandler;
	
	public TcpApplMessageHandler(IApplMsgHandler appHandler, Interaction conn ) {
		
		this.appHandler = appHandler;
		this.conn = conn;
		this.start();
		
	}
	
	@Override
	public void run() {
		
		//String name = appHandler.getName();
		try {
			while (true) {
				String msg = conn.receiveMsg();
				if (msg == null) {
					conn.close();
					break;
				}
				else {
					appHandler.elaborate(msg, conn);
				}
			}
		} 
		catch (Exception e) {
			System.out.println("TcpApplMessageHandler	|	Error: "+e.getMessage());
		}	
	}
	

	
}
