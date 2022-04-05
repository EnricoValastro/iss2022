package it.unibo.radarSystem22.domain.concrete;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utility.DomainSystemConfig;

public class SonarConcrete extends SonarModel implements ISonar {
	
	private BufferedReader reader;
	private Process p;
	
	
	@Override
	public void activate() {
		if(p==null) {
			try {
				p = Runtime.getRuntime().exec("sudo ./SonarAlone");
				reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				System.out.println("SonarConcrete	|	Sonar setup DONE");
			} catch (Exception e) {
				System.out.println("SonarConcrete	|	Error on Sonar setup");
			}
			
		}
		super.activate();
	}

	@Override
	protected void computeDistance() {
		try {
			String data = reader.readLine();
			if(data == null) 
				return;
			int d = Integer.parseInt(data);
			int lastVal = dist.getVal();
			if(d != lastVal && d <= DomainSystemConfig.sonarMaxDist)
				dist = new Distance(d);

		} catch (Exception e) {
			System.out.println("SonarConcrete	|	"+e.getMessage());
		}
	}
	
	@Override
	public void deactivate() {
		if(p != null) {
			p.destroy();
			p = null;
		}
		super.deactivate();
	}

}
