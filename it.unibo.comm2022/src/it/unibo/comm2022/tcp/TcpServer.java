package it.unibo.comm2022.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;

public class TcpServer extends Thread{
	
	private ServerSocket serverSocket;
	private IApplMsgHandler userAppHandler;
	String name;
	private boolean stopped = true;
	
	public TcpServer(String name, int port, IApplMsgHandler userAppHandler) {
		super(name);
		this.userAppHandler = userAppHandler;
		this.name = name;
		
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(600000); //add CommSystemConfig
			
		} catch (Exception e) {
			System.out.println("TcpServer	|	costruction failed: "+e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			while(!stopped) {
				Socket socket = serverSocket.accept();
				System.out.println("TcpServer	|	connnection accepted");
				Interaction conn = new TcpConnection (socket);
				new TcpApplMessageHandler(userAppHandler, conn);
			}
			
		} catch (Exception e) {
			System.out.println("TcpServer	|	connection failed "+e.getMessage());
		}
	}
	
	public void activateServer() {
		if(stopped) {
			stopped = false;
			this.start();
		}
	}
	public void deactivateServer() {
			
			try {
				stopped = true;
				serverSocket.close();
			} catch (IOException e) {
				
				System.out.println("TcpServer	|	deactivateServer error: "+e.getMessage());
			}
	}
	
}
