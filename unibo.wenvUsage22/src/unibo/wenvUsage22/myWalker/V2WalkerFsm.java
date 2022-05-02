package unibo.wenvUsage22.myWalker;

import org.json.JSONObject;

import it.unibo.kactor.IApplMessage;
import unibo.actor22.QakActor22Fsm;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.interfaces.StateActionFun;
import unibo.actor22comm.ws.WsConnSysObserver;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.common.ApplData;
import unibo.wenvUsage22.common.VRobotMoves;

public class V2WalkerFsm extends QakActor22Fsm {

	private Interaction2021 conn;
	private int numIter = 1;
	
	public V2WalkerFsm(String name) {
		super(name);
	}

	@Override
	protected void declareTheStates() {
		
		declareState("start", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);	
				conn = WsConnection.create("localhost:8091" );				   
				((WsConnection)conn).addObserver(new WsConnSysObserver(getName()));
				addTransition( "goingAhead", ApplData.robotCmdId );
				nextState();
			}			
		});
		
		declareState("goingAhead", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);	
 				VRobotMoves.step(getName(),conn);
				addTransition( "checkResult",  "wsEvent" );
 				nextState();
			}			
		});
		
		declareState("checkResult", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);	
				String payload = msg.msgContent().replaceAll("'",""); 
				JSONObject json = new JSONObject(payload);
				outInfo(""+json);
				if( json.has("endmove")) {
					VRobotMoves.step(getName(), conn);
					addTransition( "checkResult",  "wsEvent" );
	 				nextState();
				}
				else if (json.has("collision") && numIter<4) {
					numIter++;
					VRobotMoves.turnLeft(getName(), conn);
					addTransition("checkResult", "wsEvent");
					nextState();
				}
				else {
					VRobotMoves.turnLeft(getName(), conn);
					addTransition("end", "wsEvent");
					nextState();
				}
				
				
 			}			
		});
		
		declareState("end", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);
				outInfo("BYE" );
				addTransition( "end", ApplData.haltSysCmdId );
  			}			
		});
		
	}

	@Override
	protected void setTheInitialState() {
		declareAsInitialState( "start" );
	}

}
