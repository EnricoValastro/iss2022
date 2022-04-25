package it.unibo.radarSystem22_s4.comm.interfaces;

public interface IContext {
	public void addComponent (String name, IApplMsgHandler handler);
	public void removeComponent (String name);
	public void activate();
	public void deactivate();
}
