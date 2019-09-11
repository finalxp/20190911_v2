/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.service.impl;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.common.Version;
import com.pccc.touda.common.util.ConfigUtils;
import com.pccc.touda.proxy.Invoker;
import com.pccc.touda.proxy.ProxyContext;

import com.pccc.touda.proxy.api.Result;
import com.pccc.vprs.servicecustom.common.ReturnCode;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.FastDFSClientAlone;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicedisplay.bams.util.TimeUtils;
import com.pccc.vprs.servicedisplay.vprs.audio.AudioDetect;
import com.pccc.vprs.servicedisplay.vprs.audio.service.audioService;
import com.pccc.vprs.servicedisplay.vprs.common.AbstractService;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.ext.infra.security.BASE64Encoder;

import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-15 15:27:38
 *
 */
@Bizlet("validsoft声纹识别核心算法引擎接口")
public class ValidSoftService extends AbstractService implements audioService{
	private static ILogger logger = LoggerFactory.getLogger(AudioDetect.class);
	
	public static final String register_audio_duration_threshold = ConfigUtils
			.getProperty("registerAudioDurationThreshold");
	
	public static final String verify_audio_duration_threshold = ConfigUtils
			.getProperty("verifyAudioDurationThreshold");
	
	@Bizlet("调用ValidSoft接口注册固定文本声纹")
	public DataObject enrolSpeaker(DataObject inMessage,DataObject outMessage){
		
		Map<String,String> closeAudioInfo=convertCloseAudio(inMessage, "tdregister");
		try {
			StringEntity reqEntity = new StringEntity(createPayLoadTd(inMessage,false,closeAudioInfo), "UTF-8");
//			//设置contentType为application/json
			reqEntity.setContentType("application/json; charset=UTF-8");
			Result result = Invoker.invoke("enrolSpeaker", null,reqEntity);
			//获取HttpResult 中的result
			Object resultObj=result.get();
			
			if(resultObj==null) {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
                outMessage.set("returnMsg", "注册失败");
                return outMessage;
			}
			
	        JSONObject outMess = JSONObject.parseObject(resultObj.toString());
	        
	        if(outMess.get("errorData")!=null)
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "validSoft注册失败"+"code:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("code")+"description:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
//			    outMessage.set("registerStatus", "0");
				return outMessage;
	        }else
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "注册成功");
//			    outMessage.set("registerStatus", "0");
				return outMessage;
	        }

		} catch (Exception e) {
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "注册失败");
			return outMessage;
		}
	}
	
	/**
	 * 设置报文参数 （固定文本）
	 * @param inMessage   输入报文
	 * @param processType 操作流程类型
	 * @return
	 */
	public String createPayLoadTd(DataObject inMessage,Boolean processType,Map<String,String> closeAudioMap) {
		// 首先获取报文传递的参数
				String userName = inMessage.getString("userName");
				String userType = inMessage.getString("userType");
				String certType = inMessage.getString("certType");
				String certNo = inMessage.getString("certNo");
//				String closeAudio = inMessage.getString("closeAudio");  
//				String channel=inMessage.getString("channel");

				//报文修改后再操作
				String audioSamplingRate=inMessage.getString("audioSamplingRate");
				String audioEncodeFormat=inMessage.getString("audioEncodeFormat");
				
				String base64Audio=closeAudioMap.get("base64");
				
				String directory=closeAudioMap.get("directory");
				
				
				// 将url下载后音频的存放位置给报文，以便后面存入数据库
				inMessage.set("closeAudio", directory);
				
//				//获取最近音频的格式 path/url/base64
//				String closeAudioFormat = inMessage.getString("closeAudioFormat"); 
				
				
//				// 当传递方式为url时（从ToudaDfs下载）
//				if (closeAudioFormat.equals("url")) {
//					String directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
//					try {
//						AudioFileUtils.downLoadFromUrl(closeAudio, directory);
//					} catch (IOException e) {
//						e.printStackTrace();
//						logger.error("从url下载文件存储到指定路径出错"+e.getMessage());
//					}
//					// 将url下载后音频的存放位置给报文，以便后面存入数据库
//					inMessage.set("closeAudio", directory);
//					
//					//将音频文件转化为base64
//					base64Audio=AudioFileUtilsByValidSoft.fileToBase64(directory);
//				}
//				// 当传递方式为path时
//				else if(closeAudioFormat.equals("path")) {
//					String directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
//					try {
//						AudioFileUtils.copyToPath(closeAudio, directory);
//					} catch (Exception e) {
//						e.printStackTrace();
//						logger.error("从path拷贝文件存储到指定路径出错"+e.getMessage());
//					}
//					// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
//					inMessage.set("closeAudio", directory);
//					
//					//将音频文件转化为base64
//					base64Audio=AudioFileUtilsByValidSoft.fileToBase64(directory);
//				}
//				// 当传递方式为base64时
//				else if (closeAudioFormat.equals("base64")) {
//					base64Audio = closeAudio;
//					try {
//						String directory = AudioFileUtils.crtAudioPathByUUID(VPRSUtils.getUUID(), channel);
//						logger.info("个人近声存储路径：" + directory);
//						//需要将validsoft解码生成wav文件
//						AudioFileUtils.convertBase64DataToAudio(closeAudio, directory);
//						// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
//						inMessage.set("closeAudio", directory);
//					} catch (Exception e) {
//						logger.info("存储音频文件到文件服务器出错");
//					}
		//
//				}
				
				
				//将非base64转化为base64???这块要思考一下 怎么写代码更规范 更容易扩展

				// 设置日志ID
				String loggingTime = TimeUtils.generateTimestamp();
				String loggingId = certNo + loggingTime; 
				
				Map<String, Object> serviceDataMap = new HashMap<String, Object>();
				serviceDataMap.put("loggingId", loggingId);

				// 设置用户ID，形式为用户姓名加证件类型加证件号码
				String identifier = certType + certNo;
				Map<String, Object> userDataMap = new HashMap<String, Object>();
				userDataMap.put("identifier", identifier);

				// 设置文本相关还是文本无关 此处根据渠道服务类型约定设定		
				String type = "text-dependent";
				
				// 设置模式 厂商给定的值
				String mode = ""; 
				// 设置采样率
				String samplingRate = "";
				if("8000".equals(audioSamplingRate)) {
					mode="td_fuse_8_atn_v2";
					samplingRate="8000";
				}else if("16000".equals(audioSamplingRate)) {
					mode="td_fuse_16_atn_v2";
					samplingRate="16000";
				}
				
				Map<String, Object> biometricMap = new HashMap<String, Object>();
				biometricMap.put("type", type);
				biometricMap.put("mode", mode);
				
				// 设置语音格式
				String format="";
				if("pcm16".equals(audioEncodeFormat)) {
					format = "pcm16";
				}else if("alaw".equals(audioEncodeFormat)) {
					format = "alaw";
				}else if("ulaw".equals(audioEncodeFormat)) {
					format = "ulaw";
				}  
				
				Map<String, Object> audioCharacteristicsMap = new HashMap<String, Object>();
				audioCharacteristicsMap.put("samplingRate", samplingRate);
				audioCharacteristicsMap.put("format", format);

				Map<String, Object> processingInformationMap = new HashMap<String, Object>();
				processingInformationMap.put("biometric", biometricMap);
				processingInformationMap.put("audioCharacteristics", audioCharacteristicsMap);

				//TD 需要防重播
//				if(processType) {
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("value", "default");
				valueMap.put("encrypted", "false");
				Map<String, Object> metaInformationMap = new HashMap<String, Object>();
				metaInformationMap.put("key", "usage-context");
				metaInformationMap.put("value", valueMap);
				
				Map<String, Object> valueMapDetect = new HashMap<String, Object>();
				valueMapDetect.put("value", "true");
				valueMapDetect.put("encrypted", "false");
				Map<String, Object> metaInformationMapDetect = new HashMap<String, Object>();
				metaInformationMapDetect.put("key", "detect-replay");
				metaInformationMapDetect.put("value", valueMapDetect);
				
				
				// 设置meta信息
				List<Map<String, Object>> metaInformationList = new ArrayList<Map<String, Object>>();
				metaInformationList.add(metaInformationMap);
				metaInformationList.add(metaInformationMapDetect);
				processingInformationMap.put("metaInformation", metaInformationList);
//				}
				
				// 设置语音相关信息
				Map<String, Object> audioMap = new HashMap<String, Object>();
				// 放入语音，语音为base64编码格式
				audioMap.put("base64", base64Audio);
				Map<String, Object> audioInputMap = new HashMap<String, Object>();
				
				
				// 设置采集多长时间的语音
				String secondsThreshold = "0";	
				
				audioInputMap.put("secondsThreshold", secondsThreshold);
				audioInputMap.put("audio", audioMap);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("serviceData", serviceDataMap);
				map.put("userData", userDataMap);
				map.put("processingInformation", processingInformationMap);
				map.put("audioInput", audioInputMap);

				// 转为JSON字符串
				String jsonString = JSONObject.toJSONString(map);
				return jsonString;
	}
	
	
	
	/**
	 * 设置报文参数 （随意说）
	 * @param inMessage   输入报文
	 * @param processType 操作流程类型    随意说注册与更新和验证操作不同,需腰多传两位参数
	 * @return
	 */
	public String createPayLoadFs(DataObject inMessage,boolean processType,Map<String,String> closeAudioMap) {
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
//		String closeAudio = inMessage.getString("closeAudio");  
		String channel=inMessage.getString("channel").toUpperCase();

		//报文修改后再操作
		String audioSamplingRate=inMessage.getString("audioSamplingRate");
		String audioEncodeFormat=inMessage.getString("audioEncodeFormat");
		
		String base64Audio=closeAudioMap.get("base64");
		
		String directory=closeAudioMap.get("directory");
		
		
		// 将url下载后音频的存放位置给报文，以便后面存入数据库
		inMessage.set("closeAudio", directory);
		

		// 设置日志ID
		String loggingTime = TimeUtils.generateTimestamp();
		String loggingId = certNo + loggingTime; 
		
		Map<String, Object> serviceDataMap = new HashMap<String, Object>();
		serviceDataMap.put("loggingId", loggingId);

		// 设置用户ID，形式为用户姓名加证件类型加证件号码
		String identifier = certType + certNo;
		Map<String, Object> userDataMap = new HashMap<String, Object>();
		userDataMap.put("identifier", identifier);

		// 设置文本相关还是文本无关 此处根据渠道服务类型约定设定		
		String type = "text-independent";
		
		// 设置模式 厂商给定的值
		String mode = "ti_plp2covv2"; 
		// 设置采样率
		String samplingRate = "";
		if("8000".equals(audioSamplingRate)) {
			samplingRate="8000";
		}else if("16000".equals(audioSamplingRate)) {
			samplingRate="16000";
		}else if("OPM_CSR".equals(channel)) {
			samplingRate="8000";
		}
		
		Map<String, Object> biometricMap = new HashMap<String, Object>();
		biometricMap.put("type", type);
		biometricMap.put("mode", mode);
		
		// 设置语音格式
		String format="";
		if("pcm16".equals(audioEncodeFormat)) {
			format = "pcm16";
		}else if("alaw".equals(audioEncodeFormat)) {
			format = "alaw";
		}else if("ulaw".equals(audioEncodeFormat)) {
			format = "ulaw";
		}else if("OPM_CSR".equals(channel)) {
			format = "pcm16";
		}
		
		Map<String, Object> audioCharacteristicsMap = new HashMap<String, Object>();
		audioCharacteristicsMap.put("samplingRate", samplingRate);
		audioCharacteristicsMap.put("format", format);

		Map<String, Object> processingInformationMap = new HashMap<String, Object>();
		processingInformationMap.put("biometric", biometricMap);
		processingInformationMap.put("audioCharacteristics", audioCharacteristicsMap);

		//FS 不建议用防重播
		Map<String, Object> valueMap = new HashMap<String, Object>();
		//
		valueMap.put("value", channel);
		valueMap.put("encrypted", "false");
		Map<String, Object> metaInformationMap = new HashMap<String, Object>();
		metaInformationMap.put("key", "usage-context");
		metaInformationMap.put("value", valueMap);
		
		
		
		
		// 设置meta信息
		List<Map<String, Object>> metaInformationList = new ArrayList<Map<String, Object>>();
		metaInformationList.add(metaInformationMap);
		
		//如果是注册,需新增如下参数
        if(processType) {
        	Map<String, Object> valueMap1 = new HashMap<String, Object>();
    		valueMap1.put("value", "125");
    		valueMap1.put("encrypted", "false");
    		Map<String, Object> metaInformationMap1 = new HashMap<String, Object>();
    		metaInformationMap1.put("key", "detect-speech");
    		metaInformationMap1.put("value", valueMap1);
    		
    		Map<String, Object> valueMap2 = new HashMap<String, Object>();
    		valueMap2.put("value", "5");
    		valueMap2.put("encrypted", "false");
    		Map<String, Object> metaInformationMap2 = new HashMap<String, Object>();
    		metaInformationMap2.put("key", "get-snr");
    		metaInformationMap2.put("value", valueMap2);
			
    		metaInformationList.add(metaInformationMap1);
    		metaInformationList.add(metaInformationMap2);
		}
		
		
		processingInformationMap.put("metaInformation", metaInformationList);
		
		// 设置语音相关信息
		Map<String, Object> audioMap = new HashMap<String, Object>();
		// 放入语音，语音为base64编码格式
		audioMap.put("base64", base64Audio);
		Map<String, Object> audioInputMap = new HashMap<String, Object>();
		
		
		// 设置采集多长时间的语音
		String secondsThreshold="0";
		if(processType) {
			secondsThreshold = register_audio_duration_threshold;	
		}else {
			secondsThreshold = verify_audio_duration_threshold;	
		}
		
		
		audioInputMap.put("secondsThreshold", secondsThreshold);
		audioInputMap.put("audio", audioMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceData", serviceDataMap);
		map.put("userData", userDataMap);
		map.put("processingInformation", processingInformationMap);
		map.put("audioInput", audioInputMap);

		// 转为JSON字符串
		String jsonString = JSONObject.toJSONString(map);
		return jsonString;
	}
	
	/**
	 * 设置报文参数 （随意说）
	 * @param inMessage   输入报文
	 * @param processType 操作流程类型/是否需要防重播
	 * @return
	 */
	public String createPayLoadFsForDelete(DataObject inMessage,boolean processType) {
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
//		String closeAudio = inMessage.getString("closeAudio");  
//		String channel=inMessage.getString("channel");

		//报文修改后再操作
		String audioSamplingRate=inMessage.getString("audioSamplingRate");
		String audioEncodeFormat=inMessage.getString("audioEncodeFormat");
		

		// 设置日志ID
		String loggingTime = TimeUtils.generateTimestamp();
		String loggingId = certNo + loggingTime; 
		
		Map<String, Object> serviceDataMap = new HashMap<String, Object>();
		serviceDataMap.put("loggingId", loggingId);

		// 设置用户ID，形式为用户姓名加证件类型加证件号码
		String identifier = certType + certNo;
		Map<String, Object> userDataMap = new HashMap<String, Object>();
		userDataMap.put("identifier", identifier);

		// 设置文本相关还是文本无关 此处根据渠道服务类型约定设定		
		String type = "text-independent";
		
		// 设置模式 厂商给定的值
		String mode = "ti_plp2covv2"; 
		// 设置采样率
		String samplingRate = "";
		if("8000".equals(audioSamplingRate)) {
			samplingRate="8000";
		}else if("16000".equals(audioSamplingRate)) {
			samplingRate="16000";
		}
		
		Map<String, Object> biometricMap = new HashMap<String, Object>();
		biometricMap.put("type", type);
		biometricMap.put("mode", mode);
		
		// 设置语音格式
		String format="";
		if("pcm16".equals(audioEncodeFormat)) {
			format = "pcm16";
		}else if("alaw".equals(audioEncodeFormat)) {
			format = "alaw";
		}else if("ulaw".equals(audioEncodeFormat)) {
			format = "ulaw";
		}  
		
		Map<String, Object> audioCharacteristicsMap = new HashMap<String, Object>();
		audioCharacteristicsMap.put("samplingRate", samplingRate);
		audioCharacteristicsMap.put("format", format);

		Map<String, Object> processingInformationMap = new HashMap<String, Object>();
		processingInformationMap.put("biometric", biometricMap);
		processingInformationMap.put("audioCharacteristics", audioCharacteristicsMap);

		//FS 不建议用防重播
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("value", "default");
		valueMap.put("encrypted", "false");
		Map<String, Object> metaInformationMap = new HashMap<String, Object>();
		metaInformationMap.put("key", "usage-context");
		metaInformationMap.put("value", valueMap);
		
		// 设置meta信息
		List<Map<String, Object>> metaInformationList = new ArrayList<Map<String, Object>>();
		metaInformationList.add(metaInformationMap);
		processingInformationMap.put("metaInformation", metaInformationList);
		
	

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceData", serviceDataMap);
		map.put("userData", userDataMap);
		map.put("processingInformation", processingInformationMap);


		// 转为JSON字符串
		String jsonString = JSONObject.toJSONString(map);
		return jsonString;
	}
	
	/**
	 * 不同情况下的存储剥离出来  path/url/base64   
	 * 返回base64
	 * inMessage closeAudio转为地址
	 * ？？音频切割在哪里处理  下午优先思考下 肯定是变为音频后
	 */
	/**
	 * 获取inMessage的closeAudio,根据传输格式，返回存储地址及base64的键值对形式，方便使用
	 * @param inMessage
	 * @param addressFlag 存放文件的临时地址
	 * @return
	 */
	public Map<String,String> convertCloseAudio(DataObject inMessage,String addressFlag){
		// 首先获取报文传递的参数
		String closeAudio = inMessage.getString("closeAudio");
		String channel = inMessage.getString("channel");

		// 报文修改后再操作获取的,裁切可能要用
//		String audioSamplingRate = inMessage.getString("audioSamplingRate");
//		String audioEncodeFormat = inMessage.getString("audioEncodeFormat");
//		String audioTimelength = inMessage.getString("audioTimelength");

		
		
		Map<String,String> closeAudioInfo=new HashMap<String,String>();

		// 获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat");
		
		//音频存放地址
		String directory="";
		//音频对应的base64编码
		String base64Audio = "";
		
		//根据标识控制音频存储地址
		if("tdregister".equals(addressFlag)) {
			directory = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
		}else if("fsregister".equals(addressFlag)) {
			directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
		}else if("tdverify".equals(addressFlag)) {
			directory = AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
		}else if("fsverify".equals(addressFlag)) {
			directory = AudioFileUtils.crtFsVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
		}
		
		
		
		// 当传递方式为url时（从ToudaDfs下载）
		if (closeAudioFormat.equals("url")) {
//			directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("从url下载文件存储到指定路径出错" + e.getMessage());
			}
//			// 将url下载后音频的存放位置给报文，以便后面存入数据库
//			inMessage.set("closeAudio", directory);

			// 将音频文件转化为base64
//			base64Audio = AudioFileUtilsByValidSoft.fileToBase64(directory);
			base64Audio = AudioFileUtils.getAudioFile(directory);
			
			logger.info("临时文件的存储位置："+directory);
			//将临时文件删除
			File file = new File(directory);
			if (file.exists()) {
				file.delete();
			}
			
		}
		// 当传递方式为path时
		else if (closeAudioFormat.equals("path")) {
//			directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("从path拷贝文件存储到指定路径出错" + e.getMessage());
			}
//			// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
//			inMessage.set("closeAudio", directory);

			// 将音频文件转化为base64
//			base64Audio = AudioFileUtilsByValidSoft.fileToBase64(directory);
			base64Audio = AudioFileUtils.getAudioFile(directory);
		}
		// 当传递方式为base64时
		else if (closeAudioFormat.equals("base64")) {
			base64Audio = closeAudio;
//			try {
////				directory = AudioFileUtils.crtAudioPathByUUID(VPRSUtils.getUUID(), channel);
//				logger.info("个人近声存储路径：" + directory);
//				// 需要将validsoft解码生成wav文件
//				AudioFileUtils.convertBase64DataToAudio(closeAudio, directory);
////				// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
////				inMessage.set("closeAudio", directory);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("存储音频文件到文件服务器出错"+e.getMessage());
//			}
			
			try {
				logger.info("开始将语音文件存入ToudaDFS");
                //将音频存储到toudaDfs
				String fileName = FilenameUtils.getName("VPRS_Audio_"
				+ System.currentTimeMillis());
				Map<String, String> metaInfo = new HashMap<String, String>();				
				metaInfo.put("operUser", "bams_vprs_service");// 上传用户
				metaInfo.put("busiCode", "J_VPRS_001_0001");// 必填
//				String directory = "";
				directory=AudioFileUtils.convertBase64DataToToudaDfs(closeAudio, fileName, metaInfo);
//				// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
//				inMessage.set("closeAudio", fileAbsolutePath);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("存储音频文件到文件服务器出错"+ e.getMessage());
			}
		}
		
		closeAudioInfo.put("directory", directory);
		closeAudioInfo.put("base64", base64Audio);
		
		return closeAudioInfo;
	}
	
	
	/**
	 * 获取inMessage的closeAudio,根据传输格式，返回存储地址及base64的键值对形式，方便使用
	 * @param inMessage
	 * @return
	 */
	public Map<String,String> convertCloseAudioFsRegister(DataObject inMessage){
		// 首先获取报文传递的参数
		String closeAudio = inMessage.getString("closeAudio");
		String channel = inMessage.getString("channel");
		
		Map<String,String> closeAudioInfo=new HashMap<String,String>();

		// 获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat").toLowerCase();
		
		//音频存放地址
		String directory="";
		//音频对应的base64编码
		String base64Audio = "";
		
		//临时音频存储地址
        directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);		
		
		// 当传递方式为url时（从ToudaDfs下载）
		if (closeAudioFormat.equals("url")) {
			byte[] data=null;
			try {
//				if("PDA".equals(channel.toUpperCase())) {
//					 data=FastDFSClientAlone.download(closeAudio);
//					
//		
//					//对字节数组Base64编码  
//					BASE64Encoder encoder = new BASE64Encoder();
//					
//					base64Audio=encoder.encode(data);
//				}else {

				long start = System.currentTimeMillis();
				 data=FastDFSClientAlone.download(closeAudio);
				 long end = System.currentTimeMillis();
				 logger.error("dfs下载耗时--->" + (end - start));
				//检测文件，去表头转化为base64
				InputStream in=new ByteArrayInputStream(data);
				in.skip(44);
				byte[] bytes = new byte[in.available() - 44];
				in.read(bytes);
				//对字节数组Base64编码  
				BASE64Encoder encoder = new BASE64Encoder();
				
				base64Audio=encoder.encode(bytes);
//				}
			directory=closeAudio;
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("从ToudaDfs读取文件失败" + e.getMessage());
			}
			
		}
		// 当传递方式为path时
		else if (closeAudioFormat.equals("path")) {
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("从path拷贝文件存储到指定路径出错" + e.getMessage());
			}

			// 将音频文件转化为base64
