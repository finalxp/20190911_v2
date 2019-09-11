/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.proxy.Invoker;
import com.pccc.touda.proxy.api.Result;
import com.pccc.vprs.servicecustom.common.ReturnCode;
import com.pccc.vprs.servicedisplay.vprs.audio.service.audioService;
import com.pccc.vprs.servicedisplay.vprs.common.AbstractService;
import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-21 15:46:33
 *
 */
@Bizlet("nice声纹识别核心算法引擎接口")
public class NiceService extends AbstractService implements audioService{
	
	public DataObject enrolSpeaker(DataObject inMessage,DataObject outMessage){
		//首先获取参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String closeAudio=inMessage.getString("closeAudio");
		
		int resultCode = -1;
    	try {
//    		String serverURL = "https://"+PropertiesHandler.readValue("api.host");
//            String address = "/NiceConnectAPI/RTASelfService.svc/Rest/Enroll";//
    		//这里我们直接将其给定的地址拿出来 样例代码其实就是serverURL和adress拼接的 在spring-proxy中配置
    		String enrollURL="";
//    		String serverURL="指定的路径";
//    		String address="指定";
    		
            
        	Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        	//以下两个参数应该也是约定的
        	String LoginToken=clientLogin();
            paramMap.put("LoginToken",LoginToken);
            //这里注册前必须先调用得出CustomerId,phonenumber和CrmReferenceID这里我们该定义什么呢
            String  CustomerId=resolveCustomer(LoginToken,"phonenumber","CrmReferenceID");
            paramMap.put("CustomerId",CustomerId);//CustomerId
            
            //我们其实传入的报文要求应该就是base64位的
        //    String base64Code = audiotobase64.encodeBase64File(records);//D:/records/1.wav
            paramMap.put("AudioBase64",closeAudio);
            paramMap.put("AudioCodec","0");
            paramMap.put("SampleRate","16000");
            //-------------
            Map<String, Object> paramMapson = new HashMap<String, Object>();
            paramMapson.put("ExternalInteractionId","");//4986168434334333618948
            paramMapson.put("ExternalCustomerId","");//535534
            paramMapson.put("ExternalInteractionStartTimeLocal","/Date(1409202000000-0500)/");
            paramMapson.put("ExternalInteractionStartTimeGMT","/Date(1409202000000-0500)/");
            
            List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
            Map<String,Object> mapTemp = new HashMap<String,Object>();
            mapTemp.put("Key","customerId");//CustomerId
            mapTemp.put("Value","CustomerId");
            mapList.add(mapTemp);
            paramMapson.put("CustomData",mapList);
            
            paramMap.put("externalMetadata",paramMapson);
            String paramJson = JSONObject.toJSONString(paramMap);
            StringEntity reqEntity = new StringEntity(paramJson, "UTF-8");
//			//设置contentType为application/json
			reqEntity.setContentType("application/json; charset=UTF-8");
           
            
            //adress不知道用来干嘛的
			Result resultMap = Invoker.invoke("enrollURL", null,reqEntity); 
//			此处获取返回值resultMap中的值
			JSONObject obj = JSON.parseObject(resultMap.toString());
			//获取验证后的声纹得分，即相似度
			String resultCodeResult=obj.getJSONObject("ResultCode").toString();
			resultCode=Integer.parseInt(resultCodeResult);
			//用来查看返回值
			System.out.println(resultCode);
			
			String EnrollStatus=obj.getJSONObject("EnrollStatus").toString();
			
			//这边写的粗略，总之要根据返回的注册状态
			if(EnrollStatus=="2"){
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "注册成功");
				outMessage.set("registerStatus", "0");
			}else if(EnrollStatus=="1"){
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "注册需要更多音频");
				outMessage.set("registerStatus", "1");
			}
    	} catch (Exception e) {
    		e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "注册失败");
        } 
		return outMessage;
	}
	
	
	@Bizlet("调用nice接口注册无关文本声纹")
	public DataObject enrolSpeakerIndependent(DataObject inMessage,DataObject outMessage){
		 return outMessage;
	}
	
	public DataObject verifySpeakerIndependent(DataObject inMessage,DataObject outMessage,BigDecimal threshold){
		return outMessage;
	}
	
	/**
     * Client Login登录
     * @return:登陆成功返回LoginToken，登陆失败返回null
     * @throws Exception
     */
    public static String clientLogin(){
    	try {
//    		String serverURL = "https://"+PropertiesHandler.readValue("api.host");
//            String address = "/NICEConnectAPI/Login.svc/Rest/ClientLogin";
            
        	Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("OsLogin","login.user");
            paramMap.put("Password","login.password");
            String paramJson = JSONObject.toJSONString(paramMap);
            
            StringEntity reqEntity = new StringEntity(paramJson, "UTF-8");
//			//设置contentType为application/json
			reqEntity.setContentType("application/json; charset=UTF-8");
           
            
            //======spring.proxy没配
			Result resultMap = Invoker.invoke("tokenURL", null,reqEntity); 
			System.out.println(resultMap.toString());
            //此处获取返回值resultMap中的值
			JSONObject obj = JSON.parseObject(resultMap.toString());
			//获取验证后的声纹得分，即相似度
			String LoginToken=obj.getJSONObject("ResultCode").getJSONObject("Value").getJSONObject("LoginToken").toString();
//            return (String)value.get("LoginToken");
			return LoginToken;
    	} catch (Exception e) {
            //log.error("clientLogin error:",e);
    		e.printStackTrace();
        }
    	return null;
    }
	
    /**
     * resolveCustomer根据电话号码注册客户，返回客户ID
     * @param LoginToken:login登陆返回的token
     * @param phonenumber:主叫号码
     * @param CrmReferenceID：这个是必填项，暂可以用主叫号码
     * @return:成功返回客户ID，失败返回null
     * @throws Exception
     */
    public static String resolveCustomer(String LoginToken,String phonenumber,String CrmReferenceID){
    	try {
            
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("LoginToken",LoginToken);
            //-------------
            Map<String, Object> paramMapson = new HashMap<String, Object>();
            
            List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
            Map<String,Object> mapTemp = new HashMap<String,Object>();
            mapTemp.put("Key","21");
            mapTemp.put("Value",CrmReferenceID);
            mapList.add(mapTemp);
            
            paramMapson.put("Identifiers",mapList);
            
            paramMap.put("IdentificationParameters",paramMapson);
            String paramJson = JSONObject.toJSONString(paramMap);
      //      log.info("paramJson=="+paramJson);
            System.out.println(paramJson);
            StringEntity reqEntity = new StringEntity(paramJson, "UTF-8");
//			//设置contentType为application/json
			reqEntity.setContentType("application/json; charset=UTF-8");
           
            
            //======spring.proxy没配
			Result resultMap = Invoker.invoke("tokenURL", null,reqEntity); 
			System.out.println(resultMap.toString());
			JSONObject obj = JSON.parseObject(resultMap.toString());
			String value=obj.getJSONObject("Id").toString();
//            String value = (String) object.get("Id");
//            log.info("Id="+value);
            return value;
    	} catch (Exception e) {
           // log.error("resolveCustomer error:",e);
    		e.printStackTrace();
        } 
    	return null;
    }
	
	public DataObject verifySpeaker(DataObject inMessage,DataObject outMessage,BigDecimal threshold){
		return outMessage;
	}
	
	
	 
	public DataObject updateSpeaker(DataObject inMessage,DataObject outMessage){
		return outMessage;
	}
	
	public DataObject updateSpeakerIndependent(DataObject inMessage, DataObject outMessage) {
		return outMessage;
	}
	
	public DataObject compareSpeaker(DataObject inMessage,DataObject outMessage){
		return outMessage;
	}
	
	/**
	 * 声纹注册查询
	 */
	public DataObject isExistSpeaker(DataObject inMessage,DataObject outMessage){
		return outMessage;
	}


	@Override
	public DataObject verifySpeakerIndependentCQVoice(DataObject inMessage, DataObject outMessage) {
		// TODO 自动生成的方法存根
		return null;
	}


	public DataObject deleteSpeakerIndependent(DataObject inMessage, DataObject outMessage) {
		// TODO 自动生成的方法存根
		return null;
	}
	
}
