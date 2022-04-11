package it.unibo.radarSystem22.sprint3.main;

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
import org.json.JSONException;

public class RadarSystem22Sprint3ControllerOnPcMain implements IApplication {

    private LedProxyAsClient ledPxy;
    private SonarProxyAsClient sonarPxy;
    private IRadarDisplay radar;
    private Controller controller;

    protected void setup(String domainConfig, String systemConfig){
        if(domainConfig != null){
            try {
                DomainSystemConfig.setTheConfiguration(domainConfig);
            } catch (JSONException e) {
                System.out.println("RadarSystem22Sprint3ControllerOnPcMain   |   Error on domain configuration: "+e.getMessage());
            }
        }
        else{
            DomainSystemConfig.simulation   = true;
            //DomainSystemConfig.ledGui       = true;
            DomainSystemConfig.sonarDelay   = 200;
            DomainSystemConfig.DLIMIT       = 60;
        }
        if(systemConfig != null){
            try {
                RadarSystemConfig.setTheConfiguration(systemConfig);
            } catch (JSONException e) {
                System.out.println("RadarSystem22Sprint3ControllerOnPcMain   |   Error on system configuration: "+e.getMessage());
            }
        }
        else{
            RadarSystemConfig.RadarGuiRemote    = false;
            RadarSystemConfig.DLIMIT            = 60;
            RadarSystemConfig.protocolType      = ProtocolType.tcp;
            RadarSystemConfig.ledPort           = 8010;
            RadarSystemConfig.sonarPort         = 8015;
            RadarSystemConfig.raspAddr          = "localhost";
        }
    }

    protected void exec(){
        LambdaEndFun endFun = (n) -> {
            terminate();
        };

        controller.start(endFun,60);
    }

    protected void terminate(){
        System.exit(0);
    }

    protected void configure(){
        ledPxy = new LedProxyAsClient
                ("ledPxy",RadarSystemConfig.raspAddr,""+RadarSystemConfig.ledPort,RadarSystemConfig.protocolType);
        sonarPxy = new SonarProxyAsClient
                ("sonarPxy",RadarSystemConfig.raspAddr,""+RadarSystemConfig.sonarPort,RadarSystemConfig.protocolType);
        radar = ComponentFactory.createRadarGui();
        controller = Controller.create(sonarPxy,ledPxy,radar);
    }

    @Override
    public void doJob(String domainConfig, String systemConfig) {
        setup(domainConfig, systemConfig);
        configure();
        exec();
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    public static void main(String [] args){
        new RadarSystem22Sprint3ControllerOnPcMain().doJob(null, null);
    }

}
