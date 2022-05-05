package it.unibo.radarSystem22_s4.appl;

import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22_s4.comm.ProtocolType;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class RadarSystemConfig {

    public static boolean tracing         = false;
    public static boolean testing         = false;
    public static int DLIMIT              =  15;
    public static boolean  RadarGuiRemote = false;

    public static String hostAddr         = "localhost";
    public static String raspAddr         = "localhost";

    //Aggiunte dello SPRINT4
    public static ProtocolType protcolType= ProtocolType.tcp;
    public static int  ctxServerPort      = 8018;

    public static void setTheConfiguration(  ) {
        setTheConfiguration("../RadarSystemConfig.json");
    }

    public static void setTheConfiguration( String resourceName ) {

        FileReader fis = null;
        try {
            System.out.println("RadarSystemConfig   |   setTheConfiguration from file:" + resourceName);
            if(  fis == null ) {
                fis = new FileReader(new File(resourceName));
            }
	        JSONTokener tokener = new JSONTokener(fis);
            JSONObject object   = new JSONObject(tokener);

            tracing          = object.getBoolean("tracing");
            testing          = object.getBoolean("testing");
            DLIMIT           = object.getInt("DLIMIT");
            RadarGuiRemote   = object.getBoolean("RadarGuiRemote");
            //Aggiunte dello SPRINT4
            ctxServerPort    = object.getInt("ctxServerPort");
            switch( object.getString("protocolType") ) {
                case "tcp"  : protcolType = ProtocolType.tcp; break;
                case "coap" : protcolType = ProtocolType.coap; break;
                case "mqtt" : protcolType = ProtocolType.mqtt; break;
            }
        } catch (Exception e) {
            System.out.println("RadarSystemConfig   |   setTheConfiguration ERROR " + e.getMessage());
        }

    }

}
