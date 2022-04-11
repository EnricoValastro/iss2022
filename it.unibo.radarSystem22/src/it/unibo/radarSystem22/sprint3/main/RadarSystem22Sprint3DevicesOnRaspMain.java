package it.unibo.radarSystem22.sprint3.main;

import it.unibo.comm2022.enablers.EnablerAsServer;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.utility.ProtocolType;
import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.ComponentFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;
import it.unibo.radarSystem22.sprint3.handler.LedApplHandler;
import it.unibo.radarSystem22.sprint3.handler.SonarApplHandler;
import it.unibo.radarSystem22.utility.RadarSystemConfig;
import org.json.JSONException;

public class RadarSystem22Sprint3DevicesOnRaspMain implements IApplication {

    private EnablerAsServer sonarEnabler;
    private EnablerAsServer ledEnabler;
    private ILed led;
    private ISonar sonar;
    private IApplMsgHandler ledHandler;
    private IApplMsgHandler sonarHandler;

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

    protected void setup(String domainConfig, String systemConfig){
        if(domainConfig != null){
            try {
                DomainSystemConfig.setTheConfiguration(domainConfig);
            } catch (JSONException e) {
                System.out.println("RadarSystem22Sprint3DevicesOnRaspMain   |   Error on domain configuration: "+e.getMessage());
            }
        }
        else{
            DomainSystemConfig.simulation   = true;
            DomainSystemConfig.ledGui       = true;
            DomainSystemConfig.sonarDelay   = 200;
            DomainSystemConfig.DLIMIT       = 60;
        }
        if(systemConfig != null){
            try {
                RadarSystemConfig.setTheConfiguration(systemConfig);
            } catch (JSONException e) {
                System.out.println("RadarSystem22Sprint3DevicesOnRaspMain   |   Error on system configuration: "+e.getMessage());
            }
        }
        else{
            RadarSystemConfig.RadarGuiRemote    = true;
            RadarSystemConfig.DLIMIT            = 60;
            RadarSystemConfig.protocolType      = ProtocolType.tcp;
            RadarSystemConfig.ledPort           = 8010;
            RadarSystemConfig.sonarPort         = 8015;
            RadarSystemConfig.hostAddr          = "localhost";
        }
    }

    protected void configure(){
        led             = ComponentFactory.createLed();
        sonar           = ComponentFactory.createSonar();
        ledHandler      = new LedApplHandler("ledHandler", led);
        sonarHandler    = new SonarApplHandler("sonarHandler", sonar);
        ledEnabler      = new EnablerAsServer("ledEnabler", RadarSystemConfig.ledPort,RadarSystemConfig.protocolType,ledHandler);
        sonarEnabler    = new EnablerAsServer("sonarEnabler",RadarSystemConfig.sonarPort,RadarSystemConfig.protocolType,sonarHandler);
    }

    protected void exec(){
        ledEnabler.start();
        sonarEnabler.start();
    }

    public static void main(String[] args){

        new RadarSystem22Sprint3DevicesOnRaspMain().doJob(null,null);
    }
}
