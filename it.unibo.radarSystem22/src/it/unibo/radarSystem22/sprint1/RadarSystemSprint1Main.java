package it.unibo.radarSystem22.sprint1;

import org.json.JSONException;

import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.ComponentFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.utility.LambdaEndFun;
import it.unibo.radarSystem22.utility.RadarSystemConfig;

public class RadarSystemSprint1Main implements IApplication{
	
	private Controller controller;
	private ILed led;
	private ISonar sonar;
	private IRadarDisplay radar;

	private void setup(String domainConfig, String systemConfig) {
		
		if(domainConfig != null) {
			//configura il dominio leggendo dal file 
			try {
				DomainSystemConfig.setTheConfiguration(domainConfig);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(systemConfig != null) {
			try {
				RadarSystemConfig.setTheConfiguration(systemConfig);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(domainConfig == null && systemConfig == null) {
			
			DomainSystemConfig.simulation	 	= false;
			DomainSystemConfig.ledGui			= false;
			DomainSystemConfig.DLIMIT 			= 80;     
			DomainSystemConfig.step 			= 1; //step for sonar data generator.
			DomainSystemConfig.sonarDelay       = 200;    
			
			RadarSystemConfig.DLIMIT 			= 70;
			RadarSystemConfig.RadarGuiRemote	= true;
		}
	}
	
	private void initComponent() {
		System.out.println("InitComponent	|	Simulation = "+DomainSystemConfig.simulation);
		led = ComponentFactory.createLed();
		sonar = ComponentFactory.createSonar();
		if(RadarSystemConfig.RadarGuiRemote)
			radar = null;
		else 
			radar = ComponentFactory.createRadarGui();
		controller = Controller.create(sonar,led,radar);
	}
	
	private void shutDown() {
		sonar.deactivate();
		System.exit(0);
	}
	
	@Override
	public void doJob(String domainConfig, String systemConfig) {
		
		setup(domainConfig,systemConfig);
		initComponent();
		
		LambdaEndFun endFun = (msg) -> {
			System.out.println(msg);
			shutDown();
		};
		
		controller.start(endFun, 50);
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
	
	public static void main( String[] args) throws Exception {
		
		//new RadarSystemSprint1Main().doJob(null, null); 		
		new RadarSystemSprint1Main().doJob("DomainSystemConfig.json", "RadarSystemConfig.json");
 	}


}
