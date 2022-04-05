package it.unibo.radarSystem22.domain.models;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.SonarConcrete;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;

public abstract class SonarModel implements ISonar {
	
	protected boolean state;
	protected IDistance dist = new Distance(90);
	
	//factory
	public static ISonar create() {

		if(DomainSystemConfig.simulation)
			return createMock();
		else
			return createConcrete();
		
	}
	
	private static ISonar createMock() {
		System.out.println("SonarModel	|	SonarMock : created");
		return new SonarMock();
	}
	
	private static ISonar createConcrete() {
		System.out.println("SonarModel	|	SonarConcrete : created");
		return new SonarConcrete();
	}
	
	
	protected abstract void computeDistance();
	
	@Override
	public void activate() {
		dist = new Distance(90);
		state = true;
		new Thread() {
			
			public void run() {
				
				while(state) {
					computeDistance();
				}	
			}
			
		}.start();
	}

	@Override
	public void deactivate() {
		state = false;
	}

	@Override
	public IDistance getDistance() {
		return dist;
	}

	@Override
	public boolean isActive() {
		return state;
	}

}
