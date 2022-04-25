package unibo.wenvUsage22.wshttp;

import java.util.Observable;

import org.json.JSONObject;

import unibo.actor22comm.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.common.ApplData;

public class BoundaryWalkerWs implements IObserver{
     
	private boolean obstacle = false;
	
	private Interaction2021 conn;
    
	
	protected void doJob() throws Exception {
		
		conn = WsConnection.create("localhost:8091");
		((WsConnection)conn).addObserver(this);
		
		for(int i = 0; i<4; i++) {
			
			while(!obstacle) {
				conn.forward(ApplData.moveForward(300));
				Thread.sleep(500);
			}
			
			conn.forward(ApplData.turnLeft(300));
			Thread.sleep(500);
		}
		
		
		
		
	}


	@Override
	public void update(Observable o, Object arg) {
		
		System.out.println("update	|	answer: "+arg);
		
		JSONObject obj = new JSONObject(""+arg);
		obstacle = obj.has("collision");
		
	}


	@Override
	public void update(String value) {
		
	}
	
	public static void main(String[] args) throws Exception {
		new BoundaryWalkerWs().doJob();
	}
    
}
