package com.example.utils;

import java.util.List;

import com.example.model.Text;

import net.sf.json.JSONObject;

public class ReturnResults {
	public static String ReReJson(int code,String result,String errorData){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("result", result);
		jsonObject.put("errorData", errorData);
		
		
		return jsonObject.toString();
		
	}
	public static String ReReJson(int code,List<Text> result,String errorData){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("result", result);
		jsonObject.put("errorData", errorData);
			
		return jsonObject.toString();
		
	}
}
