package cn.productivetech.shtelcom.enrol.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
	
	public static String toJSONString(Object obj) {
		ObjectMapper om = new ObjectMapper();
		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	
	public static String toJSON(Object obj) {
		ObjectMapper om = new ObjectMapper();
		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
