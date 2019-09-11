package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
/**
 * 
 * @author leoli
 *
 */
public class StringGetJsonValue {
	
	public static List<String> stringGetJsonValue(String postRequest) {
		List<String> payload_parameter = new ArrayList<String>();
		Map<String, Object> map = JSONObject.fromObject(postRequest);
		System.out.println("map-->"+map);
		System.out.println("map.size--->"+map.size());
		if (map.get("serviceData") != null) {
			//get loggingId
			Object object_serviceData =  map.get("serviceData");
			System.out.println(object_serviceData);			
			Map<String, Object> map_serviceData = JSONObject.fromObject(object_serviceData);
			Object log_id = map_serviceData.get("loggingId");
			//get identifier
			Object object_userDate = map.get("userData");
			Map<String, Object> map_userDate = JSONObject.fromObject(object_userDate);
			Object name_id = map_userDate.get("identifier");
			//get audio
			Object object_audioInput =  map.get("audioInput");
			//System.out.println("object_audioInput---->"+object_audioInput);
			Map<String, Object> map_audioInput = JSONObject.fromObject(object_audioInput);
			
			Object audio = map_audioInput.get("audio");
			System.out.println("audio---->"+audio.toString());
			//get base64
			Map<String, Object> map_base64 = JSONObject.fromObject(audio);
			Object object_aduio = map_base64.get("base64");
		
			//add			
			payload_parameter.add(log_id.toString());
			payload_parameter.add(name_id.toString());
			payload_parameter.add(object_aduio.toString());
			
		}else {
			return null;
		}
		
		
		return payload_parameter ;
	}
}
