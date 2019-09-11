package com.validsoft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.pccc.touda.common.util.ConfigUtils;
import com.pccc.vprs.servicecustom.kafka.KafukaSender;

public class demo {
	public static void main(String[] args) {
		pushToKafka();
	
	}
	
	private static void pushToKafka(){ 
		JSONObject sendObject = new JSONObject(); 
		List<JSONObject> sendList = new ArrayList<JSONObject>(); 
		KafukaSender sender = new KafukaSender(ConfigUtils.getProperty("stream.topic")); 

		
		sendObject.put("ReturnCode", "aaaaa"); 
		sendObject.put("RiskLevel", "aaaaa"); 
		sendObject.put("UserUniqueId", "aaaaa"); 
		sendObject.put("ReturnMsg", "aaaaa"); 
		sendObject.put("UserName", "aaaaa"); 
		sendObject.put("UserType", "aaaaa"); 
		sendObject.put("PhoneNo", "aaaaa"); 
		sendObject.put("CertNo","aaaaa"); 
		sendObject.put("CertType","aaaaa"); 
		sendObject.put("Channel","aaaaa"); 
		sendObject.put("BusinessType","aaaaa"); 
		sendObject.put("CloseAudioName","aaaaa"); 
		sendObject.put("BatchTime","aaaaa"); 
		sendObject.put("City","aaaaa"); 
		sendObject.put("TransTime","aaaaa"); 
		//String TransTimeStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(transDetail.getTransTime()) + "+0800"; 
		sendObject.put("TransTimeStr", "aaaaa"); 
		sendObject.put("TransCode", "aaaaa"); 
		sendObject.put("TransDate", "aaaaa"); 
		
		
		
		/*sendObject.put("ReturnCode", transDetail.getReturnCode()); 
		sendObject.put("RiskLevel", transDetail.getRiskLevel()); 
		sendObject.put("UserUniqueId", transDetail.getUserUniqueId()); 
		sendObject.put("ReturnMsg", transDetail.getReturnMsg()); 
		sendObject.put("UserName", transDetail.getUserName()); 
		sendObject.put("UserType", transDetail.getUserType()); 
		sendObject.put("PhoneNo", transDetail.getPhoneNo()); 
		sendObject.put("CertNo",transDetail.getCertNo()); 
		sendObject.put("CertType",transDetail.getCertType()); 
		sendObject.put("Channel",transDetail.getChannel()); 
		sendObject.put("BusinessType",transDetail.getBusinessType()); 
		sendObject.put("CloseAudioName",transDetail.getReserveCoulumn1()); 
		sendObject.put("BatchTime",transDetail.getReserveCoulumn2()); 
		sendObject.put("City",transDetail.getReserveCoulumn3()); 
		sendObject.put("TransTime", transDetail.getTransTime()); 
		String TransTimeStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(transDetail.getTransTime()) + "+0800"; 
		sendObject.put("TransTimeStr", TransTimeStr); 
		sendObject.put("TransCode", transDetail.getTransCode()); 
		sendObject.put("TransDate", transDetail.getTransDate()); */

		sendList.add(sendObject); 
		sender.accept(sendList); 

		}
}
