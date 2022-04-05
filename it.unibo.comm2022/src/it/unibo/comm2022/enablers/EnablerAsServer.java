package it.unibo.comm2022.enablers;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.tcp.TcpServer;
import it.unibo.comm2022.utility.ProtocolType;

public class EnablerAsServer {
	
	protected int count = 1;
	protected boolean state = false;
	protected String name;
	protected TcpServer serverTcp;
	protected ProtocolType protocol;
	//protected UdpServer serverUdp;
	
	public EnablerAsServer(String name, int port, ProtocolType protocol, IApplMsgHandler handler) {
		
		this.name = name;
		this.protocol = protocol;
		if(protocol != null) {
			setServerSupport(port,protocol,handler);	
		}
		else
			System.out.println(name+"	|	Protocol undefined");
				
	}
	
	protected void setServerSupport(int port, ProtocolType protocol, IApplMsgHandler handler) {
		
		switch(protocol) {
			case tcp:
				serverTcp = new TcpServer("EnablerSrvTcp_"+count++, port, handler);
				System.out.println(name+"	|	Created on port: "+port+" protocol: "+protocol);
				break;
			case udp:
				//serverUdp = new UdpServer(...);
				break;
			case coap:
				//to-do
				break;
			case mqtt:
				//to-do
				break;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isActive() {
		return state;
	}
	
	public void start() {
		
		if(!state) {
			switch(protocol) {
				case tcp:
					serverTcp.activateServer();
					break;
				case udp:
					//serverUdp.activateServer();
					break;
				case coap:
					break;
				default:
					break;
			}
			state = true;
		}
		
	}
	
	public void stop() {
		
		if(state) {
			switch(protocol) {
				case tcp:
					serverTcp.deactivateServer();
					break;
				case udp:
					//serverUdp.deactivateServer();
					break;
				case coap:
					break;
				default:
					break;
				
			}
			state = false;
		}
	}
	
}
