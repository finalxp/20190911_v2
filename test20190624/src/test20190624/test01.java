package test20190624;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.NewBeanInstanceStrategy;

public class test01 {
	public static void main(String[] args) {

		String loggingId = "20190717";
		String identifier = "20190717-1";
		String type = "text-independent";
		String mode = "ti_plp2covv2";
		int samplingRate = 16000;
		String format = "pcm16";
		String channel_value = "defalt";
		String replay_value = "5";
		String mute_value = "125";
		String snr_value = "10";
		String secondsThreshold = "0";
		String base64 = "AAAA";

		//root
		JSONObject jsonObject = new JSONObject();
		//serviceData
		JSONObject jsonObject_serviceData = new JSONObject();
		jsonObject_serviceData.put("loggingId", loggingId);
		//userData
		JSONObject jsonObject_userData = new JSONObject();
		jsonObject_userData.put("identifier", identifier);
		//processingInformation
		JSONObject jsonObject_processingInformation = new JSONObject();
		JSONObject jsonObject_processingInformation_bio = new JSONObject();
		//biometric
		jsonObject_processingInformation_bio.put("type", type);
		jsonObject_processingInformation_bio.put("mode", mode);
		jsonObject_processingInformation.put("biometric", jsonObject_processingInformation_bio);
		JSONObject jsonObject_processingInformation_aud = new JSONObject();
		//audioCharacteristics
		jsonObject_processingInformation_aud.put("samplingRate", samplingRate);
		jsonObject_processingInformation_aud.put("format", format);
		jsonObject_processingInformation.put("biometric", jsonObject_processingInformation_aud);
		//metaInformation
		JSONObject pro_meta = new JSONObject();
		JSONArray jsonArray_meta = new JSONArray();
		//array1		
		//"key":"usage-context"
		JSONObject jsonObject_pro_meta_key1 = new JSONObject();
		jsonObject_pro_meta_key1.put("key", "usage-context");
		//"value":"ivr"
		//"encrypted":"false"
		JSONObject jsonObject_pro_meta_value1 = new JSONObject();
		jsonObject_pro_meta_value1.put("value", channel_value);
		jsonObject_pro_meta_value1.put("encrypted","false");
		//add
		jsonArray_meta.add(jsonObject_pro_meta_key1);
		jsonArray_meta.add(jsonObject_pro_meta_value1);	
		//array2
		//"key":"detect-replay-v2-16k"
		JSONObject jsonObject_pro_meta_key2 = new JSONObject();
		jsonObject_pro_meta_key2.put("key", "detect-replay-v2-16k");
		//"value":"default"
		//"encrypted":"false"
		JSONObject jsonObject_pro_meta_value2 = new JSONObject();
		jsonObject_pro_meta_value2.put("value", replay_value);
		jsonObject_pro_meta_value2.put("encrypted","false");
		//add
		jsonArray_meta.add(jsonObject_pro_meta_key2);
		jsonArray_meta.add(jsonObject_pro_meta_value2);	
		
		//audioInput
		JSONObject jsonObject_audio = new JSONObject();
		//secondsThreshold
		jsonObject_audio.put("secondsThreshold",secondsThreshold);
		//audio
		//base64
		JSONObject jsonObject_audio_aud_base = new JSONObject();
		jsonObject_audio_aud_base.put("base64",base64);
		// put
		jsonObject.put("serviceData", jsonObject_serviceData);
		jsonObject.put("userData", jsonObject_userData);
		jsonObject.put("processingInformation", jsonArray_meta);
		jsonObject.put("audioInput", jsonObject_audio);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		

		System.out.println(jsonObject.toString());

/*		JSONObject obj1 = new JSONObject();
		obj1.put("姓名", "张三");
		obj1.put("年龄", "18");
		JSONObject obj2 = new JSONObject();
		obj2.put("姓名", "李四");
		obj2.put("年龄", "19");
		JSONObject obj3 = new JSONObject();
		obj3.put("姓名", "王五");
		obj3.put("年龄", "20");
		JSONArray array = new JSONArray();
		array.add(obj1);
		array.add(obj2);
		array.add(obj3);

		JSONObject obj = new JSONObject();
		obj.put("班级", "4134170801");
		obj.put("班级人数", "27");
		obj.put("学生", array);

		System.out.println(obj.toString());*/

	}
}
