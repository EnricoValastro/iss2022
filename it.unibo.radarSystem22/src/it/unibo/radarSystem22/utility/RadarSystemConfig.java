package it.unibo.radarSystem22.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import it.unibo.comm2022.utility.ProtocolType;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RadarSystemConfig {
	
	public static boolean  RadarGuiRemote 	= false;
	public static boolean tracing         	= false;
	public static boolean testing         	= false;
	public static int DLIMIT              	= 15;
	
	public static int serverPort		 	= 8080;
	public static int ledPort			  	= 8010;
	public static int sonarPort			  	= 8015;
	public static String hostAddr		  	= "localhost";
	public static String raspAddr		  	= "localhost";
	public static ProtocolType protocolType = ProtocolType.tcp;

	
	
	public static void setTheConfiguration( String resourceName ) throws JSONException {

		FileReader fis = null;
		try {
			System.out.println("RadarSystemConfig	|	setTheConfiguration from file:" + resourceName);
			if(  fis == null ) {
 				 fis = new FileReader(new File(resourceName));
			}
	        JSONTokener tokener = new JSONTokener(fis);
	        JSONObject object   = new JSONObject(tokener);
	 		
	        //webCam          = object.getBoolean("webCam");
	        //sonarObservable = object.getBoolean("sonarObservable");	
	        //tracing         = object.getBoolean("tracing");
	        
	        
	        RadarGuiRemote      = object.getBoolean("RadarGuiRemote");		
	        testing         	= object.getBoolean("testing");
	        tracing          	= object.getBoolean("tracing");
	        DLIMIT          	= object.getInt("DLIMIT");
	        
	        serverPort			= object.getInt("serverPort");
	        hostAddr			= object.getString("hostAddr");
	        raspAddr			= object.getString("raspAddr");
 	        
		} catch (FileNotFoundException e) {
 			System.out.println("setTheConfiguration ERROR " + e.getMessage() );
		}

	}
	
	
	
	
}
