package it.unibo.radarSystem22_s4.appl.main;

import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22_s4.appl.ActionFunction;
import it.unibo.radarSystem22_s4.appl.Controller;
import it.unibo.radarSystem22_s4.appl.RadarSystemConfig;
import it.unibo.radarSystem22_s4.appl.proxy.LedProxy;
import it.unibo.radarSystem22_s4.appl.proxy.SonarProxy;
import it.unibo.radarSystem22_s4.comm.ProtocolType;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplication;
import it.unibo.radarSystem22_s4.comm.utils.CommSystemConfig;

public class RadarSystemMainWithCtxOnPc implements IApplication {

    private ILed led;
    private ISonar sonar;
    private IRadarDisplay radar;
    private Controller controller;

    @Override
    public void doJob(String domainConfig, String systemConfig) {
        setup(domainConfig, systemConfig);
        configure();
        exec();
    }

    protected void setup(String domainConfig, String systemConfig) {
        if( domainConfig != null ) {
            DomainSystemConfig.setTheConfiguration(domainConfig);
        }
        else{
            DomainSystemConfig.simulation  = true;
            DomainSystemConfig.testing     = false;
            DomainSystemConfig.sonarDelay  = 200;
            DomainSystemConfig.ledGui      = true;
        }
        if( systemConfig != null ) {
            RadarSystemConfig.setTheConfiguration(systemConfig);
        }
        else{
            RadarSystemConfig.DLIMIT           = 80;
            RadarSystemConfig.RadarGuiRemote   = false;
            RadarSystemConfig.raspAddr         = "localhost";
            RadarSystemConfig.ctxServerPort    = 8756;
            RadarSystemConfig.protcolType      = ProtocolType.tcp;
        }
    }

    protected void configure(){

        String host = RadarSystemConfig.raspAddr;
        String port = ""+RadarSystemConfig.ctxServerPort;
        ProtocolType protocol = RadarSystemConfig.protcolType;

        led = new LedProxy("ledPxy", host, port, protocol);
        sonar = new SonarProxy("sonarPxy", host, port, protocol);
        radar = DeviceFactory.createRadarGui();

        controller = Controller.create(led,sonar,radar);
    }

    protected void exec(){
        ActionFunction endFun = (n) -> {
            System.out.println(n);
            terminate();
        };
        controller.start(endFun, 70);
    }

    public void terminate(){
        sonar.deactivate();
        System.exit(0);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    public static void main(String[] args){
        new RadarSystemMainWithCtxOnPc().doJob(null, null);
    }
}
