package unibo.wenvUsage22.myWalker;

import org.json.JSONObject;
import it.unibo.kactor.IApplMessage;
import unibo.actor22.QakActor22;
import unibo.actor22comm.SystemData;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.ws.WsConnSysObserver;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.common.ApplData;
import unibo.wenvUsage22.common.VRobotMoves;

public class V1WalkerFsmBasic extends QakActor22{
	
	private enum State {start, goingAhead, turnedLeft, end };

	private Interaction2021 conn;
	private int numIter = 0;	
	private State curState  =  State.start ;
	

	public V1WalkerFsmBasic(String name) {
		super(name);
		init();
	}
	
	protected void init() {
		conn = WsConnection.create("localhost:8091" );
		((WsConnection) conn).addObserver( new WsConnSysObserver(getName()) );
	}


	@Override
	protected void handleMsg(IApplMessage msg) {
		System.out.println(getName() + "   |   handleMsg:" + msg);
		interpret(msg);
	}
	
	protected void interpret( IApplMessage m ) {
 		if( m.msgId().equals( ApplData.activateId )) {
 			fsm("",true);
			return;
		}
		if( m.isEvent() || m.msgId().equals( SystemData.wsEventId ) ) {  
			handleWsInfo(m);
			return;
		}
		if( ! m.msgId().equals( ApplData.moveCmdId )) {
			System.out.println(getName() + "   |   sorry, I don't handle :" + m);
			return;
		}		
	}
	
	protected void fsm(String move, boolean endmove){
		System.out.println("fsm " + curState + " move=" + move + " endmove="+endmove + " numIter=" + numIter);
        switch( curState ) {
	        case start: {
	        	VRobotMoves.step(getName(), conn );
	            curState = State.goingAhead;
	            numIter++;
	            break; 
	        }
	        case goingAhead: {
	            if (endmove ) {  
	             	VRobotMoves.step(getName(), conn );
	             } else {
	            	VRobotMoves.turnLeft(getName(), conn);
	                curState = State.turnedLeft;
	            }	        	
	        	break;
	        }
	        case turnedLeft:{
	        	numIter++;
                if( numIter < 5 ) {
                	VRobotMoves.step(getName(), conn );
                	curState = State.goingAhead;
                }
                else curState = State.end;
	        	break;
	        }
	        case end: {
	    		System.out.println("fsm DONE ");
	    		break;
	        }       
        }
    }
	
	protected void handleWsInfo(IApplMessage m) {
		String msg = m.msgContent().replace("'", "");
		JSONObject d = new JSONObject(""+msg);
		if( d.has("collision")) {
			fsm( d.getString("collision"), false);
			return;
 		}
		if( d.has("endmove") && d.getBoolean("endmove") ) {
			fsm( d.getString("move"), true);
			return;
		}	
    }
 
	
	


}
