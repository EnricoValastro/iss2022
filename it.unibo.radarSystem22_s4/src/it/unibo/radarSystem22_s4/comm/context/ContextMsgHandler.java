package it.unibo.radarSystem22_s4.comm.context;

import it.unibo.radarSystem22_s4.comm.ApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMessage;
import it.unibo.radarSystem22_s4.comm.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.IContextMsgHandler;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;

import java.util.HashMap;

public class ContextMsgHandler extends ApplMsgHandler implements IContextMsgHandler {

    private HashMap<String, IApplMsgHandler> handlerMap = new HashMap<String,IApplMsgHandler>();

    public ContextMsgHandler(String name) {
        super(name);
    }

    @Override
    public void elaborate(IApplMessage message, Interaction2021 conn) {

        String receiver = message.msgReceiver();
        IApplMsgHandler h = getHandler(receiver);
        if ( h != null && (!message.isReply())){
            System.out.println(this.name+ "   |   message redirected to handler: "+h.getName());
            h.elaborate(message, conn);
        }
        else{
            System.out.println(this.name+ "   |   Receiver: "+receiver+" unknown");
        }

    }

    @Override
    public void addComponent(String name, IApplMsgHandler h) {
        System.out.println(this.name + "   |   Handler " + name + " registered");
        handlerMap.put(name,h);
    }

    @Override
    public void removeComponent(String name) {
        System.out.println(this.name + "   |   Handler " + name + " removed");
        handlerMap.remove(name);
    }

    @Override
    public IApplMsgHandler getHandler(String name) {
        return handlerMap.get(name);
    }
}
