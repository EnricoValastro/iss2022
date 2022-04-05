package it.unibo.comm2022.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import it.unibo.comm2022.interfaces.Interaction;

public class TcpConnection implements Interaction{
	
	private DataOutputStream output;
	private BufferedReader input;
	private Socket socket;
	
	public TcpConnection(Socket socket) throws Exception {
		this.socket = socket;
		OutputStream out 	= socket.getOutputStream();
		InputStream in 		= socket.getInputStream();
		output = new DataOutputStream(out);
		input = new BufferedReader(new InputStreamReader(in));
	}
	
	@Override
	public void forward(String msg) throws Exception{
		System.out.println("TcpConnection	|	Sending message: "+msg);
		try {
			
			output.writeBytes(msg+"\n");
			output.flush();
		
		} catch (IOException e) {
			System.out.println("TcpConnection	|	ForwardError: "+e.getMessage() );
			throw e;
		}
		
	}

	@Override
	public String request(String msg) throws Exception {
	
		forward(msg);
		String answer = receiveMsg();
		return answer;
	}

	@Override
	public void reply(String reqid) throws Exception {
		
		forward(reqid);
	}

	@Override
	public String receiveMsg()  {
		try {
			String line = input.readLine();
			return line;
			
		} catch (IOException e) {
			System.out.println("TcpConnection	|	ReceiveMsgError: "+e.getMessage() );
			return null;
		}
		
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			
			System.out.println("TcpConnection	|	CloseError: "+e.getMessage() );
		}
		
	}

}
