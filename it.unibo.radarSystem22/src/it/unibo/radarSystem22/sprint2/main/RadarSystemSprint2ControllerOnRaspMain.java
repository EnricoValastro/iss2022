package it.unibo.radarSystem22.sprint2.main;

import org.json.JSONException;

import it.unibo.comm2022.utility.ProtocolType;
import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.ComponentFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.sprint1.Controller;
import it.unibo.radarSystem22.sprint2.proxy.RadarGuiProxyAsClient;
import it.unibo.radarSystem22.utility.LambdaEndFun;
import it.unibo.radarSystem22.utility.RadarSystemConfig;

public class RadarSystemSprint2ControllerOnRaspMain implements IApplication{
	
	private Controller controller;
	private ILed led;
	private ISonar sonar;
	private IRadarDisplay radarProxy;

	@Override
	public String getName() {
		
		return this.getClass().getName();
	}
	
	@Override
	public void doJob(String domainConfig, String systemConfig) {
		
		setup(domainConfig, systemConfig);
		configure();
		exec();
		
	}
	
	private void setup(String domainConfig, String systemConfig) {
		
		if(domainConfig != null) {
			try {
				DomainSystemConfig.setTheConfiguration(domainConfig);
			} catch (JSONException e) {
				System.out.println("RadarSystemSprint2ControllerOnRaspMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			DomainSystemConfig.simulation	 	= true;
			DomainSystemConfig.ledGui			= true;
			DomainSystemConfig.DLIMIT 			= 70;     
			DomainSystemConfig.step 			= 1; //step for sonar data generator.
			DomainSystemConfig.sonarDelay       = 200;    
			
		}
		if(systemConfig != null){
			try {
				RadarSystemConfig.setTheConfiguration(systemConfig);
			} catch (JSONException e) {
				System.out.println("RadarSystemSprint2ControllerOnRaspMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			RadarSystemConfig.DLIMIT 			= 70;
			RadarSystemConfig.RadarGuiRemote	= false;
			RadarSystemConfig.serverPort        = 8080;		
			RadarSystemConfig.hostAddr          = "172.20.10.4";
		}
	}
	
	private void configure() {
		
		sonar = ComponentFactory.createSonar();
		led = ComponentFactory.createLed();
		radarProxy = new RadarGuiProxyAsClient
				("radarProxy",RadarSystemConfig.hostAddr,""+RadarSystemConfig.serverPort,ProtocolType.tcp);
		controller =  Controller.create(sonar,led,radarProxy);
		
	}
	
	private void exec() {
		LambdaEndFun endFun = (n) -> { 
	    	System.out.println(n); 
	    	terminate(); 
	    };
	    controller.start(endFun, 60);
		
	}
	
	private void terminate() {
		sonar.deactivate();
		System.exit(0);
		
	}
	
	public static void main( String[] args) throws Exception {
		
		//new RadarSystemSprint2ControllerOnRaspMain().doJob(null, null); 		
		new RadarSystemSprint2ControllerOnRaspMain().doJob("DomainSystemConfig.json", "RadarSystemConfig.json");
		
 	}

	

}
