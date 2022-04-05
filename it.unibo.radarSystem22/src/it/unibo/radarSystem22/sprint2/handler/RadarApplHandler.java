package it.unibo.radarSystem22.sprint2.handler;

import org.json.JSONException;
import org.json.JSONObject;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;

public class RadarApplHandler extends ApplMsgHandler {
	
	private IRadarDisplay radar;
	private int curDistance;
	private String angle;

	public RadarApplHandler(String name, IRadarDisplay radar) {
		super(name);
		this.radar = radar;
	}

	@Override
	public void elaborate(String message, Interaction conn) {
		System.out.println(message);
		if(message.equals("getCurDistance")) {
			try {
				conn.reply(""+curDistance);
			} catch (Exception e) {
				System.out.println("RadarApplHandler	|	Error on replay "+e.getMessage());
			}
			return;
		}
		else {
			System.out.println(message);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(message);
				
				curDistance = Integer.parseInt(jsonObject.get("distance").toString());
				angle = jsonObject.get("angle").toString();
				
			} catch (JSONException e) {
				System.out.println("RadarApplHandler	|	Error: "+e.getMessage());
			}
			radar.update(""+curDistance, angle);
	
			
		}
		
		
	}

}
