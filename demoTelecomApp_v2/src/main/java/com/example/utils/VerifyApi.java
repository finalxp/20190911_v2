package com.example.utils;

import org.apache.ibatis.javassist.expr.NewArray;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class VerifyApi {
	

	public static String verifyPost(String log_id,String name_id,String audio_base64){
		String loggingId = log_id;
		String identifier = name_id;
		String type = "text-dependent";
		String mode = "td_fuse_16_atn_v2";
		String samplingRate = "16000";
		String format = "pcm16";
		String usage_context = "default";
		String replay = "default";
		String base64 = audio_base64;

		JSONObject jsonObject = new JSONObject();
		/**
		 * serviceData
		 */

		// serviceData
		JSONObject jsonObject_ser = new JSONObject();
		jsonObject_ser.put("loggingId", loggingId);

		/**
		 * userData
		 */
		// userData
		JSONObject jsonObject_user = new JSONObject();
		jsonObject_user.put("identifier", identifier);

		/**
		 * processingInformation
		 */
		JSONObject jsonObject_pro = new JSONObject();

		/**
		 * processingInformation biometric
		 */
		// processingInformation_biometric
		JSONObject jsonObject_pro_bio = new JSONObject();
		jsonObject_pro_bio.put("type", type);
		jsonObject_pro_bio.put("mode", mode);
		jsonObject_pro.put("biometric", jsonObject_pro_bio);

		/**
		 * processingInformation audioCharacteristics
		 */
		// processingInformation_audioCharacteristics
		JSONObject jsonObject_pro_audio = new JSONObject();
		jsonObject_pro_audio.put("samplingRate", samplingRate);
		jsonObject_pro_audio.put("format", format);
		jsonObject_pro.put("audioCharacteristics", jsonObject_pro_audio);

		/**
		 * processingInformation metaInformation
		 */
		// processingInformation

		// processingInformation_metaInformation
		JSONArray jsonArray = new JSONArray();

		// processingInformation_metaInformation_usage-context
		JSONObject jsonObject_pro_met_usa = new JSONObject();
		jsonObject_pro_met_usa.put("key", "usage-context");

		// processingInformation_metaInformation_usage-context_value
		JSONObject jsonObjectjsonObject_pro_met_usa_val = new JSONObject();
		jsonObjectjsonObject_pro_met_usa_val.put("value", usage_context);
		jsonObjectjsonObject_pro_met_usa_val.put("encrypted", "false");
		jsonObject_pro_met_usa.put("value", jsonObjectjsonObject_pro_met_usa_val);
		// processingInformation_metaInformation_detect-replay-v2-16k
		jsonArray.add(jsonObject_pro_met_usa);

		// processingInformation_metaInformation_detect-replay-v2-16k
		JSONObject jsonObject_pro_met_det = new JSONObject();
		jsonObject_pro_met_det.put("key", "detect-replay-v2-16k");

		// processingInformation_metaInformation_detect-replay-v2-16k_value
		JSONObject jsonObject_pro_met_det_val = new JSONObject();
		jsonObject_pro_met_det_val.put("value", replay);
		jsonObject_pro_met_det_val.put("encrypted", "false");
		jsonObject_pro_met_det.put("value", jsonObject_pro_met_det_val);
		jsonArray.add(jsonObject_pro_met_det);

		/**
		 * processingInformation
		 */
		jsonObject_pro.put("biometric", jsonObject_pro_bio);
		jsonObject_pro.put("audioCharacteristics", jsonObject_pro_audio);
		jsonObject_pro.put("metaInformation", jsonArray);

		/*
		 * audioInput
		 */
		// audioInput_audio
		JSONObject jsonObject_aud_aud = new JSONObject();
		jsonObject_aud_aud.put("base64", base64);

		// audioInput
		JSONObject jsonObject_aud = new JSONObject();
		jsonObject_aud.put("secondsThreshold", "0");
		jsonObject_aud.put("audio", jsonObject_aud_aud);

		/**
		 * all put
		 */

		jsonObject.put("audioInput", jsonObject_aud);
		jsonObject.put("userData", jsonObject_user);
		jsonObject.put("serviceData", jsonObject_ser);
		jsonObject.put("processingInformation", jsonObject_pro);

		//System.out.println(jsonObject);
        return jsonObject.toString();
	}
}
