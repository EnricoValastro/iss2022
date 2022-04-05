package it.unibo.comm2022.proxy;

import it.unibo.comm2022.interfaces.Interaction;
import it.unibo.comm2022.tcp.TcpClientSupport;
import it.unibo.comm2022.utility.ProtocolType;

public class ProxyAsClient {
	
	private Interaction conn;
	protected String name;
	protected ProtocolType protocol;
	
	public ProxyAsClient(String name, String host, String entry, ProtocolType protocol) {
		
		try {
			this.name = name; 
			this.protocol = protocol;
			setConnection(host,entry,protocol);
		} catch (Exception e) {
			System.out.println("ProxyAsClient	|	Error: "+e.getMessage());
		}
	}
	
	private void setConnection(String host, String entry, ProtocolType protocol) throws Exception {
		switch(protocol) {
			case tcp: 
				conn = TcpClientSupport.connect(host, Integer.parseInt(entry), 5);
				break;
			case udp:
				//conn = UdpClientSupport.connect(...);
				break;
			case coap:
				break;
			case mqtt:
				break;
			default:
				System.out.println("ProxyAsClient	|	Protocol: "+protocol+" unknown");
		}
	}
	
	public void sendCommandOnConnection(String msg) {
		try {
			conn.forward(msg);
		} catch (Exception e) {
			System.out.println("ProxyAsClient	|	Error on sendCommandOnConnection: "+e.getMessage());
		}
	}
	
	public String sendRequestOnConnection(String msg) {
		try {
			String answer = conn.request(msg);
			return answer;
		} catch (Exception e) {
			System.out.println("ProxyAsClient	|	Error on sendRequestOnConnection: "+e.getMessage());	
			return null;
		}
	}
	
	public Interaction getConn() {
		return conn;
	}
	
	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("ProxyAsClient	|	Error on close: "+e.getMessage());
		}
	}

}
