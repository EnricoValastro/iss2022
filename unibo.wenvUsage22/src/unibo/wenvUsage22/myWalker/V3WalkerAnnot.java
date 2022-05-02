package unibo.wenvUsage22.myWalker;



import it.unibo.kactor.IApplMessage;
import unibo.actor22.QakActor22FsmAnnot;
import unibo.actor22.annotations.State;
import unibo.actor22.annotations.Transition;
import unibo.actor22comm.SystemData;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.annot.walker.WsConnWEnvObserver;
import unibo.wenvUsage22.common.VRobotMoves;

public class V3WalkerAnnot extends QakActor22FsmAnnot {

	private Interaction2021 conn;	
 	private int ncorner  = 0;
	
	public V3WalkerAnnot(String name) {
		super(name);
	}
	
	@State(name = "robotStart", initial = true)
	@Transition(state = "robotMoving",	msgId = SystemData.endMoveOkId)
	@Transition(state = "wallDetected",	msgId = SystemData.endMoveKoId)
	protected void robotStart(IApplMessage msg) {
		conn = WsConnection.create("localhost:8091" );
		((WsConnection)conn).addObserver( new WsConnWEnvObserver(getName()) );
  		VRobotMoves.step(getName(),conn);
	}
	
	@State(name = "robotMoving")
	@Transition(state = "robotMoving",	msgId = SystemData.endMoveOkId)
	@Transition(state = "wallDetected",	msgId = SystemData.endMoveKoId)
	protected void robotMoving(IApplMessage msg) {
			VRobotMoves.step(getName(), conn);	
	}
	
	@State(name = "wallDetected")
	@Transition(state = "robotMoving", msgId = SystemData.endMoveOkId, guard = V3GuardContinueWork.class)
	@Transition(state = "end", 		   msgId = SystemData.endMoveOkId, guard = V3GuardEndOfWork.class)	
	protected void wallDetected(IApplMessage msg) {
		ncorner++;
		V3GuardEndOfWork.setValue(ncorner);
		V3GuardContinueWork.setValue(ncorner);
		VRobotMoves.turnLeft(getName(), conn);
	}
	
	@State(name = "end")
	protected void end(IApplMessage msg) {
		System.out.println("BYE");	
 		System.exit(0);
	}
	
}
