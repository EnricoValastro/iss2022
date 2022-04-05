package it.unibo.radarSystem22.sprint2a.proxy;

import it.unibo.comm2022.proxy.ProxyAsClient;
import it.unibo.comm2022.utility.ProtocolType;
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;

public class SonarProxyAsClient extends ProxyAsClient implements ISonar{

	public SonarProxyAsClient(String name, String host, String entry, ProtocolType protocol) {
		super(name, host, entry, protocol);
		
	}

	@Override
	public void activate() {
		sendCommandOnConnection("activate");
	}

	@Override
	public void deactivate() {
		sendCommandOnConnection("deactivate");
		super.close();
	}

	@Override
	public IDistance getDistance() {
		String asw = sendRequestOnConnection("getDistance");
		return new Distance(Integer.parseInt(asw));
	}

	@Override
	public boolean isActive() {
		String asw = sendRequestOnConnection("isActive");
		if(asw.equals("true")) {
			return true;
		}
		return false;
	}
}
