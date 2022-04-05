package it.unibo.radarSystem22.sprint2.main;

import org.json.JSONException;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.tcp.TcpServer;
import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.ComponentFactory;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.sprint2.handler.RadarApplHandler;
import it.unibo.radarSystem22.utility.RadarSystemConfig;

public class RadarSystemSprint2RadarOnPcMain implements IApplication{

	private IRadarDisplay radar;
	private TcpServer server;
	
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
				System.out.println("RadarSystemSprint2RadarOnPcMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			
		}
		if(systemConfig != null) {
			try {
				RadarSystemConfig.setTheConfiguration(systemConfig);
			} catch (JSONException e) {
				System.out.println("RadarSystemSprint2RadarOnPcMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			RadarSystemConfig.serverPort	=	8080;
			RadarSystemConfig.raspAddr		= 	"192.168.2.2";
		}
		
	}
	
	private void configure() {
		
		radar = ComponentFactory.createRadarGui();
		IApplMsgHandler radarHandler = new RadarApplHandler("radarHandler", radar);
		server = new TcpServer("RadarGuiServer",RadarSystemConfig.serverPort,radarHandler);
		
	}
	
	private void exec() {
		
		server.activateServer();
		
	}
	
	public static void main( String[] args) throws Exception {
		
		//new RadarSystemSprint2RadarOnPcMain().doJob(null, null);
		new RadarSystemSprint2RadarOnPcMain().doJob("DomainSystemConfig.json", "RadarSystemConfig.json"); 		
 	}
	
}
