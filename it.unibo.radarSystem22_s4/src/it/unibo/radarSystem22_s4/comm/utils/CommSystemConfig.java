package it.unibo.radarSystem22_s4.comm.utils;

import it.unibo.radarSystem22_s4.comm.ProtocolType;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class CommSystemConfig {

    public static  String mqttBrokerAddr = "tcp://localhost:1883";
    public static int serverTimeOut        =  600000;
    public static ProtocolType protcolType = ProtocolType.tcp;
    public static boolean tracing          = false;

    public static void setTheConfiguration(  ) {
        setTheConfiguration("../CommSystemConfig.json");
    }

    public static void setTheConfiguration(String resourceName)  {
        FileReader fis = null;
        try {
            System.out.println("---- CommSystemConfig    |   setting configuration from file: " + resourceName +" ----");
            if(  fis == null ) {
                fis = new FileReader(new File(resourceName));
            }

            JSONTokener tokener = new JSONTokener(fis);
            JSONObject object   = new JSONObject(tokener);

            mqttBrokerAddr   = object.getString("mqttBrokerAddr");
            tracing          = object.getBoolean("tracing");

            switch( object.getString("protocolType") ) {
                case "tcp"  : protcolType = ProtocolType.tcp; break;
                case "coap" : protcolType = ProtocolType.coap; break;
                case "mqtt" : protcolType = ProtocolType.mqtt; break;
            }

        } catch (Exception e) {
            System.out.println("CommSystemConfig    |   setting configuration ERROR " + e.getMessage() );
        }
    }



}
