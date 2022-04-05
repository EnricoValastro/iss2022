package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.interfaces.IDistance;

public class Distance implements IDistance {
	
	private int dist;
	 
	public Distance(int dist) {
		this.dist = dist;
	}
	
	@Override
	public int getVal() {
		return dist;
	}

	

}
