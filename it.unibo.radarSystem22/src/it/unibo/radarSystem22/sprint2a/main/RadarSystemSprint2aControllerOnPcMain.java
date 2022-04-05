package it.unibo.radarSystem22.sprint2a.main;

import org.json.JSONException;

import it.unibo.comm2022.utility.ProtocolType;
import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.ComponentFactory;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.sprint1.Controller;
import it.unibo.radarSystem22.sprint2a.proxy.LedProxyAsClient;
import it.unibo.radarSystem22.sprint2a.proxy.SonarProxyAsClient;
import it.unibo.radarSystem22.utility.LambdaEndFun;
import it.unibo.radarSystem22.utility.RadarSystemConfig;

public class RadarSystemSprint2aControllerOnPcMain implements IApplication {
	
	private Controller controller;
	private IRadarDisplay radar;
	private LedProxyAsClient ledPxy;
	private SonarProxyAsClient sonarPxy;
	
	@Override
	public String getName() {
		
		return this.getClass().getName();
	}

	@Override
	public void doJob(String domainConfig, String systemConfig) {
		setup(domainConfig,systemConfig);
		configure();
		exec();
		
	}
	
	private void setup(String domainConfig, String systemConfig) {
		
		if(domainConfig != null) {
			try {
				DomainSystemConfig.setTheConfiguration(domainConfig);
			} catch (JSONException e) {
				System.out.println("RadarSystemSprint2aControllerOnPcMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			DomainSystemConfig.simulation		= false;
			DomainSystemConfig.ledGui			= false;
			DomainSystemConfig.DLIMIT			= 80;
			DomainSystemConfig.sonarDelay		= 200;
		}
		if(systemConfig != null) {
			try {
				RadarSystemConfig.setTheConfiguration(systemConfig);
			} catch (JSONException e) {
				System.out.println("RadarSystemSprint2aControllerOnPcMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			RadarSystemConfig.RadarGuiRemote	= false;
			RadarSystemConfig.ledPort			= 8010;
			RadarSystemConfig.sonarPort			= 8015;
			RadarSystemConfig.DLIMIT			= 70;
			RadarSystemConfig.raspAddr			= "192.168.2.2";
			//RadarSystemConfig.raspAddr			= "localhost";
		}
	}

	private void configure() {
		
		radar = ComponentFactory.createRadarGui();
		ledPxy = new LedProxyAsClient
				("ledPxy", RadarSystemConfig.raspAddr, ""+RadarSystemConfig.ledPort, ProtocolType.tcp);
		
		sonarPxy = new SonarProxyAsClient
				("sonarPxy", RadarSystemConfig.raspAddr, ""+RadarSystemConfig.sonarPort, ProtocolType.tcp);
		controller = Controller.create(sonarPxy, ledPxy, radar);
		
	}
	
	private void exec() {
		LambdaEndFun endFun = (n) -> { 
	    	System.out.println(n); 
	    	terminate(); 
	    };
	    controller.start(endFun, 60);
	}
	
	private void terminate() {
		sonarPxy.deactivate();
		System.exit(0);
	}
	
	
	public static void main(String args[]) {
		new RadarSystemSprint2aControllerOnPcMain().doJob(null, null);
		//new RadarSystemSprint2aControllerOnPcMain().doJob("DomainSystemConfig.json", "RadarSystemConfig.json");
	}

}
