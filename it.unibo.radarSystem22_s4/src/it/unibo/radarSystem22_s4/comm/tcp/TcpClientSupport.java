package it.unibo.radarSystem22_s4.comm.tcp;

import java.net.Socket;

import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

public class TcpClientSupport {
	
	public static Interaction2021 connect(String host, int port, int nattempts ) throws Exception {
//		TcpClient tcpc = new TcpClient(host,   port,   nattempts);
//		return new TcpConnection( tcpc.getSocket() );
		 
		for( int i=1; i<=nattempts; i++ ) {
			try {
				Socket socket         =  new Socket( host, port );
 				Interaction2021 conn  =  new TcpConnection( socket );
				return conn;
			}catch(Exception e) {
				System.out.println("TcpClient | Another attempt to connect with host:" + host + " port=" + port);
				Thread.sleep(500);
			}
		}//for
		throw new Exception("TcpClient | Unable to connect to host:" + host);
		 
 	}
}