//			base64Audio = AudioFileUtilsByValidSoft.fileToBase64(directory);
			base64Audio = AudioFileUtils.getAudioFile(directory);
		}
		// 当传递方式为base64时
		else if (closeAudioFormat.equals("base64")) {
			base64Audio = closeAudio;			
			try {
				logger.info("开始将语音文件存入ToudaDFS");
                //将音频存储到toudaDfs
				String fileName = FilenameUtils.getName("VPRS_Audio_Fs_Register"
				+ System.currentTimeMillis());
				Map<String, String> metaInfo = new HashMap<String, String>();				
				metaInfo.put("operUser", "bams_vprs_service");// 上传用户
				metaInfo.put("busiCode", "J_VPRS_001_0001");// 必填
				directory=AudioFileUtils.convertBase64DataToToudaDfs(closeAudio, fileName, metaInfo);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("存储音频文件到文件服务器出错"+ e.getMessage());
			}
			
			
			

		}
		
		closeAudioInfo.put("directory", directory);
		closeAudioInfo.put("base64", base64Audio);
		
		return closeAudioInfo;
	}
	
	
	/**
	 * 获取inMessage的closeAudio,根据传输格式，返回存储地址及base64的键值对形式，方便使用
	 * @param inMessage
	 * @return
	 */
	public Map<String,String> convertCloseAudioFsUpdate(DataObject inMessage){
		// 首先获取报文传递的参数
		String closeAudio = inMessage.getString("closeAudio");
		String channel = inMessage.getString("channel");
		
		Map<String,String> closeAudioInfo=new HashMap<String,String>();

		// 获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat").toLowerCase();
		
		//音频存放地址
		String directory="";
		//音频对应的base64编码
		String base64Audio = "";
		
			
		
		// 当传递方式为url时（从ToudaDfs下载）
		if (closeAudioFormat.equals("url")) {

			byte[] data=null;
			try {
//				if("PDA".equals(channel.toUpperCase())) {
//					 data=FastDFSClientAlone.download(closeAudio);
//					
//		
//					//对字节数组Base64编码  
//					BASE64Encoder encoder = new BASE64Encoder();
//					
//					base64Audio=encoder.encode(data);
//				}else {

				
				 data=FastDFSClientAlone.download(closeAudio);
				
				//检测文件，去表头转化为base64
				InputStream in=new ByteArrayInputStream(data);
				in.skip(44);
				byte[] bytes = new byte[in.available() - 44];
				in.read(bytes);
				//对字节数组Base64编码  
				BASE64Encoder encoder = new BASE64Encoder();
				
				base64Audio=encoder.encode(bytes);
//				}
			
			directory=closeAudio;
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("从ToudaDfs读取文件失败" + e.getMessage());
			}
			
		}
		// 当传递方式为path时
		else if (closeAudioFormat.equals("path")) {
			try {
				//临时音频存储地址
		        directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);	
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("从path拷贝文件存储到指定路径出错" + e.getMessage());
			}

			// 将音频文件转化为base64
//			base64Audio = AudioFileUtilsByValidSoft.fileToBase64(directory);
			base64Audio = AudioFileUtils.getAudioFile(directory);
		}
		// 当传递方式为base64时
		else if (closeAudioFormat.equals("base64")) {
			base64Audio = closeAudio;			
			try {
				logger.info("开始将语音文件存入ToudaDFS");
                //将音频存储到toudaDfs
				String fileName = FilenameUtils.getName("VPRS_Audio_Fs_Update"
				+ System.currentTimeMillis());
				Map<String, String> metaInfo = new HashMap<String, String>();				
				metaInfo.put("operUser", "bams_vprs_service");// 上传用户
				metaInfo.put("busiCode", "J_VPRS_001_0001");// 必填
				directory=AudioFileUtils.convertBase64DataToToudaDfs(closeAudio, fileName, metaInfo);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("存储音频文件到文件服务器出错"+ e.getMessage());
			}
		}
		
		closeAudioInfo.put("directory", directory);
		closeAudioInfo.put("base64", base64Audio);
		
		return closeAudioInfo;
	}
	
	
	/**
	 * 获取inMessage的closeAudio,根据传输格式，返回存储地址及base64的键值对形式，方便使用
	 * @param inMessage
	 * @return
	 */
	public Map<String,String> convertCloseAudioFsVerify(DataObject inMessage){
		// 首先获取报文传递的参数
		String closeAudio = inMessage.getString("closeAudio");
		String channel = inMessage.getString("channel");
		String isStore = inMessage.getString("isStore").toUpperCase();//是否存储
		
		Map<String,String> closeAudioInfo=new HashMap<String,String>();

		// 获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat").toLowerCase();
		
		//音频存放地址
		String directory="";
		//音频对应的base64编码
		String base64Audio = "";
		
			
		
		// 当传递方式为url时（从ToudaDfs下载）
		if (closeAudioFormat.equals("url")) {

			byte[] data=null;
			try {
//				if("PDA".equals(channel.toUpperCase())) {
//					 data=FastDFSClientAlone.download(closeAudio);
//					
//		
//					//对字节数组Base64编码  
//					BASE64Encoder encoder = new BASE64Encoder();
//					
//					base64Audio=encoder.encode(data);
//				}else {

				
				 data=FastDFSClientAlone.download(closeAudio);
				
				//检测文件，去表头转化为base64
				InputStream in=new ByteArrayInputStream(data);
				in.skip(44);
				byte[] bytes = new byte[in.available() - 44];
				in.read(bytes);
				//对字节数组Base64编码  
				BASE64Encoder encoder = new BASE64Encoder();
				
				base64Audio=encoder.encode(bytes);
//				}
			
			directory=closeAudio;
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("从ToudaDfs读取文件失败" + e.getMessage());
			}
			
		}
		// 当传递方式为path时
		else if (closeAudioFormat.equals("path")) {
			try {
				//临时音频存储地址
		        directory = AudioFileUtils.crtFsVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);	
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("从path拷贝文件存储到指定路径出错" + e.getMessage());
			}

			// 将音频文件转化为base64
//			base64Audio = AudioFileUtilsByValidSoft.fileToBase64(directory);
			base64Audio = AudioFileUtils.getAudioFile(directory);
		}
		// 当传递方式为base64时
		else if (closeAudioFormat.equals("base64") ) {//暂定Y为存储,N为不存储
			base64Audio = closeAudio;			
			try {
				if(isStore.equals("Y")) {
				logger.info("开始将语音文件存入ToudaDFS");
                //将音频存储到toudaDfs
				String fileName = FilenameUtils.getName("VPRS_Audio_Fs_Update"
				+ System.currentTimeMillis());
				Map<String, String> metaInfo = new HashMap<String, String>();				
				metaInfo.put("operUser", "bams_vprs_service");// 上传用户
				metaInfo.put("busiCode", "J_VPRS_001_0001");// 必填
				directory=AudioFileUtils.convertBase64DataToToudaDfs(closeAudio, fileName, metaInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("存储音频文件到文件服务器出错"+ e.getMessage());
			}
			
			
			

		}
		
		closeAudioInfo.put("directory", directory);
		closeAudioInfo.put("base64", base64Audio);
		
		return closeAudioInfo;
	}
	
	/**
	 * 裁切策略            现暂不考虑
	 * 根据音频时长进行裁切
	 */
	public String splitCloseAudio() {
		return null;
	}
	
	@Bizlet("调用ValidSoft接口注册无关文本声纹")
	public DataObject enrolSpeakerIndependent(DataObject inMessage,DataObject outMessage){
		
		Map<String,String> closeAudioInfo=convertCloseAudioFsRegister(inMessage);
		try {			
			StringEntity reqEntity = new StringEntity(createPayLoadFs(inMessage,true,closeAudioInfo), "UTF-8");
			//设置contentType为application/json
			reqEntity.setContentType("application/json; charset=UTF-8");
			Result result = Invoker.invoke("enrolSpeaker", null,reqEntity);
			logger.info("随意说返回结果result:"+result);
			//获取HttpResult 中的result
			Object resultObj=result.get();
			
			if(resultObj==null) {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
                outMessage.set("returnMsg", "注册失败，调用enrolSpeaker接口返回为空！");
                return outMessage;
			}
			
	        JSONObject outMess = JSONObject.parseObject(resultObj.toString());
	        
	        if(outMess.get("errorData")!=null)
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "validSoft注册失败"+"code:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("code")+"description:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
//			    outMessage.set("registerStatus", "0");
				return outMessage;
	        }else
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "注册成功");
//			    outMessage.set("registerStatus", "0");
				return outMessage;
	        }

		} catch (Exception e) {
			logger.error("enrolSpeakerIndependent注册异常:"+e);
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "注册异常："+e.getMessage());
			return outMessage;
		}
	}
	
	
	
	@Bizlet("调用ValidSoft接口验证固定文本声纹(根据得分更新模型--注册中比较)")
	public DataObject verifySpeaker(DataObject inMessage, DataObject outMessage, BigDecimal threshold) {

		Map<String, String> closeAudioInfo = convertCloseAudio(inMessage, "tdverify");

		try {

			StringEntity entity = null;
			String payLoadFs = "";
			try {
				payLoadFs = createPayLoadTd(inMessage, false, closeAudioInfo);
				entity = new StringEntity(payLoadFs);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			HashMap<String, Object> protocalMap = new HashMap<String, Object>();
			protocalMap.put("Content-Type", "application/json");
			ProxyContext context1 = Invoker.initProxyContext("verifySpeaker", Version.DefaultVersion, protocalMap, null,
					entity, false);
			Result result = null;
			try {
				result = Invoker.invoke(context1);
				logger.info("固定文本说验证返回结果result:" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 获取HttpResult 中的result
			Object resultMap = result.get();
			if (resultMap == null) {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "验证失败");
				return outMessage;
			}

			logger.info(resultMap.toString() + "----------------1-------------");

			JSONObject outMess = JSONObject.parseObject(resultMap.toString());

			logger.info(outMess.toString() + "----------------2-------------");

			if (outMess.get("errorData") != null) {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "validSoft校验失败" + "code:"
						+ (JSONObject.parseObject(outMess.getString("errorData"))).getString("code") + "description:"
						+ (JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
				// outMessage.set("registerStatus", "0");
				return outMessage;
			}

			String score = outMess.getJSONObject("result").getString("score").toString();
			// 转化格式
			BigDecimal similarity = (BigDecimal) VPRSUtils.convertObject2BigDecimal(score);
			if (similarity != null && (similarity.compareTo(threshold)) >= 0) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "检验成功");
				outMessage.set("info/similarity", similarity + "");
				outMessage.set("info/threshold", threshold + "");
				outMessage.set("info/compareResult", "success");
				outMessage.set("payLoadFs", payLoadFs);
			} else if (similarity == null) {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "检验失败,音频质量不合格");
				outMessage.set("info/similarity", similarity + "");
				outMessage.set("info/threshold", threshold + "");
				outMessage.set("info/compareResult", "fail");
			} else {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "检验失败,两份音频相似度未达到预设阈值");
				outMessage.set("info/similarity", similarity + "");
				outMessage.set("info/threshold", threshold + "");
				outMessage.set("info/compareResult", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
			outMessage.set("returnMsg", "调用validsoft声纹对比验证服务异常:" + e.getMessage());
			outMessage.set("info/compareResult", "fail");
		}
		return outMessage;
	}
	
	@Bizlet("调用ValidSoft接口验证文本无关声纹(根据得分更新模型--注册中比较)")
	public DataObject verifySpeakerIndependent(DataObject inMessage,DataObject outMessage,BigDecimal threshold){
		
		Map<String,String> closeAudioInfo=convertCloseAudioFsVerify(inMessage);
		
		try {
			StringEntity entity = null;
			String payLoadFs="";
	        try {
	        	payLoadFs=createPayLoadFs(inMessage,false,closeAudioInfo);
	            entity = new StringEntity(payLoadFs);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        HashMap<String, Object> protocalMap = new HashMap<String, Object>();
	        protocalMap.put("Content-Type", "application/json");
	        logger.debug("随意说验证request:"+payLoadFs);
	        ProxyContext context1 = Invoker.initProxyContext("verifySpeaker", Version.DefaultVersion, protocalMap, null, entity, false);
	        Result result = null;
	        try {
	            result = Invoker.invoke(context1);
				logger.info("随意说验证返回结果result:"+result);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

			//获取HttpResult 中的result
	        Object resultMap = result.get();
	        if(resultMap==null) {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
                outMessage.set("returnMsg", "声纹引擎verifySpeaker调用出错，返回结果为空！");
                return outMessage;
			}
	        
	        JSONObject outMess = JSONObject.parseObject(resultMap.toString());
	        
			if(outMess.get("errorData")!=null)
	        {
				String errorCode=JSONObject.parseObject(outMess.getString("errorData")).getString("code");
				String description=JSONObject.parseObject(outMess.getString("errorData")).getString("description");
				
				if("105".equals(errorCode)) {
					outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
					outMessage.set("returnMsg", "该用户未注册");
				}else if("107".equals(errorCode)) {  //待厂商确认
					outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
					outMessage.set("returnMsg", "音频时长不够");
				}else {
					outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
					outMessage.set("returnMsg", "声纹引擎调用出错");
				}
//	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
//				outMessage.set("returnMsg", "validSoft校验失败"+"code:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("code")+"description:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
////			    outMessage.set("registerStatus", "0");
//				return outMessage;
	        }else {
	        	String description=JSONObject.parseObject(outMess.getString("result")).getString("decision");
	        	String score=outMess.getJSONObject("result").getString("score");
	        	//转化格式
				BigDecimal similarity = (BigDecimal)VPRSUtils.convertObject2BigDecimal(score);
				if("accepted".equals(description)) {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "声纹验证为本人");
					outMessage.set("info/similarity", similarity);
					outMessage.set("info/threshold" , "");
					outMessage.set("info/compareResult" , "success");
				}else {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "声纹验证非本人");
					outMessage.set("info/similarity", similarity);
					outMessage.set("info/threshold" , "");
					outMessage.set("info/compareResult" , "fail");
				}
	        }
			
			
//			//阈值后面还得看
//			String thresholdValue="";
//			if(threshold==null) {
//				thresholdValue="";
//			}
//			
////			if (similarity!=null&&(similarity.compareTo(threshold))>=0) {
//			if (similarity!=null && score.equals("Y")) {//暂定传给我Y代表验证通过	
//				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//				outMessage.set("returnMsg", "检验成功");
//				outMessage.set("info/similarity", similarity+"");
//				outMessage.set("info/threshold" , thresholdValue +"");
//				outMessage.set("info/compareResult" , "success");
//				outMessage.set("payLoadFs", payLoadFs);
//			}else if(similarity == null){
//				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
//				outMessage.set("returnMsg", "检验失败,音频质量不合格");
//				outMessage.set("info/similarity", similarity+"");
//				outMessage.set("info/threshold" , thresholdValue +"");
//				outMessage.set("info/compareResult" , "fail");
//			}else {
//				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//				outMessage.set("returnMsg", "检验失败");
//				outMessage.set("info/similarity", similarity+"");
//				outMessage.set("info/threshold" , thresholdValue +"");
//				outMessage.set("info/compareResult" , "fail");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
			outMessage.set("returnMsg", "调用validsoft声纹对比验证服务异常:"+e.getMessage());
//			outMessage.set("info/compareResult" , "fail");
		}
		
		return outMessage;
	}
	
	
	@Bizlet("调用ValidSoft接口抓包语音验证声纹(根据得分更新模型--注册中比较)")
	public DataObject verifySpeakerIndependentCQVoice(DataObject inMessage,DataObject outMessage){
		
		String userType = inMessage.getString("userType");//用户类型
		String userName = inMessage.getString("userName");//用户姓名
		String certType = inMessage.getString("certType");//证件类型
		String certNo = inMessage.getString("certNo");//证件号码
		String userUniqueId = inMessage.getString("userUniqueId");//分机号码
		String channel = inMessage.getString("channel");//渠道号
		String businessType = inMessage.getString("businessType");//业务场景
		
		String callId = inMessage.getString("callId");//callId
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userType", userType);
		map.put("userName", userName);
		map.put("certType", certType);
		map.put("certNo", certNo);
		map.put("userUniqueId", userUniqueId);
		map.put("channel", channel);
		map.put("businessType", businessType);
		map.put("callID", callId);

		// 转为JSON字符串
		String jsonString = JSONObject.toJSONString(map);
		
        try {
            StringEntity entity = new StringEntity(jsonString);
            HashMap<String, Object> protocalMap = new HashMap<String, Object>();
            protocalMap.put("Content-Type", "application/json");
            long start = System.currentTimeMillis();
            ProxyContext context1 = Invoker.initProxyContext("CQVoiceVerifySpeaker", Version.DefaultVersion, protocalMap, null, entity, false);
            Result result = Invoker.invoke(context1);
            long end = System.currentTimeMillis();
    		logger.error("调用抓包服务耗时--->" + (end - start));
            JSONObject resultObj = JSONObject.parseObject(result.getString());
            
//		JSONObject jsonString = JSONObject.parseObject(HttpRequest.sendPost(url, js.toJSONString()));
		
		/*
			returnCode 	    // 返回码，000000为成功
			returnMsg	    // 返回描述
			userUniqueId	// 分机号
			similarity		// 相似度 分数
			threshold		// 阈值
			compareResult	//  本人返回success，非本人返回fail
			
		*/
        inMessage.set("userUniqueId", userUniqueId+"|"+callId);   //callId和userUniqueId拼接存储在数据库USER_UNIQUE_ID字段中
            
		outMessage.set("returnCode",  resultObj.get("returnCode"));
		outMessage.set("returnMsg",  resultObj.get("returnMsg"));
		outMessage.set("info/similarity",  resultObj.get("similarity"));
		outMessage.set("info/threshold" ,  resultObj.get("threshold"));
		outMessage.set("info/compareResult" ,  resultObj.get("compareResult"));
		outMessage.set("info/fileName" ,  resultObj.get("fileName"));
		outMessage.set("info/uuid" ,  resultObj.get("uuid"));
		outMessage.set("info/transTime" ,  resultObj.get("transTime"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("verifySpeakerIndependentCQVoice调用ValidSoft接口抓包语音验证声纹异常:"+e);
			outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
			outMessage.set("returnMsg", "调用validsoft声纹对比验证服务异常:"+e.getMessage());
		}
		return outMessage;
	}
	
	
	@Bizlet("调用ValidSoft接口更新声纹模型")
	public DataObject updateSpeaker(DataObject inMessage,DataObject outMessage){
		
		Map<String,String> closeAudioInfo=convertCloseAudio(inMessage, "tdregister");

		try {
			StringEntity reqEntity = new StringEntity(createPayLoadTd(inMessage,false,closeAudioInfo), "UTF-8");
			reqEntity.setContentType("application/json; charset=UTF-8");
			Result result = Invoker.invoke("updateSpeaker", null,reqEntity);
			logger.info("固定文本更新结果result:"+result);
			//获取HttpResult 中的result
			Object resultObj=result.get();
						
			if(resultObj==null) {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
                outMessage.set("returnMsg", "更新失败");
                return outMessage;
			}
			
	        JSONObject outMess = JSONObject.parseObject(resultObj.toString());
	        
	        if(outMess.get("errorData")!=null)
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "validSoft更新失败"+"code:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("code")+"description:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
				return outMessage;
	        }else
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "声纹模型更新成功");
				return outMessage;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "声纹模型更新失败");
			return outMessage;
		}
	}
	
	
	public DataObject updateSpeakerIndependent(DataObject inMessage, DataObject outMessage) {
		
		Map<String,String> closeAudioInfo=convertCloseAudioFsUpdate(inMessage);
		
		try {
			StringEntity reqEntity = new StringEntity(createPayLoadFs(inMessage,true,closeAudioInfo), "UTF-8");
			reqEntity.setContentType("application/json; charset=UTF-8");
			logger.debug("随意说更新request:"+createPayLoadFs(inMessage,true,closeAudioInfo));
			Result result = Invoker.invoke("updateSpeaker", null,reqEntity);
			
			logger.info("随意说更新结果result:"+result);
			//获取HttpResult 中的result
			Object resultObj=result.get();
						
			if(resultObj==null) {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
                outMessage.set("returnMsg", "更新失败,updateSpeaker接口返回为空");
                return outMessage;
			}
			
	        JSONObject outMess = JSONObject.parseObject(resultObj.toString());
	        
	        if(outMess.get("errorData")!=null)
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "validSoft更新失败"+"code:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("code")+"description:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
				return outMessage;
	        }else
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "声纹模型更新成功");
				return outMessage;
	        }
			
		} catch (Exception e) {
			logger.error("updateSpeakerIndependent:"+e);
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "声纹模型更新异常"+e.getMessage());
			return outMessage;
		}
	}
	
	public DataObject deleteSpeakerIndependent(DataObject inMessage, DataObject outMessage) {
		
		
		try {
			StringEntity reqEntity = new StringEntity(createPayLoadFsForDelete(inMessage,false), "UTF-8");
			reqEntity.setContentType("application/json; charset=UTF-8");
			Result result = Invoker.invoke("deleteSpeaker", null,reqEntity);
			
			logger.info("随意说删除结果result:"+result);
			//获取HttpResult 中的result
			Object resultObj=result.get();
						
			if(resultObj==null) {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
                outMessage.set("returnMsg", "删除失败,deleteSpeaker接口返回为空");
                return outMessage;
			}
			
	        JSONObject outMess = JSONObject.parseObject(resultObj.toString());
	        
	        if(outMess.get("errorData")!=null)
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "validSoft删除失败"+"code:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("code")+"description:"+(JSONObject.parseObject(outMess.getString("errorData"))).getString("description"));
				return outMessage;
	        }else
	        {
	        	outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "声纹模型删除成功");
				return outMessage;
	        }
			
		} catch (Exception e) {
			logger.error("deleteSpeakerIndependent:"+e);
			outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
			outMessage.set("returnMsg", "声纹模型删除异常"+e.getMessage());
			return outMessage;
		}
	}
	
	@Bizlet("调用ValidSoft接口比较声纹")
	public DataObject compareSpeaker(DataObject inMessage,DataObject outMessage){
		return outMessage;
	}
	
	/**
	 * 声纹注册查询
	 */
	public DataObject isExistSpeaker(DataObject inMessage,DataObject outMessage){
//		首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certNo=inMessage.getString("certNo");
		
        //设置日志ID，即当前操作记录在何处，形式为用户名加时间戳
		String loggingTime=TimeUtils.generateTimestamp();
		String loggingId = userName+loggingTime;   //这个地方要不要换成证件号码，以防同名同一时间注册
		Map<String,Object> serviceDataMap = new HashMap<String,Object>();
		serviceDataMap.put("loggingId", loggingId);
		
		// 设置用户ID，形式为用户姓名加证件类型加证件号码
		String identifier = userName+userType+certNo;
		Map<String,Object> userDataMap = new HashMap<String,Object>();
		userDataMap.put("identifier", identifier);
		
		// 设置文本相关还是文本无关  此处根据渠道服务类型约定设定
		String type="text-dependent";
		// 设置模式 厂商给定的值
		String mode = "td_fuse_8_atn_v2";
		Map<String,Object> biometricMap = new HashMap<String,Object>();
		biometricMap.put("type", type);
		biometricMap.put("mode", mode);
		

		Map<String,Object> processingInformationMap = new HashMap<String,Object>();
		processingInformationMap.put("biometric", biometricMap);

		Map<String,Object> valueMap = new HashMap<String,Object>();
		valueMap.put("value", "default");
		valueMap.put("encrypted", "false");
		Map<String,Object> metaInformationMap = new HashMap<String,Object>();
		metaInformationMap.put("key", "usage-context");
		metaInformationMap.put("value", valueMap);

		// 设置meta信息
		List<Map<String,Object>> metaInformationList = new ArrayList<Map<String,Object>>();
		metaInformationList.add(metaInformationMap);
		processingInformationMap.put("metaInformation", metaInformationList);


		Map<String,Object> map = new HashMap<String,Object>();
		map.put("serviceData", serviceDataMap);
		map.put("userData", userDataMap);
		map.put("processingInformation", processingInformationMap);
		
		
		// 转为JSON字符串
		String jsonString = JSONObject.toJSONString(map);
		
		StringEntity reqEntity = new StringEntity(jsonString, "UTF-8");
//		//设置contentType为application/json
		reqEntity.setContentType("application/json; charset=UTF-8");
		Result resultMap = Invoker.invoke("isExistSpeaker", null,reqEntity);
		
//		此处获取返回值resultMap中的值
		JSONObject obj = JSON.parseObject(resultMap.toString());
		//获取返回结果
		String outcome=obj.getJSONObject("outcome").toString();
		if(outcome.equals("KNOWN_USER_ACTIVE")){
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "用户已注册");
		}
		if(outcome.equals("UNKNOWN_USER")){
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "用户未注册");
		}
		if(outcome.equals("ERROR")){
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "用户注册查询错误");
		}
		return outMessage;
	}

	
}
