package unibo.wenvUsage22.myWalker;

import unibo.actor22.Qak22Context;
import unibo.actor22.annotations.Actor22;
import unibo.actor22.annotations.Context22;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;
import unibo.wenvUsage22.common.ApplData;

@Context22(name = "pcCtx", host = "localhost", port = "8083")
@Actor22(name = ApplData.robotName,contextName="pcCtx",implement = V3WalkerAnnot.class)
public class V3MainWalkerAnnot {

	
	protected void configure() throws Exception {
 		Qak22Context.configureTheSystem(this);
		CommUtils.delay(1000);  
		Qak22Context.showActorNames();
		CommSystemConfig.tracing = false;
 	}
	
	public static void main(String[] args) throws Exception   {
		CommUtils.aboutThreads("Before start - ");
 		new V3MainWalkerAnnot().configure();
  		CommUtils.aboutThreads("At end - ");
	}
	
}
