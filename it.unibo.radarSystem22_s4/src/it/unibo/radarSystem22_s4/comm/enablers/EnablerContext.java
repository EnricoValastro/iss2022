package it.unibo.radarSystem22_s4.comm.enablers;

import it.unibo.radarSystem22_s4.comm.ProtocolType;
import it.unibo.radarSystem22_s4.comm.context.ContextMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IContext;
import it.unibo.radarSystem22_s4.comm.interfaces.IContextMsgHandler;
import it.unibo.radarSystem22_s4.comm.tcp.TcpServer;

public class EnablerContext implements IContext {

    protected IContextMsgHandler ctxMsgHandler;
    protected String name;
    protected ProtocolType protocol;
    protected TcpServer serverTcp;
    //protected UdpServer serverUdp;
    protected boolean isactive = false;

    public EnablerContext( String name, String port, ProtocolType protocol )   {
        this(name,port,protocol,new ContextMsgHandler("ctxH"));
    }

    public EnablerContext( String name, String port, ProtocolType protocol, IContextMsgHandler handler )   {
        try {
            this.name     			= name;
            this.protocol 			= protocol;
            ctxMsgHandler           = handler;
            if( protocol != null ) {
                setServerSupport( port, protocol, handler  );
            }
            else
                System.out.println(name+"   |   CREATED no protocol"  );
        } catch (Exception e) {
            System.out.println(name+"   |   CREATE Error: " + e.getMessage()  );
        }
    }

    protected void setServerSupport(String port, ProtocolType protocol, IContextMsgHandler handler){
        switch (protocol){
            case tcp:
                serverTcp = new TcpServer("TcpServer", Integer.parseInt(port), handler);
                System.out.println(name+"   |   CREATED  on port=" + port + " protocol=" + protocol + " handler="+handler);
                break;
            case udp:
                //serverUdp = new UdpServer(...);
                break;
            case coap:
                break;
            case mqtt:
                break;
            default:
                System.out.println(name+"   |   Protocol Unknown");
                break;
        }
    }

    public String getName(){
        return name;
    }

    public boolean isActive(){
        return isactive;
    }

    @Override
    public void addComponent(String name, IApplMsgHandler handler) {
        ctxMsgHandler.addComponent(name, handler);
    }

    @Override
    public void removeComponent(String name) {
        ctxMsgHandler.removeComponent(name);
    }

    @Override
    public void activate() {
        if(!isactive){
            switch (protocol){
                case tcp:
                    serverTcp.activate();
                    isactive = true;
                    break;
                case udp:
                    break;
            }
        }
    }

    @Override
    public void deactivate() {
        if(!isactive)
            return;
        switch (protocol){
            case tcp:
                serverTcp.deactivate();
                isactive = false;
                break;
            case udp:
                break;
        }
    }
}
