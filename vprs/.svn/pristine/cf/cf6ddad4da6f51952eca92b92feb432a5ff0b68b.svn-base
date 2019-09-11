package com.pccc.vprs.servicedisplay.vprs.audio.tts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.common.util.NetUtils;
import com.pccc.vprs.servicecustom.transflow.TransFlowConstant;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicedisplay.vprs.audio.iat.Iat;
import com.pccc.vprs.servicedisplay.vprs.common.ReturnCode;
import com.pccc.vprs.servicedisplay.vprs.model.IatInfo;
import com.pccc.vprs.servicedisplay.vprs.model.TtsInfo;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.btp.api.engine.context.IKernelServiceContext;
import com.primeton.btp.spi.transportservice.def.TransportServiceDefinition;
import com.primeton.esb.message.impl.DefaultMessagePayload;
import com.primeton.ext.infra.security.BASE64Encoder;

import commonj.sdo.DataObject;
import com.pccc.vprs.servicedisplay.tts.util.AudioTts;
import com.pccc.vprs.servicedisplay.tts.util.PcmToAmr;

@Bizlet("tts,将文本转化为语音")
public class Tts {

	private static ILogger logger = LoggerFactory.getLogger(Tts.class);
	
	@Bizlet("tts:文本合成语音") 
	public DataObject textToAudio(DataObject inMessage,DataObject outMessage,TtsInfo ttsInfo){	
		
		if (StringUtils.isBlank(inMessage.getString("text"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		
		
		//首先获取报文参数
		String text=inMessage.getString("text");
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		
		//定义转化后的pcm音频文件存放地址   ===可配置
//		String pcmFilePath="D:\\test.pcm";
		String pcmFilePath = AudioFileUtils.crtAudioPathByUUIDTTS(VPRSUtils.getUUID(),channel,".pcm");
		AudioFileUtils.createNewFileByPath(pcmFilePath);
		logger.info("PCM文件路径："+pcmFilePath);
		try{
	    //这边肯定是调用转换，并将信息放到outMessage中
		String errorCode=AudioTts.start(text,pcmFilePath);  //返回的错误码
		if(errorCode.equals("0")){
			outMessage.set("returnCode", "000000");
			outMessage.set("returnMsg", pcmFilePath);
		}else{
			outMessage.set("returnCode", "999999");
			outMessage.set("returnMsg", "文本合成语音文件失败");
			return outMessage;
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("文本合成语音文件失败");
			outMessage.set("returnCode", "330000");
			outMessage.set("returnMsg", "文本合成语音调用异常");
			return outMessage;
		}
		
        //定义转化后的amr音频文件存放地址   ===可配置
		String amrFilePath=AudioFileUtils.crtAudioPathByUUIDTTS(VPRSUtils.getUUID(),channel,".amr");;
		
//		ttsInfo.setCloseAudio(amrFilePath);
		//根据生成的pcm文件路径转化为amr文件
		try{
		PcmToAmr.changeToAmr(pcmFilePath, amrFilePath);
		outMessage.set("returnCode", "000000");
		outMessage.set("returnMsg", amrFilePath);
		logger.info("AMR文件路径："+amrFilePath+"=========================");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("文件格式转换错误");
			outMessage.set("returnCode", "999999");
			outMessage.set("returnMsg", "文件格式转换错误");
		}
		
		
		
		return outMessage;
	}
	
	
	public static final byte[] inputbyte(InputStream inStream)throws IOException {  
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
		byte[] buff = new byte[1024];  
		int rc = 0;  
		while ((rc = inStream.read(buff, 0, 100)) > 0) {  
		    swapStream.write(buff, 0, rc);  
		}  
		byte[] in2b = swapStream.toByteArray();  
		return in2b;  
	}
	
	@Bizlet("将调用文本转语音的数据添加进流水表中")
	public DataObject insertTtsInfo(DataObject inMessage,DataObject outMessage,TtsInfo ttsInfo,IKernelServiceContext context){
//		if (StringUtils.isBlank(inMessage.getString("text"))) {
//			outMessage.set("returnCode",
//					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
		
        //首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String text=inMessage.getString("text");
		
//		iatinfo.setCloseAudio(closeAudio);
		ttsInfo.setText(text);
		
		
		
		
		
		 //将科大讯飞引擎调用后的值赋值
		if(outMessage.getString("returnCode").equals("000000")){
			ttsInfo.setStatus("1");
		}
		else{
			ttsInfo.setStatus("2");
		}
		ttsInfo.setReturnCode(outMessage.getString("returnCode"));
		ttsInfo.setReturnMsg(outMessage.getString("returnMsg"));  //数据库中放入的是amr文件的路径
		
		
        //将转化后的amr文件再转化为base64字符串   == 返回的报文是base64字符串
		//前提是之前的逻辑效果都是正确的，即returnCode=000000
		if (outMessage.getString("returnCode").equals("000000")) {
			try {
				File file = new File(outMessage.getString("returnMsg"));
				FileInputStream in = new FileInputStream(file);
				String base64File = new BASE64Encoder().encode(inputbyte(in));
				outMessage.set("returnCode", "000000");
				outMessage.set("returnMsg", base64File);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.info("amr编码为base64字符串错误");
				outMessage.set("returnCode", "999999");
				outMessage.set("returnMsg", "amr编码为base64字符串错误");
			}
		}
		
		
		
		//在调用引擎结束后,将数据插入注册明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;		
		//获取请求报文
		Object requestObj = payload.getMessagePayload();
		DataObject requestSDO = null;
		if (requestObj != null && requestObj instanceof DataObject) {
			requestSDO = (DataObject) requestObj;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("==== TD ====requestSDO error");
			}
		}
		String transId = (String) payload.getSystemHeaders().get(TransFlowConstant.HEAD_TRANS_ID);
		ttsInfo.setJnlSeqNo(transId);//流水号
		String globalSeqNo = (String) requestSDO.get(TransFlowConstant.HEADER+"/"+TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		ttsInfo.setChannelSeqNo(globalSeqNo);  //渠道流水号、全局流水号
		String transCode = "";	
		//获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest().getRequestMessage()
		.getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if(transportDefine != null){
			transCode = transportDefine.getId();
		}
		ttsInfo.setTransCode(transCode); 	
		if (logger.isDebugEnabled()) {
			logger.debug("========ttsInfo="+ttsInfo.toString() );
		}
		//设置交易描述
		ttsInfo.setTransDesc("文本合成语音tts"); 
		
		//插入服务器节点
		String ip = NetUtils.getLocalHost();
		ttsInfo.setServerNode(ip);//服务器节点
			
		
		String insertSqlId = "com.pccc.vprs.servicecustom.sql.AudioTransform.insertIntoTtsTable";

		try {
			DatabaseExt.executeNamedSql("default", insertSqlId, ttsInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入文本合成语音流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入文本合成语音流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);	
		}
		return outMessage;
		
		
		
	}
}
