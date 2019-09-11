package ValidSoft;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Text20190724_json {
	public static void main(String[] args) {
		String loggingId = null;
		String identifier = null;
		String type = null;
		String mode = null;
		String samplingRate = null;
		String format = null;
		String usage_context = null;
		String replay = null;
		
		
		
		
		
		
		JSONObject jsonObject = new JSONObject();
		
		
		//serviceData
		JSONObject jsonObject_ser = new JSONObject();		
		jsonObject_ser.put("loggingId", loggingId);
		jsonObject.put("serviceData", jsonObject_ser);
		
		
		//userData
		JSONObject jsonObject_user = new JSONObject();				
		jsonObject_user.put("identifier", identifier);	
		jsonObject.put("userData", jsonObject_user);
		
		
		//processingInformation
		
		//processingInformation_metaInformation
		JSONArray jsonArray = new JSONArray();
		
		//processingInformation_metaInformation_usage-context
		JSONObject jsonObject_pro_met_usa = new JSONObject();
		jsonObject_pro_met_usa.put("key", "usage-context");
		
		//processingInformation_metaInformation_usage-context_value
		JSONObject jsonObjectjsonObject_pro_met_usa_val = new JSONObject();
		jsonObjectjsonObject_pro_met_usa_val.put("value", usage_context);
		jsonObjectjsonObject_pro_met_usa_val.put("encrypted", "false");
		
		//processingInformation_metaInformation_detect-replay-v2-16k
		JSONObject jsonObject_pro = new JSONObject();
		jsonArray.add(jsonObjectjsonObject_pro_met_usa_val);
		
		//processingInformation_metaInformation_detect-replay-v2-16k		
		JSONObject jsonObject_pro_met_det = new JSONObject();
		jsonObject_pro_met_det.put("key", "detect-replay-v2-16k");
		
		//processingInformation_metaInformation_detect-replay-v2-16k_value
		JSONObject jsonObject_pro_met_det_val = new JSONObject();
		jsonObject_pro_met_det_val.put("value", replay);
		jsonObject_pro_met_det_val.put("encrypted", "false");		
		jsonObject_pro_met_det.put("value", jsonObject_pro_met_det_val);
		
		//processingInformation_audioCharacteristics
		JSONObject jsonObject_pro_audio = new JSONObject();
		jsonObject_pro_audio.put("samplingRate", samplingRate);
		jsonObject_pro_audio.put("format", format);
		jsonObject_pro.put("audioCharacteristics", jsonObject_pro_audio);
		
		//processingInformation_biometric
		JSONObject jsonObject_pro_bio = new JSONObject();
		jsonObject_pro_bio.put("type", type);
		jsonObject_pro_bio.put("mode", mode);
		jsonObject_pro.put("biometric", jsonObject_pro_bio);
		
		//processingInformation put
		jsonObject.put("processingInformation", jsonObject_pro);
		
		
		//audioInput
		jsonObject.put("audioInput", "");
		//System.out.println(jsonObject);
		System.out.println("public static void main(String[] args) {");
	}
}
