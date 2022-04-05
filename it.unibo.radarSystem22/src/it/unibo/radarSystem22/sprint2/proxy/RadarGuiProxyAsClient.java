package it.unibo.radarSystem22.sprint2.proxy;

import it.unibo.comm2022.proxy.ProxyAsClient;
import it.unibo.comm2022.utility.ProtocolType;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;

public class RadarGuiProxyAsClient extends ProxyAsClient implements IRadarDisplay {

	public RadarGuiProxyAsClient(String name, String host, String entry, ProtocolType protocol) {
		super(name, host, entry, protocol);
	}
	
	public String getCurDistance() {
		String answer = sendRequestOnConnection("getCurDistance");
		return answer;
	}
	
	@Override
	public void update(String d, String a) {
		String msg = "{ \"distance\" : D , \"angle\" : A }".replace("D",d).replace("A",a);
		
		sendCommandOnConnection(msg);		
	}

}
