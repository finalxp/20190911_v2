/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.proxy.Invoker;
import com.pccc.touda.proxy.api.Result;
import com.pccc.vprs.servicecustom.common.ReturnCode;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicedisplay.vprs.audio.service.audioService;
import com.pccc.vprs.servicedisplay.vprs.common.AbstractService;
import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-15 15:27:56
 *
 */
@Bizlet("小I声纹识别核心算法引擎接口")
public class XiaoIService extends AbstractService implements audioService{
	@Bizlet("调用小I接口注册声纹")
	public DataObject enrolSpeaker (DataObject inMessage,DataObject outMessage){
//		首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String closeAudio=inMessage.getString("closeAudio");
		
		Map<String,Object> param=new HashMap<String,Object>();
	    //设置分组ID,与业务约定
		String groupid="groupid";
        param.put("groupid", groupid);
        
		//设置声纹ID，唯一标志  形式为用户姓名加证件类型加证件号码
        String systemid = userName+userType+certNo;
		param.put("systemid", systemid);
		
		//设置本次语音数据流水号
		String callid="callid";
		param.put("callid", callid);
		
		//设置本次语音二进制数据,此处模仿人脸识别，前台传给我音频的base64格式文件
		String buffer=closeAudio;
		
		if(buffer==null){
		   outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
		   outMessage.set("returnMsg", "输入参数必输项丢失");
		   return outMessage;
		}
		
		//将param参数和音频文件buffer传送给调用地址
//		String uuidA = VPRSUtils.getUUID();
//		String directoryA = AudioFileUtils.crtAudioPathByUUID(uuidA);
		byte[] bufferBinary=null;
		try {
			//将base64编码格式的音频文件解码为二进制文件
			bufferBinary=AudioFileUtils.convertBase64DataToAudioNoAdress(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
			outMessage.set("returnMsg", "base64解码文件保存到指定目录操作异常");
			return outMessage;
		}
		
//		String filepathA = directoryA;// 音频A路径
		
		try{
			MultipartEntityBuilder builder = MultipartEntityBuilder.create(); 
			builder.addBinaryBody("audio", bufferBinary);  		  
			HttpEntity entity = builder.build();// 生成 HTTP POST 实体    
			
			//这边好像拿不到值，result返回为空
			String result=invoke2("call_ivr_register",param,entity).getString();
			//获取返回值
			JSONObject jsonOb = JSONObject.parseObject(result);
			System.out.println(result);
//			BigDecimal similarity = (BigDecimal) jsonOb.getJSONObject("result_idcard").get("confidence");
			//将返回值拿出来
			String code=(String)jsonOb.getJSONObject("code").get("code");
			int coderesult=Integer.parseInt(code);
			//如果是1，则递归调用
			if(coderesult==1){
				enrolSpeaker(inMessage,outMessage);
			}
		}catch(Exception e){
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "调用远鉴注册服务异常");
			e.printStackTrace();
		}

		return outMessage;
	}
	
	
	@Bizlet("调用小I接口注册无关文本声纹")
	public DataObject enrolSpeakerIndependent(DataObject inMessage,DataObject outMessage){
		 return outMessage;
	}

	public DataObject verifySpeakerIndependent(DataObject inMessage,DataObject outMessage,BigDecimal threshold){
		return outMessage;
	}
	
	
	
	
	
	//此处验证是对模型的验证更新（注册中用于比较）   ====剑川说有点问题先等一下
	@Bizlet("调用小I接口验证声纹")
	public DataObject verifySpeaker (DataObject inMessage,DataObject outMessage,BigDecimal threshold){
        //首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String closeAudio=inMessage.getString("closeAudio");
		
		Map<String,Object> param=new HashMap<String,Object>();
	    //设置分组ID,与业务约定
		String groupid="groupid";
        param.put("groupid", groupid);
        
		//设置声纹ID，唯一标志  形式为用户姓名加证件类型加证件号码
        String systemid = userName+userType+certNo;
		param.put("systemid", systemid);
		
		//设置本次语音数据流水号
		String callid="callid";
		param.put("callid", callid);
		
		//设置本次语音二进制数据,此处模仿人脸识别，前台传给我音频的base64格式文件
		String buffer=closeAudio;
		
		if(buffer==null){
		   outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
		   outMessage.set("returnMsg", "输入参数必输项丢失");
		   return outMessage;
		}
		
		//将param参数和音频文件buffer传送给调用地址
		String uuidA = VPRSUtils.getUUID();
		String directoryA = AudioFileUtils.crtAudioPathByUUID(uuidA);
		byte[] bufferBinary=null;
		try {
			//将base64编码格式的音频文件解码为二进制文件
			AudioFileUtils.convertBase64DataToAudio(buffer,directoryA);
		} catch (IOException e) {
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
			outMessage.set("returnMsg", "base64解码文件保存到指定目录操作异常");
			return outMessage;
		}
		
		String filepathA = directoryA;// 音频A路径
		
		try{
			MultipartEntityBuilder builder = MultipartEntityBuilder.create(); 
			builder.addBinaryBody("audio", bufferBinary);  		  
			HttpEntity entity = builder.build();// 生成 HTTP POST 实体    
			
			//这边好像拿不到值，result返回为空
			String result=invoke2("call_ivr_register",param,entity).getString();
			//获取返回值
			JSONObject jsonOb = JSONObject.parseObject(result);
			System.out.println(result);
//			BigDecimal similarity = (BigDecimal) jsonOb.getJSONObject("result_idcard").get("confidence");
			//将返回值拿出来
			String code=(String)jsonOb.getJSONObject("code").toString();
			int coderesult=Integer.parseInt(code);
			//如果是1，则递归调用
			if(coderesult==1){
				enrolSpeaker(inMessage,outMessage);
			}
		}catch(Exception e){
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "调用远鉴注册服务异常");
			e.printStackTrace();
		}

		return outMessage;
	}
	
	@Bizlet("调用小I接口更新声纹")
	public DataObject updateSpeaker (DataObject inMessage,DataObject outMessage){
        //首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String closeAudio=inMessage.getString("closeAudio");
		
		Map<String,Object> param=new HashMap<String,Object>();
	    //获取分组ID  =====================
		String groupid="groupid";
        param.put("groupid", groupid);
        
		//设置声纹ID，唯一标志  形式为用户姓名加证件类型加证件号码
        String systemid = userName+userType+certNo;
		param.put("systemid", systemid);
		//invoke调用服务，spring-proxy待配置    =======================
		Result result=Invoker.invoke("call_ivr_update", null,param);
		System.out.println(result.toString());
		JSONObject jsonOb = JSONObject.parseObject(result.toString());
		//将返回值拿出来
		String code=(String)jsonOb.getJSONObject("code").toString();
		if(code=="1"){
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "声纹模型更新失败");
		}else if(code=="0"){
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "声纹模型更新成功");
		}
		return outMessage;
	}
	
	
	public DataObject updateSpeakerIndependent(DataObject inMessage, DataObject outMessage) {
		return outMessage;
	}
	
	
	@Bizlet("调用小I接口比较声纹")
	public DataObject compareSpeaker(DataObject inMessage,DataObject outMessage){
        //首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String closeAudio=inMessage.getString("closeAudio");
		
		
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
