package it.unibo.radarSystem22.domain.mock;


import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utility.BasicUtility;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;

public class SonarMock extends SonarModel implements ISonar {
	

	protected void computeDistance() {
		
		int v = dist.getVal();
		dist = new Distance(v-DomainSystemConfig.step);
		if(v <= 1 )
			state = false;
		BasicUtility.delay(DomainSystemConfig.sonarDelay);
	}
	
}
