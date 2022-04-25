package it.unibo.radarSystem22_s4.comm.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

public class TcpConnection implements Interaction2021 {
	
	private DataOutputStream outputChannel;
	private BufferedReader inputChannel;
	private Socket socket;

	public TcpConnection( Socket socket  ) throws Exception {
		this.socket                    = socket;
		OutputStream outStream 	       = socket.getOutputStream();
		InputStream inStream  	       = socket.getInputStream();
		outputChannel                  =  new DataOutputStream(outStream);
		inputChannel                   =  new BufferedReader( new InputStreamReader( inStream ) );		
	}
	
	@Override
	public void forward(String msg) throws Exception {
		
		System.out.println( "TcpConnection	|	sendMessage  " + msg + " on " + outputChannel);	 
		try {
			outputChannel.writeBytes( msg+"\n" );
			outputChannel.flush();	 
		} catch (IOException e) { 
			throw e;
		}	
		
	}

	@Override
	public String request(String msg) throws Exception {
		
		forward(  msg );
		String answer = receiveMsg();
		return answer;
	}

	@Override
	public void reply(String msg) throws Exception {
		
		forward(msg);
		
	}

	@Override
	public String receiveMsg() throws Exception {
		try {
			String	line = inputChannel.readLine() ; //blocking =>
 			return line;		
		} 
		catch ( Exception e   ) {
			System.out.println( "TcpConnection | receiveMsg ERROR  " + e.getMessage() );	
	 		return null;
		}		
	}

	@Override
	public void close() throws Exception {
		try {
			socket.close();
			System.out.println( "TcpConnection | CLOSED" );
		} catch (IOException e) {
			System.out.println( "TcpConnection | close ERROR " + e.getMessage());	
		}
	}

}
