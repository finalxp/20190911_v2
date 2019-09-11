package com.example.text;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.utils.VerifyApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SpringBootTest
public class textJson {
	public static void main(String[] args) {
		
	}
	
	@Test
	public void verifyPost(){
		String loggingId = null;
		String identifier = null;
		String type = null;
		String mode = null;
		String samplingRate = null;
		String format = null;
		String channel_value = null;
		String replay_value = null;
		String mute_value = null;
		String snr_value = null;
		String secondsThreshold = null;
		String base64 = null;
		
		//0
		JSONObject jsonObject = new JSONObject();
		//1
		JSONObject jsonObject_serviceData = new JSONObject();
		JSONObject jsonObject_userData = new JSONObject();
		JSONObject jsonObject_processingInformation = new JSONObject();
		JSONObject jsonObject_audioInput = new JSONObject();
		//2
		JSONObject jsonObject_serviceData_loggingId = new JSONObject();
		JSONObject jsonObject_userData_identifier = new JSONObject();
		JSONObject jsonObject_processingInformation_biometric = new JSONObject();
		JSONObject jsonObject_processingInformation_audioCharacteristics = new JSONObject();
		JSONObject jsonObject_processingInformation_metaInformation = new JSONObject();
		JSONObject jsonObject_audioInput_secondsThreshold = new JSONObject();
		JSONObject jsonObject_audioInput_audio = new JSONObject();
		//3
		JSONObject jsonObject_processingInformation_biometric_type = new JSONObject();
		jsonObject_processingInformation_biometric_type.put("type", type);
		jsonObject_processingInformation_biometric_type.put("mode", mode);
		JSONObject jsonObject_processingInformation_biometric_mode = new JSONObject();
		JSONObject jsonObject_processingInformation_audioCharacteristics_samplingRate = new JSONObject();
		JSONObject jsonObject_processingInformation_audioCharacteristics_format = new JSONObject();
		JSONObject jsonObject_processingInformation_metaInformation_key1 = new JSONObject();
		JSONObject jsonObject_processingInformation_metaInformation_value1 = new JSONObject();
		JSONObject jsonObject_processingInformation_metaInformation_key2 = new JSONObject();
		JSONObject jsonObject_processingInformation_metaInformation_value2 = new JSONObject();
		JSONObject jsonObject_audioInput_audio_base64 = new JSONObject();
		//4
		JSONObject jsonObject_processingInformation_metaInformation_value1_value = new JSONObject();
		jsonObject_processingInformation_metaInformation_value1_value.put("value", channel_value);
		JSONObject jsonObject_processingInformation_metaInformation_value1_encrypted = new JSONObject();
		jsonObject_processingInformation_metaInformation_value1_encrypted.put("encrypted", "false");
		JSONObject jsonObject_processingInformation_metaInformation_value2_value = new JSONObject();
		jsonObject_processingInformation_metaInformation_value2_value.put("value", replay_value);
		JSONObject jsonObject_processingInformation_metaInformation_value2_encrypted = new JSONObject();
		jsonObject_processingInformation_metaInformation_value2_encrypted.put("encrypted", "false");
		
		//array
		JSONArray jsonArray_serviceData = new JSONArray();
		jsonArray_serviceData.add(jsonObject_serviceData_loggingId);
		JSONArray jsonArray_userData = new JSONArray();
		//jsonArray_userData.add(value)
		JSONArray jsonArray_processingInformation = new JSONArray();
		JSONArray jsonArray_audioInput = new JSONArray();
		
		
		//put
		jsonObject.put("serviceData", jsonArray_serviceData);
		jsonObject.put("userData", jsonObject_userData_identifier);
		//jsonObject.put("processingInformation", value);
		//jsonObject.put("audioInput", value);
		
		System.out.println(jsonObject.toString());
        
	}
}
