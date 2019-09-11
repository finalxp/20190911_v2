/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.iat;


import org.apache.commons.lang.StringUtils;

import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.common.util.NetUtils;
import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicecustom.transflow.TransFlowConstant;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicecustom.util.XMLToolKit;
import com.pccc.vprs.servicedisplay.vprs.audio.AudioDetect;
import com.pccc.vprs.servicedisplay.vprs.common.ReturnCode;
import com.pccc.vprs.servicedisplay.vprs.model.IatInfo;
import com.pccc.vprs.servicedisplay.vprs.model.UserInfo;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.btp.api.engine.context.IKernelServiceContext;
import com.primeton.btp.api.engine.context.IServiceResponse;
import com.primeton.btp.spi.transportservice.def.TransportServiceDefinition;
import com.primeton.esb.message.impl.DefaultMessagePayload;
import com.pccc.vprs.servicedisplay.bams.util.AudioFormatChange;
import com.pccc.vprs.servicedisplay.bams.util.AudioIat;

import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-22 15:40:15
 *
 */
@Bizlet("语音识别iat,将语音转化为文本")
public class Iat {
	
	private static ILogger logger = LoggerFactory.getLogger(Iat.class);
	
	@Bizlet("iat:语音转化为文本") 
	public DataObject audioToText(DataObject inMessage,DataObject outMessage,IatInfo iatinfo){	
	 	logger.info("进入iat:语音转化为文本");
		//首先获取音频文件的base64编码格式
		
		String para[]={"closeAudio","audioFormat"};
		for (int i = 0; i < para.length; i++) {
			if(StringUtils.isBlank(inMessage.getString(para[i]))){
				outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
				outMessage.set("returnMsg", "输入参数必输项丢失");
				return outMessage;
			}
		}
		
		
		String closeAudio=inMessage.getString("closeAudio");
		String audioFormat=inMessage.getString("audioFormat");
		
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		
		//首先对两者参数先校验一下
		if(StringUtils.isBlank(closeAudio)||StringUtils.isBlank(audioFormat)){
			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		
		//将base64位的音频字符串解码并存放到指定的路径下
		try {
//			//此处路径为传入解码后的原始音频存放路径
			String directory = AudioFileUtils.crtAudioPathByUUID(VPRSUtils.getUUID(),channel,audioFormat);
			logger.info("个人音频存储路径："+directory);
			iatinfo.setCloseAudio(directory);
			AudioFileUtils.convertBase64DataToAudio(closeAudio, directory);	
			//此处先简单做amr和wav两种判断
		String directorydst=directory;
		if(audioFormat.equals(".amr")){
		//获取转化格式后的目标路径
		directorydst = AudioFileUtils.crtAudioPathByUUID(VPRSUtils.getUUID(),channel,".wav");
		AudioFormatChange.changeToWav(directory, directorydst);
		}
		//下面就调取iat的相关实现函数
		AudioIat audioIat=new AudioIat();
		String[] args={""};
		outMessage=audioIat.start(args,directorydst,outMessage);
		logger.info("科大讯飞语音返回码："+outMessage.getString("returnCode"));
		logger.info("科大讯飞语音返回值："+outMessage.getString("returnMsg"));
		return outMessage;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
			outMessage.set("returnMsg", "语音转文本服务调用异常");
			return outMessage;
		}
	}
	
	@Bizlet("将调用语音转文本的数据添加进流水表中")
	public DataObject insertIatInfo(DataObject inMessage,DataObject outMessage,IatInfo iatinfo,IKernelServiceContext context){
//		String para[]={"closeAudio","audioFormat"};
//		for (int i = 0; i < para.length; i++) {
//			if(StringUtils.isBlank(inMessage.getString(para[i]))){
//				outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//				outMessage.set("returnMsg", "输入参数必输项丢失");
//				return outMessage;
//			}
//		}
		
        //首先获取报文传递的参数
		String userName=inMessage.getString("userName");
		String userType=inMessage.getString("userType");
		String certType=inMessage.getString("certType");
		String certNo=inMessage.getString("certNo");
		String userUniqueId=inMessage.getString("userUniqueId");
		String channel=inMessage.getString("channel");
		String businessType=inMessage.getString("businessType");
		String closeAudio=inMessage.getString("closeAudio");
		String audioFormat=inMessage.getString("audioFormat");
		
//		iatinfo.setCloseAudio(closeAudio);
		iatinfo.setAudioFormat(audioFormat);
		
		
		
		
		
		 //将科大讯飞引擎调用后的值赋值
		if(outMessage.getString("returnCode").equals("000000")){
			iatinfo.setStatus("1");
		}
		else{
			iatinfo.setStatus("2");
		}
		iatinfo.setReturnCode(outMessage.getString("returnCode"));
		iatinfo.setReturnMsg(outMessage.getString("returnMsg"));
		
		
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
		iatinfo.setJnlSeqNo(transId);//流水号
		String globalSeqNo = (String) requestSDO.get(TransFlowConstant.HEADER+"/"+TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		iatinfo.setChannelSeqNo(globalSeqNo);  //渠道流水号、全局流水号
		String transCode = "";	
		//获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest().getRequestMessage()
		.getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if(transportDefine != null){
			transCode = transportDefine.getId();
		}
		iatinfo.setTransCode(transCode); 	
		if (logger.isDebugEnabled()) {
			logger.debug("========iatInfo="+iatinfo.toString() );
		}
		//设置交易描述
		iatinfo.setTransDesc("语音转文本iat"); 
		
		//插入服务器节点
		String ip = NetUtils.getLocalHost();
		iatinfo.setServerNode(ip);//服务器节点
			
		
		String insertSqlId = "com.pccc.vprs.servicecustom.sql.AudioTransform.insertIntoIatTable";

		try {
			DatabaseExt.executeNamedSql("default", insertSqlId, iatinfo);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入语音识别流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入语音识别流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);	
		}
		return outMessage;
	}
}
