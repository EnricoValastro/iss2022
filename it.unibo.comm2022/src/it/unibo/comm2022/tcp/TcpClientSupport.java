package it.unibo.comm2022.tcp;

import java.net.Socket;

import it.unibo.comm2022.interfaces.Interaction;

public class TcpClientSupport {
	
	public static Interaction connect(String ip, int port, int natt) throws Exception {
		
		for(int i=0; i<natt; i++) {
			try {
				Socket socket = new Socket(ip,port);
				Interaction conn = new TcpConnection(socket);
				return conn;
			} catch (Exception e) {
				System.out.println("TcpClientSupport	|	Something went wrong, new attempt to connect with host");
			}
		}
		throw new Exception("TcpClientSupport	|	Unable to connect to host: "+ip);
	}
	
}
