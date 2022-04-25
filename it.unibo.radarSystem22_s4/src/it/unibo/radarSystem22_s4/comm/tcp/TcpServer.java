package it.unibo.radarSystem22_s4.comm.tcp;


import java.net.ServerSocket;
import java.net.Socket;

import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

public class TcpServer extends Thread{

	private ServerSocket serversock;
	protected IApplMsgHandler userDefHandler;
	protected String name;
	protected boolean stopped = true;
	
	public TcpServer(String name, int port, IApplMsgHandler userDefHandler ) {
		super(name);
		
		try {
			this.name = name;
			this.userDefHandler = userDefHandler;
			serversock = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println(getName() + " | costruct ERROR: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
	      try {
		  	System.out.println(getName() + " | STARTING ... ");
			while( ! stopped ) {
				//Accept a connection				 	 
		 		Socket sock          = serversock.accept();	
				System.out.println(getName() + " | accepted connection  ");  
		 		Interaction2021 conn = new TcpConnection(sock);
		 		//Create a message handler on the connection
		 		new TcpApplMessageHandler( userDefHandler, conn );			 		
			}//while
		  }catch (Exception e) {  //Scatta quando la deactive esegue: serversock.close();
			  System.out.println(getName() + " | probably socket closed: " + e.getMessage());		 
		  }
	}
	
	public void activate() {
		if(stopped) {
			stopped = false;
			this.start();
		}
	}
	
	public void deactivate() {
		try {
			stopped = true;
            serversock.close();
		} catch (Exception e) {
			System.out.println(getName() + " | deactivate ERROR: " + e.getMessage());	 
		}
	}
	
}
