package it.unibo.radarSystem22.domain.utility;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DomainSystemConfig {
	
	public static boolean simulation	 	= true;
	public static boolean ledGui			= false;
	public static boolean testing			= false;
	
	public static int DLIMIT 				= 15;     
	public static int step 					= 1; //step for sonar data generator.
	public static int sonarDelay          	= 100;    
	public static int sonarMaxDist			= 150;
	
	public static void setTheConfiguration() throws JSONException {
			setTheConfiguration("../DomainSystemConfig.json");
		
	}
	
	public static void setTheConfiguration( String resourceName ) throws JSONException {

		FileReader fis = null;
		try {
			System.out.println("DomainSystemConfig	|	setTheConfiguration from file:" + resourceName);
			if(  fis == null ) {
 				 fis = new FileReader(new File(resourceName));
			}
	        JSONTokener tokener = new JSONTokener(fis);
	        JSONObject object   = new JSONObject(tokener);
	 		
	        
	        //webCam          = object.getBoolean("webCam");
	        //sonarObservable = object.getBoolean("sonarObservable");	
	        //tracing         = object.getBoolean("tracing");
	        
	        sonarDelay      = object.getInt("sonarDelay");	
	        sonarMaxDist 	= object.getInt("sonarMaxDist");	
	        DLIMIT          = object.getInt("DLIMIT");	
	        simulation          = object.getBoolean("simulation");
	        testing         = object.getBoolean("testing");
	        ledGui          = object.getBoolean("ledGui");
	        
 	        
		} catch (FileNotFoundException e) {
 			System.out.println("setTheConfiguration ERROR " + e.getMessage() );
		}

	}
	
}
