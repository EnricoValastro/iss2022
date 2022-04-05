package it.unibo.radarSystem22.sprint2a.main;

import org.json.JSONException;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.tcp.TcpServer;
import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.ComponentFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.sprint2a.handler.LedApplHandler;
import it.unibo.radarSystem22.sprint2a.handler.SonarApplHandler;
import it.unibo.radarSystem22.utility.RadarSystemConfig;

public class RadarSystemSprint2aDevicesOnRaspMain implements IApplication {

	private ILed led;
	private ISonar sonar;
	private IApplMsgHandler ledHandler;
	private IApplMsgHandler sonarHandler;
	private TcpServer ledServer;
	private TcpServer sonarServer;
	
	@Override
	public String getName() {
		return null;
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
				System.out.println("RadarSystemSprint2aDevicesOnRaspMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			DomainSystemConfig.simulation		= false;
			DomainSystemConfig.ledGui			= false;
			DomainSystemConfig.DLIMIT			= 70;
			DomainSystemConfig.sonarDelay		= 200;
		}
		if(systemConfig != null) {
			try {
				RadarSystemConfig.setTheConfiguration(systemConfig);
			} catch (JSONException e) {
				System.out.println("RadarSystemSprint2aDevicesOnRaspMain	|	Error on setup: "+e.getMessage());
			}
		}
		else {
			RadarSystemConfig.RadarGuiRemote	= true;
			RadarSystemConfig.ledPort			= 8010;
			RadarSystemConfig.sonarPort			= 8015;
			RadarSystemConfig.hostAddr			= "192.168.137.1";
			//RadarSystemConfig.hostAddr			= "localhost";
		}
	}
	
	private void configure() {
		
		led = ComponentFactory.createLed();
		sonar = ComponentFactory.createSonar();
		
		ledHandler = new LedApplHandler("ledHandler", led);
		sonarHandler = new SonarApplHandler("sonarHandler", sonar);
		
		ledServer = new TcpServer("ledServer", RadarSystemConfig.ledPort, ledHandler);
		sonarServer = new TcpServer("sonarServer",RadarSystemConfig.sonarPort, sonarHandler);
		
	}
	
	private void exec() {
		
		ledServer.activateServer();
		sonarServer.activateServer();
		
	}
	
	public static void main(String[] args) throws Exception{
		new RadarSystemSprint2aDevicesOnRaspMain().doJob(null, null);
		//new RadarSystemSprint2aDevicesOnRaspMain().doJob("DomainSystemConfig.json","RadarSystemConfig.json");
	}

}
