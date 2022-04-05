package it.unibo.radarSystem22.sprint1;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utility.BasicUtility;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.sprint1.usecases.LedUsecase;
import it.unibo.radarSystem22.sprint1.usecases.RadarUsecase;
import it.unibo.radarSystem22.utility.LambdaEndFun;

public class Controller {
	
	private ISonar sonar;
	private ILed led;
	private IRadarDisplay radar;
	private LambdaEndFun endFun;
	
	
	public static Controller create(ISonar sonar, ILed led, IRadarDisplay radar) {

			return new Controller(sonar, led, radar);
	}
	
	private Controller(ISonar sonar, ILed led, IRadarDisplay radar) {
		this.sonar = sonar;
		this.led = led;
		this.radar = radar;
	}

	public void start(LambdaEndFun endFun, int limit) {
		this.endFun = endFun;
		sonar.activate();
		activateController(limit);
	}
	
	private void activateController(int limit) {
		
		new Thread() {
			
			public void run() {
				
				if(sonar.isActive()) {
					for(int i = 1; i<limit; i++) {
						IDistance d = sonar.getDistance();
						RadarUsecase.doUsecase(radar,d);
						LedUsecase.doUsecase(led, d);
						BasicUtility.delay(DomainSystemConfig.sonarDelay);
					}
				}
				sonar.deactivate();
				endFun.run("Controller	|	Execution Completed: System shutdown");
			}
		}.start();
		
	}
}
