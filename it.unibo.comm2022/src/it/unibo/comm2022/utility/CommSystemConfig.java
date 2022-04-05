package it.unibo.comm2022.utility;

import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class CommSystemConfig {
	
	public static String mqttBrokerAddr = "tcp://localhost:1883";
	public static ProtocolType protocolType = ProtocolType.tcp;
	public static int serverTimeout = 600000;
	
	public static void setTheConfiguration(String resourceName ) {

		FileReader fis = null;
		try {
			System.out.println("DomainSystemConfig	|	setTheConfiguration from file:" + resourceName);
			if(  fis == null ) {
 				 fis = new FileReader(new File(resourceName));
			}
	        JSONTokener tokener = new JSONTokener(fis);
	        JSONObject object   = new JSONObject(tokener);
	        
	        switch(object.getString("protocolType")){
	        	case "tcp":		protocolType = ProtocolType.tcp; break;
	        	case "udp":		protocolType = ProtocolType.udp; break;
	        	case "coap":	protocolType = ProtocolType.coap; break;
	        	case "mqtt":	protocolType = ProtocolType.mqtt; break;
	        }
	        
	        //serverTimeout = object.getInt("serverTimeout");
	        
	        mqttBrokerAddr = object.getString("mqttBrokerAddr");
 	        
		} catch (Exception e) {
 			System.out.println("setTheConfiguration ERROR " + e.getMessage() );
		}

	}
}
	


