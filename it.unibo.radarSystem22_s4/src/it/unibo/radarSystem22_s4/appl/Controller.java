package it.unibo.radarSystem22_s4.appl;

import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22_s4.appl.usecases.LedUsecase;
import it.unibo.radarSystem22_s4.appl.usecases.RadarGuiUsecase;
import it.unibo.radarSystem22_s4.comm.utils.BasicUtils;

public class Controller {
    private ILed led;
    private ISonar sonar;
    private IRadarDisplay radar;
    private ActionFunction endFun;

    public static Controller create(ILed led, ISonar sonar,IRadarDisplay radar ) {
        return new Controller( led,  sonar, radar  );
    }
    public static Controller create(ILed led, ISonar sonar ) {
        return new Controller( led,  sonar, DeviceFactory.createRadarGui()  );
    }

    public Controller(ILed led, ISonar sonar, IRadarDisplay radar){
        this.led    = led;
        this.sonar  = sonar;
        this.radar  = radar;
        led.turnOff();
    }

    public void start( ActionFunction endFun, int limit ) {
        this.endFun = endFun;
        System.out.println("Controller   |   start with endFun=" + endFun);
        sonar.activate();
        activate( limit );
    }

    public void activate(int limit){
        new Thread() {
            public void run(){
                System.out.println("Controller   |   Actived...");
                boolean sonarStatus = sonar.isActive();
                if(sonarStatus){
                    //while(sonar.isActive())
                    for(int i=0; i<limit; i++){
                        IDistance d = sonar.getDistance();
                        if(radar != null)
                            RadarGuiUsecase.doUseCase(radar, d);
                        LedUsecase.doUseCase(led,d);
                        BasicUtils.delay(DomainSystemConfig.sonarDelay);
                    }
                }
                sonar.deactivate();
                endFun.run("Controller   |   Work done, bye");
            }

        }.start();
    }

}
