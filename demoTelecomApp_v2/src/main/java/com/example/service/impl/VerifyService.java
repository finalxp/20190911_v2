package com.example.service.impl;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.example.service.IVerifyService;
import com.example.utils.PostRequest;
import com.example.utils.ReturnResults;

@Service
public class VerifyService implements IVerifyService {

	@Override
	@SuppressWarnings("unchecked")
	public String sendPostToVerify(String payload) {
		String returnStringNull = null;
		String sss = null;
		Double replayScore = 0.0;
		
		//String sendPost = PostRequest.sendPost("http://192.168.18.236:4567/stdBiometricLite/verifySpeaker", payload);
		String sendPost = PostRequest.sendPost("http://127.0.0.1:4567/stdBiometricLite/verifySpeaker", payload);
		//String sendPost = PostRequest.sendPost("http://192.168.2.129:4567/stdBiometricLite/verifySpeaker", payload);
		
		Map<String, Object> map = JSONObject.fromObject(sendPost);

		if (map.get("result") != null) {
			Map<String, Object> map2 = JSONObject.fromObject(map.get("result"));
			JSONArray array = JSONArray.fromObject(map2.get("metaInformation"));
			for (Object object : array) {
				Map<String, Object> fromObject = JSONObject.fromObject(object);
				Map<String, Object> fromObject2 = JSONObject.fromObject(fromObject.get("value"));
				Object object2 = fromObject2.get("value");
				replayScore = Double.parseDouble((String) object2);
				System.out.println("replayScore--->" + replayScore);
			}

			//System.out.println("map2---->" + map2);
			//System.out.println("-------------<" + map2.get("score") + ">----------------");

			double score = Double.parseDouble((String) map2.get("score"));

			if (replayScore > 5.0 && score > 1.6) {
				sss = ReturnResults.ReReJson(1, map2.toString(), returnStringNull);
				return sss;
			} else {
				sss = ReturnResults.ReReJson(0, map2.toString(), returnStringNull);

			}

		}
		if (map.get("result") == null) {
			Map<String, Object> map3 = JSONObject.fromObject(map.get("errorData"));
			sss = ReturnResults.ReReJson(2, returnStringNull, map3.toString());
		}

		return sss;
	}

}
