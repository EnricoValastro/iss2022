package it.unibo.radarSystem22_s4.comm.tcp;

import it.unibo.radarSystem22_s4.comm.ApplMessage;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

public class TcpApplMessageHandler extends Thread {

	private IApplMsgHandler handler ;
	private Interaction2021 conn;
	
	public TcpApplMessageHandler(  IApplMsgHandler handler, Interaction2021 conn ) {
		this.handler = handler;
		this.conn    = conn;
 		this.start();
	}
	
	@Override 
	public void run() {
		String name = handler.getName();
		try {
			System.out.println( "TcpApplMessageHandler | STARTS with handler=" + name + " conn=" + conn);
			while( true ) {
			    String msg = conn.receiveMsg();
			    System.out.println(name + "  | TcpApplMessageHandler received:" + msg + " handler="+handler);
			    if( msg == null ) {
			    	conn.close();
			    	break;
			    } else{ 
			    	//Creare ApplMessage
			    	IApplMessage m = new ApplMessage(msg);
			    	handler.elaborate( m, conn ); 
			    }
			}
			
		}catch( Exception e) {
			System.out.println( name + "  | ERROR:" + e.getMessage()  );
		}	
	}
	
}
