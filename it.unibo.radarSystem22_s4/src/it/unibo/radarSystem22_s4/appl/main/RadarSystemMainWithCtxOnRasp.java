package it.unibo.radarSystem22_s4.appl.main;

import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22_s4.appl.RadarSystemConfig;
import it.unibo.radarSystem22_s4.appl.handler.LedApplHandler;
import it.unibo.radarSystem22_s4.appl.handler.SonarApplHandler;
import it.unibo.radarSystem22_s4.comm.ProtocolType;
import it.unibo.radarSystem22_s4.comm.context.ContextMsgHandler;
import it.unibo.radarSystem22_s4.comm.enablers.EnablerContext;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplication;
import it.unibo.radarSystem22_s4.comm.interfaces.IContext;

public class RadarSystemMainWithCtxOnRasp implements IApplication {

    private ILed led;
    private ISonar sonar;
    private IContext contextServer;

    @Override
    public void doJob(String domainConfig, String systemConfig) {
        setup( domainConfig, systemConfig);
        configure();
        exec();
    }

    protected void setup(String domainConfig, String systemConfig){
        if(domainConfig != null){
            DomainSystemConfig.setTheConfiguration(domainConfig);
        }
        else{
            DomainSystemConfig.simulation  = true;
            DomainSystemConfig.testing     = false;
            DomainSystemConfig.tracing     = false;
            DomainSystemConfig.sonarDelay  = 200;
            DomainSystemConfig.ledGui      = true;
        }
        if(systemConfig != null){
            RadarSystemConfig.setTheConfiguration(systemConfig);
        }
        else{
            RadarSystemConfig.RadarGuiRemote   = true;
            RadarSystemConfig.ctxServerPort    = 8756;
            RadarSystemConfig.protcolType      = ProtocolType.tcp;
        }
    }

    protected void configure(){
        led     = DeviceFactory.createLed();
        sonar   = DeviceFactory.createSonar();

        contextServer = new EnablerContext
                ("ctx", ""+RadarSystemConfig.ctxServerPort, RadarSystemConfig.protcolType, new ContextMsgHandler("ctxH"));

        IApplMsgHandler ledH    = new LedApplHandler("ledH", led);
        IApplMsgHandler sonarH  = new SonarApplHandler("sonarH", sonar);

        contextServer.addComponent("led", ledH);
        contextServer.addComponent("sonar", sonarH);
    }

    protected void exec(){
        contextServer.activate();
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    public static void main(String[] args){
        new RadarSystemMainWithCtxOnRasp().doJob(null,null);
    }
}
