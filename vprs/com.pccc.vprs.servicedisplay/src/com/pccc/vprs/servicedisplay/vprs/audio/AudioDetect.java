/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;

import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicecustom.transflow.TransDetailModel;
import com.pccc.vprs.servicecustom.transflow.TransFlowConstant;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.FastDFSClientAlone;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicecustom.util.XMLToolKit;
import com.pccc.vprs.servicedisplay.vprs.audio.service.AudioFactory;
import com.pccc.vprs.servicedisplay.vprs.audio.service.audioService;
import com.pccc.vprs.servicedisplay.vprs.common.Constants;
import com.pccc.vprs.servicedisplay.vprs.common.ReturnCode;
import com.pccc.touda.common.util.ConfigUtils;
import com.pccc.touda.common.util.NetUtils;
import com.pccc.vprs.servicedisplay.bams.util.VPRSDaoUtils;
import com.pccc.vprs.servicedisplay.vprs.model.UserInfo;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.btp.api.engine.context.IKernelServiceContext;
import com.primeton.btp.api.engine.context.IServiceResponse;
import com.primeton.btp.spi.transportservice.def.TransportServiceDefinition;
import com.primeton.esb.message.impl.DefaultMessagePayload;
import com.primeton.ext.infra.security.BASE64Encoder;

import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-12 16:02:14
 * 
 */
@Bizlet("声纹注册")
public class AudioDetect {

	private static ILogger logger = LoggerFactory.getLogger(AudioDetect.class);
	
	public static final String operUser = ConfigUtils.getProperty("operUser");
	
	@Bizlet("根据Status用户信息")
	public Boolean queryUserStatusInfo(DataObject inMessage,UserInfo userInfo,Boolean flag) {
		logger.error("进入根据证件类型和证件号查询用户信息");
		if(inMessage.getString("audioType").equals("FS"))
		{
			if(userInfo.getFsStatus()!=null&&userInfo.getFsStatus().equals("0")&&userInfo.getHasFSModel().equals("1"))
			{
				flag = true;
			}else
			{
				flag = false;
			}
		}else if(inMessage.getString("audioType").equals("TD"))
		{
			if(userInfo.getFsStatus()!=null&&userInfo.getTdStatus().equals("0")&&userInfo.getHasTDModel().equals("1"))
			{
				flag = true;
			}else
			{
				flag = false;
			}
		}

		return flag;
	}

	
	/**
	 * 声纹注册（固定文本/随意说）
	 * 
	 * @param inMessage
	 * @param userInfo
	 * @return
	 */
	@Bizlet("根据三要素查询用户信息")
	public UserInfo queryUserInfo(DataObject inMessage,UserInfo userInfo) {
		logger.error("进入根据证件类型和证件号查询用户信息");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userName = inMessage.getString("userName");

		userInfo.setCertType(certType);
		userInfo.setCertNo(certNo);
		userInfo.setUserName(userName);

		String sqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.queryUserInfo";
		try {

			Object[] objs = DatabaseExt.queryByNamedSql("default", sqlId,userInfo);
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					userInfo = (UserInfo) obj;
				}
			} else {
				userInfo = new UserInfo();
			}
		} catch (Exception e) {
			logger.error("根据三要素查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
			throw new BTPRuntimeException("根据三要素查询用户信息数据库操作异常，异常信息:"
					+ e.getMessage(), e);
		}
		return userInfo;
	}
	
	
	@Bizlet("随意说注册明细表插入")
	public DataObject insertFsRegisterDetail(DataObject inMessage, DataObject outMessage, TransDetailModel transDetail,
			IKernelServiceContext context) {
		logger.info("进入随意说注册明细表插入操作");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String riskLevel=inMessage.getString("riskLevel");
		String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
		String closeAudioFormat = inMessage.getString("closeAudioFormat");

		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
		transDetail.setRiskLevel(riskLevel);
//		transDetail.setAudioType("FS");
        transDetail.setUserType(userType);
		transDetail.setScrLevel("00");

//		/**
//		 * 固定文本的话，如果是base64，由于是要查询流水表拼接字段，那流水表音频路径肯定是要放在发布项目下了
//		 */
//
//		// 当传递形式为base64时，做相应处理，将 base64 字符串解析存储到服务器下发布项目目录下，然后将文件的url地址传递给closeAudio
//		if (closeAudioFormat.equals("base64")) {
//			String separator = File.separator;
//
//			String audioLocalPath = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
//
//			try {
//				AudioFileUtils.convertBase64DataToAudio(closeAudio, audioLocalPath);
//				// 数据库中保存的转码之后的路径
//				transDetail.setCloseAudio(audioLocalPath);
//				inMessage.set("closeAudio", audioLocalPath);
//			} catch (IOException e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//				logger.info("存储音频文件到文件服务器出错：" + e.getMessage());
//			}
//		}
//
//		// 当传递方式为url时（前提是该地址文件能够访问）
//		if (closeAudioFormat.equals("url")) {
//			String directory = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
//			try {
//				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
//			} catch (IOException e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储音频文件到文件服务器出错：" + e.getMessage());
//				return outMessage;
//			}
//			// 将url下载后音频的存放位置给报文，以便后面存入数据库
//			inMessage.set("closeAudio", directory);
//			transDetail.setCloseAudio(directory);
//		}
//
//		// 当传递方式为path时
//		if (closeAudioFormat.equals("path")) {
//			String directory = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
//			try {
//				AudioFileUtils.copyToPath(closeAudio, directory);
//			} catch (Exception e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储音频文件到文件服务器出错：" + e.getMessage());
//				return outMessage;
//
//			}
//
//			// 将url下载后音频的存放位置给报文，以便后面存入数据库
//			inMessage.set("closeAudio", directory);
//			transDetail.setCloseAudio(directory);
//
//		}

		// 将引擎调用后的值赋值
		if ("000000".equals(outMessage.getString("returnCode"))) {
			transDetail.setStatus("1");
		} else {
			transDetail.setStatus("2");
		}
		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));

		// 在调用引擎结束后,将数据插入注册明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;
		// 获取请求报文
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
		transDetail.setJnlSeqNo(transId);// 流水号
		String globalSeqNo = (String) requestSDO
				.get(TransFlowConstant.HEADER + "/" + TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
		String transCode = "";
		// 获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest()
				.getRequestMessage().getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if (transportDefine != null) {
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode);
		if (logger.isDebugEnabled()) {
			logger.debug("==== FS ====transDetailInfo=" + transDetail.toString());
		}
		// 设置交易描述
		transDetail.setTransDesc("随意说注册");
		// 获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();
		if (iresponse != null) {
			responseSDO = (DataObject) iresponse.getPayload();
		}

		// 插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		// 插入请求报文
		String transRequestMsg = XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if (transRequestMsg.length() > 32700) {
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		// 插入响应报文 inMessage等价于requestSDO outMessage 等价于responseSDO
		String transResponseMsg = XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if (transResponseMsg.length() > 32700) {
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}

		String insertRegisterSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoFsRegisterTable";
		try {
			DatabaseExt.executeNamedSql("default", insertRegisterSql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入随意说注册明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入随意说注册明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
		}
		logger.info("插入随意说注册明细流水表成功");
		return outMessage;

	}
	@Bizlet("随意说删除明细表插入")
	public DataObject insertFsDeleteDetail(DataObject inMessage,UserInfo userInfo,DataObject outMessage, TransDetailModel transDetail,
			IKernelServiceContext context) {
		logger.info("进入随意说删除明细表插入操作");
		// 首先获取报文传递的参数
				String userName = inMessage.getString("userName");
				String certType = inMessage.getString("certType");
				String certNo = inMessage.getString("certNo");
				String userUniqueId = inMessage.getString("userUniqueId");

		
		if(!"".equals(userInfo.getCertNo()))
		{
		transDetail.setUserinfo_certNo(userInfo.getCertNo());
		}
		else
		{
			transDetail.setUserinfo_certNo(certNo);	
		}
		if(!"".equals(userInfo.getUserName()))
		{
		transDetail.setUserinfo_userName(userInfo.getUserName());
		}
		else
		{
			transDetail.setUserinfo_userName(userName);	
		}
		if(!"".equals(userInfo.getCertType()))
		{
		transDetail.setUserinfo_certType(userInfo.getCertType());
		}
		else
		{
			transDetail.setUserinfo_certType(certType);	
		}
		transDetail.setUserinfo_crtChannel(userInfo.getCrtChannel());
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setScrLevel("00");

		

		// 将引擎调用后的值赋值
		if ("000000".equals(outMessage.getString("returnCode"))) {
			transDetail.setStatus("1");
		} else {
			transDetail.setStatus("2");
		}
		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));

		// 在调用引擎结束后,将数据插入明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;
		// 获取请求报文
		Object requestObj = payload.getMessagePayload();
		DataObject requestSDO = null;
		if (requestObj != null && requestObj instanceof DataObject) {
			requestSDO = (DataObject) requestObj;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("==== FS ====requestSDO error");
			}
		}
		String transId = (String) payload.getSystemHeaders().get(TransFlowConstant.HEAD_TRANS_ID);
		transDetail.setJnlSeqNo(transId);// 流水号
		String globalSeqNo = (String) requestSDO
				.get(TransFlowConstant.HEADER + "/" + TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
		String transCode = "";
		// 获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest()
				.getRequestMessage().getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if (transportDefine != null) {
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode);
		if (logger.isDebugEnabled()) {
			logger.debug("==== FS ====transDetailInfo=" + transDetail.toString());
		}
		// 设置交易描述
		transDetail.setTransDesc("随意说删除");
		// 获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();
		if (iresponse != null) {
			responseSDO = (DataObject) iresponse.getPayload();
		}

		// 插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		// 插入请求报文
		String transRequestMsg = XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if (transRequestMsg.length() > 32700) {
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		// 插入响应报文 inMessage等价于requestSDO outMessage 等价于responseSDO
		String transResponseMsg = XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if (transResponseMsg.length() > 32700) {
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}

		String insertRegisterSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoFsDeleteTable";
		try {
			DatabaseExt.executeNamedSql("default", insertRegisterSql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入随意说删除明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入随意说删除明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
		}
		logger.info("插入随意说删除明细流水表成功");
		return outMessage;

	}
	
	
	@Bizlet("随意说更新明细表插入")
	public DataObject insertFsUpdateDetail(DataObject inMessage, DataObject outMessage, TransDetailModel transDetail,
			IKernelServiceContext context) {
		logger.info("进入随意说更新明细表插入操作");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String riskLevel=inMessage.getString("riskLevel");
		String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
		String closeAudioFormat = inMessage.getString("closeAudioFormat");

		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
		transDetail.setRiskLevel(riskLevel);
//		transDetail.setAudioType("FS");
        transDetail.setUserType(userType);
		transDetail.setScrLevel("00");


		// 将引擎调用后的值赋值
		if ("000000".equals(outMessage.getString("returnCode"))) {
			transDetail.setStatus("1");
		} else {
			transDetail.setStatus("2");
		}
		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));

		// 在调用引擎结束后,将数据插入注册明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;
		// 获取请求报文
		Object requestObj = payload.getMessagePayload();
		DataObject requestSDO = null;
		if (requestObj != null && requestObj instanceof DataObject) {
			requestSDO = (DataObject) requestObj;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("==== FS ====requestSDO error");
			}
		}
		String transId = (String) payload.getSystemHeaders().get(TransFlowConstant.HEAD_TRANS_ID);
		transDetail.setJnlSeqNo(transId);// 流水号
		String globalSeqNo = (String) requestSDO
				.get(TransFlowConstant.HEADER + "/" + TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
		String transCode = "";
		// 获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest()
				.getRequestMessage().getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if (transportDefine != null) {
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode);
		if (logger.isDebugEnabled()) {
			logger.debug("==== FS ====transDetailInfo=" + transDetail.toString());
		}
		// 设置交易描述
		transDetail.setTransDesc("随意说更新");
		// 获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();
		if (iresponse != null) {
			responseSDO = (DataObject) iresponse.getPayload();
		}

		// 插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		// 插入请求报文
		String transRequestMsg = XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if (transRequestMsg.length() > 32700) {
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		// 插入响应报文 inMessage等价于requestSDO outMessage 等价于responseSDO
		String transResponseMsg = XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if (transResponseMsg.length() > 32700) {
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}

		String insertRegisterSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoFsUpdateTable";
		try {
			DatabaseExt.executeNamedSql("default", insertRegisterSql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入随意说更新明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入随意说更新明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
		}
		logger.info("插入随意说更新明细流水表成功");
		return outMessage;

	}
	
	@Bizlet("随意说更新明细表插入")
	public DataObject insertFsVerifyDetail(DataObject inMessage, DataObject outMessage, TransDetailModel transDetail,
			IKernelServiceContext context) {
		logger.info("进入随意说验证明细表插入操作");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String riskLevel=inMessage.getString("riskLevel");
		String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
        String score=outMessage.getString("info/similarity");
        String compareResult=outMessage.getString("info/compareResult");
//		if(StringUtils.isNotBlank((String) responseSDO.get("info/similarity"))){
//			transDetail.setScore((String) responseSDO.get("info/similarity"));
//		}	
//		if(StringUtils.isNotBlank((String) responseSDO.get("info/compareResult"))){
//			transDetail.setCompareResult((String) responseSDO.get("info/compareResult"));
//		}
        transDetail.setScore(score);
		transDetail.setCompareResult(compareResult);
		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
		transDetail.setRiskLevel(riskLevel);
//		transDetail.setAudioType("FS");
        transDetail.setUserType(userType);
		transDetail.setScrLevel("00");
//		transDetail.setScore(score);


		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));

		// 在调用引擎结束后,将数据插入注册明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;
		// 获取请求报文
		Object requestObj = payload.getMessagePayload();
		DataObject requestSDO = null;
		if (requestObj != null && requestObj instanceof DataObject) {
			requestSDO = (DataObject) requestObj;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("==== FS ====requestSDO error");
			}
		}
		String transId = (String) payload.getSystemHeaders().get(TransFlowConstant.HEAD_TRANS_ID);
		transDetail.setJnlSeqNo(transId);// 流水号
		String globalSeqNo = (String) requestSDO
				.get(TransFlowConstant.HEADER + "/" + TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
		String transCode = "";
		// 获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest()
				.getRequestMessage().getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if (transportDefine != null) {
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode);
		if (logger.isDebugEnabled()) {
			logger.debug("==== FS ====transDetailInfo=" + transDetail.toString());
		}
		// 设置交易描述
		transDetail.setTransDesc("随意说验证");
		// 获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();
		if (iresponse != null) {
			responseSDO = (DataObject) iresponse.getPayload();
		}

		// 插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		

		
		// 插入请求报文
		String transRequestMsg = XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if (transRequestMsg.length() > 32700) {
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		// 插入响应报文 inMessage等价于requestSDO outMessage 等价于responseSDO
		String transResponseMsg = XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if (transResponseMsg.length() > 32700) {
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}

		String insertVerifySql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoFsVerifyTable";
		try {
			DatabaseExt.executeNamedSql("default", insertVerifySql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入随意说更新明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入随意说更新明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
		}
		logger.info("插入随意说更新明细流水表成功");
		return outMessage;

	}
	
	
	@Bizlet("随意说验证明细表插入")
	public DataObject insertFsVerifyDetailCQVoice(DataObject inMessage, DataObject outMessage, TransDetailModel transDetail,
			IKernelServiceContext context) {
		logger.info("进入随意说验证明细表插入操作");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String riskLevel=inMessage.getString("riskLevel");
		String closeAudio = outMessage.getString("info/fileName"); 
        String score=outMessage.getString("info/similarity");
        String compareResult=outMessage.getString("info/compareResult");
//		if(StringUtils.isNotBlank((String) responseSDO.get("info/similarity"))){
//			transDetail.setScore((String) responseSDO.get("info/similarity"));
//		}	
//		if(StringUtils.isNotBlank((String) responseSDO.get("info/compareResult"))){
//			transDetail.setCompareResult((String) responseSDO.get("info/compareResult"));
//		}
        transDetail.setScore(score);
		transDetail.setCompareResult(compareResult);
		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
		transDetail.setRiskLevel(riskLevel);
//		transDetail.setAudioType("FS");
        transDetail.setUserType(userType);
		transDetail.setScrLevel("00");
//		transDetail.setScore(score);


		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));

		// 在调用引擎结束后,将数据插入注册明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;
		// 获取请求报文
		Object requestObj = payload.getMessagePayload();
		DataObject requestSDO = null;
		if (requestObj != null && requestObj instanceof DataObject) {
			requestSDO = (DataObject) requestObj;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("==== FS ====requestSDO error");
			}
		}
		String transId = (String) payload.getSystemHeaders().get(TransFlowConstant.HEAD_TRANS_ID);
		transDetail.setJnlSeqNo(transId);// 流水号
		String globalSeqNo = (String) requestSDO
				.get(TransFlowConstant.HEADER + "/" + TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
		String transCode = "";
		// 获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest()
				.getRequestMessage().getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if (transportDefine != null) {
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode);
		if (logger.isDebugEnabled()) {
			logger.debug("==== FS ====transDetailInfo=" + transDetail.toString());
		}
		// 设置交易描述
		transDetail.setTransDesc("随意说验证");
		// 获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();
		if (iresponse != null) {
			responseSDO = (DataObject) iresponse.getPayload();
		}

		// 插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		

		
		// 插入请求报文
		String transRequestMsg = XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if (transRequestMsg.length() > 32700) {
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		// 插入响应报文 inMessage等价于requestSDO outMessage 等价于responseSDO
		String transResponseMsg = XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if (transResponseMsg.length() > 32700) {
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}
		long start = System.currentTimeMillis();
		String insertVerifySql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoFsVerifyTable";
		try {
			DatabaseExt.executeNamedSql("default", insertVerifySql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入随意说更新明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入随意说更新明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
		}
		 long end = System.currentTimeMillis();
		logger.error("验证流水入库耗时--->" + (end - start));
		logger.info("插入随意说更新明细流水表成功");
		return outMessage;

	}
	
	@Bizlet("固定文本调用接口注册声纹")
	public DataObject enrolSpeaker(DataObject inMessage, DataObject outMessage,TransDetailModel transDetail,IKernelServiceContext context,UserInfo userInfo) {
		logger.info("进入声纹注册服务");
		
		/*调用validsoft 固定文本注册一次，更新两次*/
		String closeAudioTd1=userInfo.getCloseAudioTd1();  //固定文本注册成功的音频存放地址
		
		String closeAudioTd2=userInfo.getCloseAudioTd2();  //固定文本注册成功顺延第一条
		
		String closeAudioTd3=userInfo.getCloseAudioTd3();  //固定文本注册成功顺延第二条
		
		//当第一条有效音频进来
		if(StringUtils.isBlank(closeAudioTd1)&&StringUtils.isBlank(closeAudioTd2)&&StringUtils.isBlank(closeAudioTd3)) {
			try {
				audioService f = AudioFactory
						.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
				if (f != null) {
					DataObject obj = f.enrolSpeaker(inMessage, outMessage);
					outMessage.set("returnCode", obj.getString("returnCode"));
					outMessage.set("returnMsg", obj.getString("returnMsg"));
				}
			} catch (Exception e) {
				outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
				outMessage.set("returnMsg", "声纹注册异常:" + e.getMessage());
			}
			
			if(ReturnCode.TOUDA_SUCCESS.equals(outMessage.get("returnCode"))){
				outMessage.set("registerStatus", "1");  //需要更多音频
				transDetail.setStatus("1");   //状态成功
			}else {
				transDetail.setStatus("2");   //状态失败
			}
		}
		//当第二条有效音频进来
		else if(!StringUtils.isBlank(closeAudioTd1)&&StringUtils.isBlank(closeAudioTd2)&&StringUtils.isBlank(closeAudioTd3)) {
			try {
				audioService f = AudioFactory
						.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
				if (f != null) {
					DataObject obj = f.updateSpeaker(inMessage, outMessage);
					outMessage.set("returnCode", obj.getString("returnCode"));
					outMessage.set("returnMsg", obj.getString("returnMsg"));
				}
			} catch (Exception e) {
				outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
				outMessage.set("returnMsg", "声纹注册异常:" + e.getMessage());
			}
			if(ReturnCode.TOUDA_SUCCESS.equals(outMessage.get("returnCode"))){
				outMessage.set("registerStatus", "1"); //需要更多音频
				transDetail.setStatus("1");   //状态成功
			}else {
				transDetail.setStatus("2");   //状态失败
			}
		}
		//当第三条有效音频进来
		else {
			try {
				audioService f = AudioFactory
						.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
				if (f != null) {
					DataObject obj = f.updateSpeaker(inMessage, outMessage);
					outMessage.set("returnCode", obj.getString("returnCode"));
					outMessage.set("returnMsg", obj.getString("returnMsg"));
				}
			} catch (Exception e) {
				outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
				outMessage.set("returnMsg", "声纹注册异常:" + e.getMessage());
			}
			if(ReturnCode.TOUDA_SUCCESS.equals(outMessage.get("returnCode"))){
	            //0表示注册成功
				outMessage.set("registerStatus", "0");  //注册成功
				transDetail.setStatus("1");   //状态成功
			}else {
				transDetail.setStatus("2");   //状态失败
			}
		}
		
		
		
//		//将引擎调用后的值赋值
//		if(outMessage.getString("returnMsg").equals("固定文本注册成功")){
//			transDetail.setStatus("1");
//		}
//		else if(outMessage.getString("returnMsg").equals("请输入更多音频")){
//			transDetail.setStatus("3");
//		}
//		else{
//			transDetail.setStatus("2");
//		}
		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));
		
		
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
				logger.debug("==== TransServiceHandler.afterReply ====requestSDO error");
			}
//			return;
		}
		String transId = (String) payload.getSystemHeaders().get(TransFlowConstant.HEAD_TRANS_ID);
		transDetail.setJnlSeqNo(transId);//流水号
		String globalSeqNo = (String) requestSDO.get(TransFlowConstant.HEADER+"/"+TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo);  //渠道流水号、全局流水号
		String transCode = "";	
		//获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest().getRequestMessage()
		.getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if(transportDefine != null){
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode); 	
		if (logger.isDebugEnabled()) {
			logger.debug("==== TransServiceHandler.afterReply ====transDetailInfo="+transDetail.toString() );
		}
		//设置交易描述
		transDetail.setTransDesc("固定文本注册"); 
		//获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();		
		if(iresponse != null){
			responseSDO = (DataObject) iresponse.getPayload();			
		}
		
		//插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);//服务器节点
		//插入请求报文
		String  transRequestMsg=XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if(transRequestMsg.length()>32700){
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		//插入响应报文  inMessage等价于requestSDO   outMessage 等价于responseSDO
		String transResponseMsg=XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if(transResponseMsg.length()>32700){
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}
		//将数据插入注册明细流水表
		insertRegisterDetail(inMessage,transDetail);

		return outMessage;
		
	}
	
	
	
	
	/**
	 * 固定文本添加注册明细流水表 (未注册的情况下)
	 * @param inMessage
	 * @param outMessage
	 * @param transDetail
	 */
	public void insertRegisterDetail(DataObject inMessage,TransDetailModel transDetail){
//		 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
		String closeAudioFormat=inMessage.getString("closeAudioFormat");
		
		transDetail.setUserName(userName);
		transDetail.setCertType(certType);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
        transDetail.setAudioType("TD");
		
        transDetail.setScrLevel("00");
        
        
		String insertRegisterSql="com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoTdRegisterTable";
		try {
			DatabaseExt.executeNamedSql("default", insertRegisterSql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入注册明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入注册明细流水表数据库操作异常，异常信息:"
					+ e1.getMessage(), e1);
		}
		logger.info("插入注册明细流水表成功！");
	}
	
	/**
	 * 固定文本添加注册明细流水表 (除未注册的情况下)
	 * @param inMessage
	 * @param outMessage
	 * @param transDetail
	 */
	@Bizlet("固定文本添加注册明细流水表 (除未注册的情况下)")
	public DataObject insertRegisterDetailOther(DataObject inMessage,DataObject outMessage,TransDetailModel transDetail,IKernelServiceContext context){
        //首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
		String closeAudioFormat=inMessage.getString("closeAudioFormat");
		
		
		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
//        transDetail.setAudioType("TD");
		
        transDetail.setScrLevel("00");
        
        /**
         * 固定文本的话，如果是base64，由于是要查询流水表拼接字段，那流水表音频路径肯定是要放在发布项目下了
         */
        
        //当传递形式为base64时，做相应处理，将 base64 字符串解析存储到服务器下发布项目目录下，然后将文件的url地址传递给closeAudio
		if (closeAudioFormat.equals("base64")) {
			String separator = File.separator;
//			//音频在发布项目子目录下的地址  
//			String audioWebPathIn = VPRSUtils.getTime("yyyyMMdd") + separator
//					+ channel + separator + VPRSUtils.getUUID() + ".wav";
//			//linux中音频的地址
//			String audioLocalPath = VprsConstant.JILIXUN_SHARE_PATH_REGISTER_TD + separator
//					+ audioWebPathIn;

			String audioLocalPath=AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			
			try {
				AudioFileUtils.convertBase64DataToAudio(closeAudio,
						audioLocalPath);
                 //数据库中保存的转码之后的路径
				 transDetail.setCloseAudio(audioLocalPath);
				 inMessage.set("closeAudio", audioLocalPath);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logger.info("存储音频文件到文件服务器出错："+e.getMessage());
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
//				return outMessage;
			}
		}
      
		
		
		 //当传递方式为url时（前提是该地址文件能够访问）
		if(closeAudioFormat.equals("url")){
			String directory=AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;	
			}
			//将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			transDetail.setCloseAudio(directory);
		}
		
		
        //当传递方式为path时
		if(closeAudioFormat.equals("path")){
			String directory=AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			transDetail.setCloseAudio(directory);
			
		}
		
        
        //将引擎调用后的值赋值
		if(outMessage.getString("returnMsg").equals("用户已注册声纹")){
			transDetail.setStatus("1");
		}
		else{
			transDetail.setStatus("2");
		}
		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));
		
		
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
		transDetail.setJnlSeqNo(transId);//流水号
		String globalSeqNo = (String) requestSDO.get(TransFlowConstant.HEADER+"/"+TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo);  //渠道流水号、全局流水号
		String transCode = "";	
		//获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest().getRequestMessage()
		.getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if(transportDefine != null){
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode); 	
		if (logger.isDebugEnabled()) {
			logger.debug("==== TD ====transDetailInfo="+transDetail.toString() );
		}
		//设置交易描述
		transDetail.setTransDesc("固定文本注册"); 
		//获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();		
		if(iresponse != null){
			responseSDO = (DataObject) iresponse.getPayload();			
		}
		
		//插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);//服务器节点
		//插入请求报文
		String  transRequestMsg=XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if(transRequestMsg.length()>32700){
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		//插入响应报文  inMessage等价于requestSDO   outMessage 等价于responseSDO
		String transResponseMsg=XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if(transResponseMsg.length()>32700){
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}
      
        
		String insertRegisterSql="com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoTdRegisterTable";
		try {
			DatabaseExt.executeNamedSql("default", insertRegisterSql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
//			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
//			outMessage.set("returnMsg", "插入注册明细流水表失败！");
			logger.error("插入注册明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入注册明细流水表数据库操作异常，异常信息:"
					+ e1.getMessage(), e1);
		}
		logger.info("插入注册明细流水表成功");
//		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//		outMessage.set("returnMsg", "插入注册明细流水表成功！");
		return outMessage;
	}
	
	
	@Bizlet("随意说验证明细表插入")
	public DataObject insertVerifyDetail(DataObject inMessage, DataObject outMessage, TransDetailModel transDetail,
			IKernelServiceContext context) {

		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
		String closeAudioFormat = inMessage.getString("closeAudioFormat");

		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setCertNo(certNo);
		transDetail.setUserUniqueId(userUniqueId);
		transDetail.setChannel(channel);
		transDetail.setBusinessType(businessType);
		transDetail.setCloseAudio(closeAudio);
		transDetail.setAudioType("FS");

		transDetail.setScrLevel("00");

		/**
		 * 固定文本的话，如果是base64，由于是要查询流水表拼接字段，那流水表音频路径肯定是要放在发布项目下了
		 */

		// 当传递形式为base64时，做相应处理，将 base64 字符串解析存储到服务器下发布项目目录下，然后将文件的url地址传递给closeAudio
		if (closeAudioFormat.equals("base64")) {
			String separator = File.separator;

			String audioLocalPath = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);

			try {
				AudioFileUtils.convertBase64DataToAudio(closeAudio, audioLocalPath);
				// 数据库中保存的转码之后的路径
				transDetail.setCloseAudio(audioLocalPath);
				inMessage.set("closeAudio", audioLocalPath);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logger.info("存储音频文件到文件服务器出错：" + e.getMessage());
			}
		}

		// 当传递方式为url时（前提是该地址文件能够访问）
		if (closeAudioFormat.equals("url")) {
			String directory = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错：" + e.getMessage());
				return outMessage;
			}
			// 将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			transDetail.setCloseAudio(directory);
		}

		// 当传递方式为path时
		if (closeAudioFormat.equals("path")) {
			String directory = AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错：" + e.getMessage());
				return outMessage;

			}

			// 将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			transDetail.setCloseAudio(directory);

		}

		// 将引擎调用后的值赋值
		if (outMessage.getString("returnMsg").equals("用户已注册声纹")) {
			transDetail.setStatus("1");
		} else {
			transDetail.setStatus("2");
		}
		transDetail.setReturnCode(outMessage.getString("returnCode"));
		transDetail.setReturnMsg(outMessage.getString("returnMsg"));

		// 在调用引擎结束后,将数据插入注册明细流水表
		Object cc = context.getServiceRequest().getRequestMessage().getPayload();
		DefaultMessagePayload payload = (DefaultMessagePayload) cc;
		// 获取请求报文
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
		transDetail.setJnlSeqNo(transId);// 流水号
		String globalSeqNo = (String) requestSDO
				.get(TransFlowConstant.HEADER + "/" + TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
		transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
		String transCode = "";
		// 获取交易码
		TransportServiceDefinition transportDefine = (TransportServiceDefinition) context.getServiceRequest()
				.getRequestMessage().getHeaders().get(TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
		if (transportDefine != null) {
			transCode = transportDefine.getId();
		}
		transDetail.setTransCode(transCode);
		if (logger.isDebugEnabled()) {
			logger.debug("==== TD ====transDetailInfo=" + transDetail.toString());
		}
		// 设置交易描述
		transDetail.setTransDesc("固定文本注册");
		// 获取返回报文
		DataObject responseSDO = null;
		IServiceResponse iresponse = context.getServiceResponse();
		if (iresponse != null) {
			responseSDO = (DataObject) iresponse.getPayload();
		}

		// 插入服务器节点
		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		// 插入请求报文
		String transRequestMsg = XMLToolKit.parseStringXml(requestSDO.toString());
		transDetail.setTransRequestMsg(transRequestMsg);
		if (transRequestMsg.length() > 32700) {
			StringUtils.left(transRequestMsg, 32699);
			transDetail.setTransRequestMsg(transRequestMsg);
		}
		// 插入响应报文 inMessage等价于requestSDO outMessage 等价于responseSDO
		String transResponseMsg = XMLToolKit.parseStringXml(outMessage.toString());
		transDetail.setTransResponseMsg(transResponseMsg);
		if (transResponseMsg.length() > 32700) {
			StringUtils.left(transResponseMsg, 32699);
			transDetail.setTransResponseMsg(transResponseMsg);
		}

		String insertRegisterSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoTdRegisterTable";
		try {
			DatabaseExt.executeNamedSql("default", insertRegisterSql, transDetail);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("插入验证明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("插入注册明细流水表数据库操作异常，异常信息:" + e1.getMessage(), e1);
		}
		logger.info("插入验证明细流水表成功");
		return outMessage;

		// return null;
	}
	
	
	
	@Bizlet("随意说调用接口注册声纹")
	public DataObject enrolSpeakerIndependent(DataObject inMessage,DataObject outMessage) {
		logger.info("进入声纹随意说注册服务");
		
		try {
			audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
			if (f != null) {
				outMessage = f.enrolSpeakerIndependent(inMessage,outMessage);
			}
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
			outMessage.set("returnMsg", "声纹注册异常:" + e.getMessage());
		}
		return outMessage;
	}
	
	
	@Bizlet("固定文本添加声纹用户信息表==validsoft")
	public DataObject insertUserInfo(DataObject inMessage,
			DataObject outMessage, UserInfo userInfo) {
		logger.info("进入添加用户信息");
		
		// 在做第一次插入表的操作时，根据先前查出来userInfo的结果，再做一次校验，决定是插入还是更新
		String certNoFromUserInfo = userInfo.getCertNo();
		
		//获取固定文本已存取了几条音频
		String closeAudioTd1=userInfo.getCloseAudioTd1();  //固定文本注册成功的音频存放地址
		
		String closeAudioTd2=userInfo.getCloseAudioTd2();  //固定文本注册成功顺延第一条
		
		String closeAudioTd3=userInfo.getCloseAudioTd3();  //固定文本注册成功顺延第二条
		
		if (StringUtils.isBlank(certNoFromUserInfo)) {
			// 插入数据库
			logger.info("用户注册信息表无该用户任何声纹信息");
			String para[] = { "channel", "certType", "closeAudio", "certNo",
					"userName", "userType" };
			for (int i = 0; i < para.length; i++) {
				if (StringUtils.isBlank(inMessage.getString(para[i]))) {
					outMessage.set("returnCode",
							ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
					outMessage.set("returnMsg", "输入参数必输项丢失");
					return outMessage;
				}
			}

			// 首先获取报文传递的参数
			String userName = inMessage.getString("userName");
			String userType = inMessage.getString("userType");
			String certType = inMessage.getString("certType");
			String certNo = inMessage.getString("certNo");
			String userUniqueId = inMessage.getString("userUniqueId");
			String channel = inMessage.getString("channel");
			String businessType = inMessage.getString("businessType");
			String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url

			userInfo = new UserInfo();
			userInfo.setCertNo(certNo);
			userInfo.setUserName(userName);
			userInfo.setCertType(certType);
			userInfo.setCrtChannel(channel);
//			userInfo.setCloseAudio(closeAudio);
			
			if(StringUtils.isBlank(closeAudioTd1)) {
				userInfo.setCloseAudioTd1(closeAudio);
			}else if(StringUtils.isBlank(closeAudioTd2)) {
				userInfo.setCloseAudioTd2(closeAudio);
			}else if(StringUtils.isBlank(closeAudioTd3)) {
				userInfo.setCloseAudioTd3(closeAudio);
			}
			
						

			// userInfo的固定文本模型设置根据注册的返回值进行设置
			if (outMessage.getString("registerStatus") == "0") {
				userInfo.setHasTDModel("1");
//				//当注册成功的情况下对表音频字段操作
//				/*注册成功的情况下，将本次输入音频及按时序查出的前2条音频插入用户注册信息表*/  
//				//此处做一个判断只有注册成功时，才更新数据库字段，那这里需要再查询下界面，不管是基立迅还是Validsoft应该放在这边好些
//				TransDetailModel transDetail = new TransDetailModel();
//				transDetail.setAudioType("TD");
//				transDetail.setCertNo(certNo);
//				transDetail.setCertType(certType);
//				transDetail.setUserName(userName);
//				transDetail.setChannel(channel);
//				//根据查询条件查出前三条记录
//				String queryRegisterDetailSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryRegisterTableSuccess";
//	            //查询出前三条记录  但我们查询到的记录数应为>=2条,validsoft可能只要一条就成功了
//				try{
//				Object[] objs = DatabaseExt.queryByNamedSql("default",
//						queryRegisterDetailSql, transDetail);
//			    if(objs!=null&& objs.length > 0){
//			    	//
//			    	TransDetailModel transDetailNew=new TransDetailModel();
//			    	transDetailNew = (TransDetailModel) objs[0];
//			    	userInfo.setCloseAudioTd1(transDetailNew.getCloseAudio());
//			    	if(objs.length==2){
//			    		transDetailNew = (TransDetailModel) objs[1];
//			    		userInfo.setCloseAudioTd2(transDetailNew.getCloseAudio());
//			    	}
//			    	if(objs.length==3){
//			    		transDetailNew = (TransDetailModel) objs[2];
//			    		userInfo.setCloseAudioTd3(transDetailNew.getCloseAudio());
//			    	}
//			    }
//				}catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					logger.error("查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
//				}
				
			} else if (outMessage.getString("registerStatus") == "1") {
				userInfo.setHasTDModel("2");
			}

			userInfo.setTdStatus(Constants.TD_STATUS_OPEN);


			String insertSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.insertUserInfo";
			
			try {
				DatabaseExt.executeNamedSql("default", insertSqlId, userInfo);
			} catch (Exception e1) {
				e1.printStackTrace();
				outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
				outMessage.set("returnMsg", "插入用户信息表失败！");
				logger.error("插入用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
				throw new BTPRuntimeException("插入用户信息表数据库操作异常，异常信息:"
						+ e1.getMessage(), e1);
			}
			logger.info("添加用户信息成功！");
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "添加用户信息成功！");
			return outMessage;

		} else {
			// 更新数据库
			logger.info("用户注册信息表已拥有声纹信息");
			outMessage = updateUserInfo(inMessage, outMessage, userInfo);
			return outMessage;
		}

	}

//	@Bizlet("固定文本添加声纹用户信息表==基立迅")
//	public DataObject insertUserInfo(DataObject inMessage,
//			DataObject outMessage, UserInfo userInfo) {
//		logger.info("进入添加用户信息");
//		
//		// 在做第一次插入表的操作时，根据先前查出来userInfo的结果，再做一次校验，决定是插入还是更新
//		String certNoFromUserInfo = userInfo.getCertNo();
//		if (StringUtils.isBlank(certNoFromUserInfo)) {
//			// 插入数据库
//			logger.info("用户注册信息表无该用户任何声纹信息");
//			String para[] = { "channel", "certType", "closeAudio", "certNo",
//					"userName", "userType" };
//			for (int i = 0; i < para.length; i++) {
//				if (StringUtils.isBlank(inMessage.getString(para[i]))) {
//					outMessage.set("returnCode",
//							ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//					outMessage.set("returnMsg", "输入参数必输项丢失");
//					return outMessage;
//				}
//			}
//
//			// 首先获取报文传递的参数
//			String userName = inMessage.getString("userName");
//			String userType = inMessage.getString("userType");
//			String certType = inMessage.getString("certType");
//			String certNo = inMessage.getString("certNo");
//			String userUniqueId = inMessage.getString("userUniqueId");
//			String channel = inMessage.getString("channel");
//			String businessType = inMessage.getString("businessType");
//			String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
//
//			userInfo = new UserInfo();
//			userInfo.setCertNo(certNo);
//			userInfo.setUserName(userName);
//			userInfo.setCertType(certType);
//			userInfo.setCrtChannel(channel);
////			userInfo.setCloseAudio(closeAudio);
//			
//			
//						
//
//			// userInfo的固定文本模型设置根据注册的返回值进行设置
//			if (outMessage.getString("registerStatus") == "0") {
//				userInfo.setHasTDModel("1");
//				//当注册成功的情况下对表音频字段操作
//				/*注册成功的情况下，将本次输入音频及按时序查出的前2条音频插入用户注册信息表*/  
//				//此处做一个判断只有注册成功时，才更新数据库字段，那这里需要再查询下界面，不管是基立迅还是Validsoft应该放在这边好些
//				TransDetailModel transDetail = new TransDetailModel();
//				transDetail.setAudioType("TD");
//				transDetail.setCertNo(certNo);
//				transDetail.setCertType(certType);
//				transDetail.setUserName(userName);
//				transDetail.setChannel(channel);
//				//根据查询条件查出前三条记录
//				String queryRegisterDetailSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryRegisterTableSuccess";
//	            //查询出前三条记录  但我们查询到的记录数应为>=2条,validsoft可能只要一条就成功了
//				try{
//				Object[] objs = DatabaseExt.queryByNamedSql("default",
//						queryRegisterDetailSql, transDetail);
//			    if(objs!=null&& objs.length > 0){
//			    	//
//			    	TransDetailModel transDetailNew=new TransDetailModel();
//			    	transDetailNew = (TransDetailModel) objs[0];
//			    	userInfo.setCloseAudioTd1(transDetailNew.getCloseAudio());
//			    	if(objs.length==2){
//			    		transDetailNew = (TransDetailModel) objs[1];
//			    		userInfo.setCloseAudioTd2(transDetailNew.getCloseAudio());
//			    	}
//			    	if(objs.length==3){
//			    		transDetailNew = (TransDetailModel) objs[2];
//			    		userInfo.setCloseAudioTd3(transDetailNew.getCloseAudio());
//			    	}
//			    }
//				}catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					logger.error("查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
//				}
//				
//			} else if (outMessage.getString("registerStatus") == "1") {
//				userInfo.setHasTDModel("2");
//			}
//
//			userInfo.setTdStatus(Constants.TD_STATUS_OPEN);
//
//
//			String insertSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.insertUserInfo";
//			
//			try {
//				DatabaseExt.executeNamedSql("default", insertSqlId, userInfo);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
//				outMessage.set("returnMsg", "插入用户信息表失败！");
//				logger.error("插入用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
//				throw new BTPRuntimeException("插入用户信息表数据库操作异常，异常信息:"
//						+ e1.getMessage(), e1);
//			}
//			logger.info("添加用户信息成功！");
//			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//			outMessage.set("returnMsg", "添加用户信息成功！");
//			return outMessage;
//
//		} else {
//			// 更新数据库
//			logger.info("用户注册信息表已拥有声纹信息");
//			outMessage = updateUserInfo(inMessage, outMessage, userInfo);
//			return outMessage;
//		}
//
//	}
	
	
	@Bizlet("随意说添加声纹用户信息表")
	public DataObject insertUserInfoIndependent(DataObject inMessage,DataObject outMessage, UserInfo userInfo) {
			logger.info("随意说进入添加用户信息");		
			
			String para[] = { "channel", "certType", "closeAudio", "certNo",
					"userName","closeAudioFormat"};
			for (int i = 0; i < para.length; i++) {
				if (StringUtils.isBlank(inMessage.getString(para[i]))) {
					outMessage.set("returnCode",
							ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
					outMessage.set("returnMsg", "输入参数必输项丢失");
					return outMessage;
				}
			}
			
		
			// 在做第一次插入表的操作时，根据先前查出来userInfo的结果，再做一次校验，决定是插入还是更新
			String certNoFromUserInfo = userInfo.getCertNo();
			if (StringUtils.isBlank(certNoFromUserInfo)) {

			// 插入数据库
			logger.info("用户注册信息表无该用户任何声纹信息");
			// 首先获取报文传递的参数
			String channel = inMessage.getString("channel");
			String closeAudio = inMessage.getString("closeAudio"); // 如果是基立迅，则保存的拼接好的音频url
			
			userInfo.setCertNo(inMessage.getString("certNo"));
			userInfo.setUserName(inMessage.getString("userName"));
			userInfo.setCertType(inMessage.getString("certType"));
			userInfo.setCrtChannel(inMessage.getString("channel"));
			userInfo.setUserUniqueId(inMessage.getString("userUniqueId"));
			userInfo.setRiskLevel(inMessage.getString("riskLevel"));
//            userInfo.setUserStatus("0");
			// 既然进入了插入数据库的阶段，即判断已经注册成功了，那随意说模型必然已经有了
			userInfo.setHasFSModel("1");

            // 获取服务状态
			userInfo.setFsStatus(Constants.FS_STATUS_OPEN);
			
			//当音频为base64时，需先进行处理再将音频文件存入指定的文件路径
			if(inMessage.getString("closeAudioFormat").equals("base64")){
				userInfo.setCloseAudioFs(inMessage.getString("closeAudio"));
//				 try {
//				 String directory =AudioFileUtils.crtAudioPathByUUID(VPRSUtils.getUUID(),channel);
//				 logger.info("个人近声存储路径："+directory);
//				 AudioFileUtils.convertBase64DataToAudio(closeAudio, directory);
//				 userInfo.setCloseAudioFs(directory);
//				 } catch (Exception e) {
//				 logger.info("存储音频文件到文件服务器出错");
//				 outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				 outMessage.set("returnMsg", "存储音频文件到文件服务器出错");
//				 return outMessage;
//				 }
			}else if(inMessage.getString("closeAudioFormat").equals("path")) {
				userInfo.setCloseAudioFs(inMessage.getString("closeAudio"));
			}else if(inMessage.getString("closeAudioFormat").equals("url")) {
				userInfo.setCloseAudioFs(inMessage.getString("closeAudio"));
			}
			
			String insertSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.insertUserInfo";
			try {
				DatabaseExt.executeNamedSql("default", insertSqlId, userInfo);
			} catch (Exception e1) {
				e1.printStackTrace();
				outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
				outMessage.set("returnMsg", "插入用户信息表失败！");
				logger.error("插入用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
				throw new BTPRuntimeException("插入用户信息表数据库操作异常，异常信息:"
						+ e1.getMessage(), e1);
			}
            logger.info( "添加用户信息成功！");
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "添加用户信息成功！");
			return outMessage;
			}else {
				// 更新数据库
				logger.info("用户注册信息表已拥有声纹信息");
				outMessage = updateUserInfoIndependent(inMessage, outMessage,
						userInfo);
				return outMessage;
			}

	}

	@Bizlet("更新声纹用户信息表--注册过程中  validsoft")
	public DataObject updateUserInfo(DataObject inMessage,
			DataObject outMessage, UserInfo userInfo) {
		logger.info("进入更新用户注册信息表");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio");

		// 获取固定文本已存取了几条音频
		String closeAudioTd1 = userInfo.getCloseAudioTd1(); // 固定文本注册成功的音频存放地址

		String closeAudioTd2 = userInfo.getCloseAudioTd2(); // 固定文本注册成功顺延第一条

		String closeAudioTd3 = userInfo.getCloseAudioTd3(); // 固定文本注册成功顺延第二条
		
		if(StringUtils.isBlank(closeAudioTd1)) {
			userInfo.setCloseAudioTd1(closeAudio);
		}else if(StringUtils.isBlank(closeAudioTd2)) {
			userInfo.setCloseAudioTd2(closeAudio);
		}else if(StringUtils.isBlank(closeAudioTd3)) {
			userInfo.setCloseAudioTd3(closeAudio);
		}
		
		
		userInfo.setCertNo(certNo);
		userInfo.setUserName(userName);
		userInfo.setCertType(certType);
//		userInfo.setCloseAudio(closeAudio);
		userInfo.setCrtChannel(channel);
		userInfo.setTdStatus(Constants.TD_STATUS_OPEN);
		// userInfo的固定文本模型设置根据注册的返回值进行设置
		if (outMessage.getString("registerStatus") == "0") {
			userInfo.setHasTDModel("1");
			
//			/*注册成功的情况下，将本次输入音频及按时序查出的前2条音频插入用户注册信息表*/  
//			//此处做一个判断只有注册成功时，才更新数据库字段，那这里需要再查询下界面，不管是基立迅还是Validsoft应该放在这边好些
//			TransDetailModel transDetail = new TransDetailModel();
//			transDetail.setAudioType("TD");
//			transDetail.setCertNo(certNo);
//			transDetail.setCertType(certType);
//			transDetail.setUserName(userName);
//			transDetail.setChannel(channel);
//			//根据查询条件查出前三条记录
//			String queryRegisterDetailSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryRegisterTableSuccess";
//	        //查询出前三条记录  但我们查询到的记录数应为>=2条,validsoft可能只要一条就成功了
//			try{
//			Object[] objs = DatabaseExt.queryByNamedSql("default",
//					queryRegisterDetailSql, transDetail);
//			if(objs!=null&& objs.length > 0){
//		    	//
//		    	TransDetailModel transDetailNew=new TransDetailModel();
//		    	transDetailNew = (TransDetailModel) objs[0];
//		    	userInfo.setCloseAudioTd1(transDetailNew.getCloseAudio());
//		    	if(objs.length==2){
//		    		transDetailNew = (TransDetailModel) objs[1];
//		    		userInfo.setCloseAudioTd2(transDetailNew.getCloseAudio());
//		    	}
//		    	if(objs.length==3){
//		    		transDetailNew = (TransDetailModel) objs[2];
//		    		userInfo.setCloseAudioTd3(transDetailNew.getCloseAudio());
//		    	}
//		    }
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				logger.error("查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
//			}
			
		} else if (outMessage.getString("registerStatus") == "1") {
			userInfo.setHasTDModel("2");
		}

		
		
		
		
		
		String updateSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.updateUserInfo";

		try {
			DatabaseExt.executeNamedSql("default", updateSqlId, userInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", "更新用户信息表失败！");
			logger.error("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("更新用户信息表数据库操作异常，异常信息:"
					+ e1.getMessage(), e1);
		}
        logger.info("更新用户信息成功！");
        outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "更新用户信息成功！");
		return outMessage;

	}

//	@Bizlet("更新声纹用户信息表--注册过程中 基立迅")
//	public DataObject updateUserInfo(DataObject inMessage,
//			DataObject outMessage, UserInfo userInfo) {
//		logger.info("进入更新用户注册信息表");
//		// 首先获取报文传递的参数
//		String userName = inMessage.getString("userName");
//		String userType = inMessage.getString("userType");
//		String certType = inMessage.getString("certType");
//		String certNo = inMessage.getString("certNo");
//		String userUniqueId = inMessage.getString("userUniqueId");
//		String channel = inMessage.getString("channel");
//		String businessType = inMessage.getString("businessType");
//		String closeAudio = inMessage.getString("closeAudio");
//
//		userInfo.setCertNo(certNo);
//		userInfo.setUserName(userName);
//		userInfo.setCertType(certType);
////		userInfo.setCloseAudio(closeAudio);
//		userInfo.setCrtChannel(channel);
//		userInfo.setTdStatus(Constants.TD_STATUS_OPEN);
//		// userInfo的固定文本模型设置根据注册的返回值进行设置
//		if (outMessage.getString("registerStatus") == "0") {
//			userInfo.setHasTDModel("1");
//			
//			/*注册成功的情况下，将本次输入音频及按时序查出的前2条音频插入用户注册信息表*/  
//			//此处做一个判断只有注册成功时，才更新数据库字段，那这里需要再查询下界面，不管是基立迅还是Validsoft应该放在这边好些
//			TransDetailModel transDetail = new TransDetailModel();
//			transDetail.setAudioType("TD");
//			transDetail.setCertNo(certNo);
//			transDetail.setCertType(certType);
//			transDetail.setUserName(userName);
//			transDetail.setChannel(channel);
//			//根据查询条件查出前三条记录
//			String queryRegisterDetailSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryRegisterTableSuccess";
//	        //查询出前三条记录  但我们查询到的记录数应为>=2条,validsoft可能只要一条就成功了
//			try{
//			Object[] objs = DatabaseExt.queryByNamedSql("default",
//					queryRegisterDetailSql, transDetail);
//			if(objs!=null&& objs.length > 0){
//		    	//
//		    	TransDetailModel transDetailNew=new TransDetailModel();
//		    	transDetailNew = (TransDetailModel) objs[0];
//		    	userInfo.setCloseAudioTd1(transDetailNew.getCloseAudio());
//		    	if(objs.length==2){
//		    		transDetailNew = (TransDetailModel) objs[1];
//		    		userInfo.setCloseAudioTd2(transDetailNew.getCloseAudio());
//		    	}
//		    	if(objs.length==3){
//		    		transDetailNew = (TransDetailModel) objs[2];
//		    		userInfo.setCloseAudioTd3(transDetailNew.getCloseAudio());
//		    	}
//		    }
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				logger.error("查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
//			}
//			
//		} else if (outMessage.getString("registerStatus") == "1") {
//			userInfo.setHasTDModel("2");
//		}
//
//		
//		
//		
//		
//		
//		String updateSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.updateUserInfo";
//
//		try {
//			DatabaseExt.executeNamedSql("default", updateSqlId, userInfo);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
//			outMessage.set("returnMsg", "更新用户信息表失败！");
//			logger.error("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
//			throw new BTPRuntimeException("更新用户信息表数据库操作异常，异常信息:"
//					+ e1.getMessage(), e1);
//		}
//        logger.info("更新用户信息成功！");
//        outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//		outMessage.set("returnMsg", "更新用户信息成功！");
//		return outMessage;
//
//	}

	@Bizlet("随意说更新声纹用户信息表--注册过程中")
	public DataObject updateUserInfoIndependent(DataObject inMessage,
			DataObject outMessage, UserInfo userInfo) {
		logger.info("进入更新用户注册信息表");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio");
        String closeAudioFormat=inMessage.getString("closeAudioFormat");
		
		
		userInfo.setCertNo(certNo);
		userInfo.setUserName(userName);
		userInfo.setCertType(certType);
//		userInfo.setCloseAudio(closeAudio);
		userInfo.setCloseAudioFs(closeAudio);
		
		userInfo.setCrtChannel(channel);
        //更新即已经注册成功
		userInfo.setHasFSModel("1");
        //获取服务状态
		userInfo.setFsStatus(Constants.FS_STATUS_OPEN);

		
        //由于注册信息表不需要closeAudio字段，故此处不需要做处理存入数据库，closeAudio音频路径可到流水表中查看
//		if(closeAudioFormat.equals("base64")){
//			 try {
//			 String directory =AudioFileUtils.crtAudioPathByUUID(VPRSUtils.getUUID(),channel);
//			 logger.info("个人近声存储路径："+directory);
//			 AudioFileUtils.convertBase64DataToAudio(closeAudio, directory);
//			 //数据库中保存的转码之后的路径
//			 userInfo.setCloseAudio(directory);
//			 } catch (Exception e) {
//			 logger.info("存储音频文件到文件服务器出错");
//			 outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//			 outMessage.set("returnMsg", "存储音频文件到文件服务器出错");
//			 return outMessage;
//			 }
//		}
		
	    

		String updateSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.updateUserInfo";

		try {
			DatabaseExt.executeNamedSql("default", updateSqlId, userInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", "更新用户信息表失败！");
			logger.error("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("更新用户信息表数据库操作异常，异常信息:"
					+ e1.getMessage(), e1);
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "更新用户信息成功！");
		return outMessage;

	}

	@Bizlet("比对现场声纹与数据库库中所存声纹")
	public static DataObject compareCloseAudioWithLocalAudio(
			DataObject inMessage, UserInfo userInfo, DataObject outMessage) {
		logger.error("比对现场照与数据库库中所存照片");
		if (StringUtils.isBlank(inMessage.getString("certNo"))
				|| StringUtils.isBlank(inMessage.getString("closeAudio"))
				|| StringUtils.isBlank(inMessage.getString("channel"))
				|| StringUtils.isBlank(inMessage.getString("businessType"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return null;
		}

		String audioPath = userInfo.getCloseAudio();
		if (StringUtils.isBlank(audioPath)) {
			userInfo = null;
			outMessage.set("returnCode", ReturnCode.FILE_NOT_FOUND_EXCEPTION);
			outMessage.set("returnMsg", "用户的近声为空");
			return outMessage;
		}

		// 获取所需比较的两段音频
		String audioA = inMessage.getString("closeAudio");
		String audioB = AudioFileUtils.getAudioFile(audioPath);
		if (StringUtils.isBlank(audioB)) {
			userInfo = null;
			outMessage.set("returnCode", ReturnCode.FILE_NOT_FOUND_EXCEPTION);
			outMessage.set("returnMsg", "用户的近声为空");
			return outMessage;
		}

		// 查询阈值
		BigDecimal threshold = VPRSDaoUtils.queryThreshold(inMessage
				.getString("channel"), inMessage.getString("businessType"),
				Constants.BIOMETRIC_TYPE_AUDIO, Constants.COMPARE_AUDIO);
		if (threshold == null || threshold.compareTo(new BigDecimal(0)) == 0) {
			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
			outMessage.set("returnMsg", "获取阈值异常");
			return outMessage;
		}

		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory
				.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.verifySpeaker(inMessage, outMessage, threshold);
			// 将验证服务中返回值中的得分取出并赋给userInfo
			String score = outMessage.get("info/similarity").toString();
			BigDecimal similarity = (BigDecimal) VPRSUtils
					.convertObject2BigDecimal(score);
			userInfo.setScore(similarity);
		}
		return outMessage;
	}

	@Bizlet("更新声纹模型")
	public static DataObject updateSpeaker(DataObject inMessage,
			UserInfo userInfo, DataObject outMessage) {
		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory
				.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.updateSpeaker(inMessage, outMessage);
			// 将验证服务中返回值中的得分取出并赋给userInfo
			String score = outMessage.get("info/similarity").toString();
			BigDecimal similarity = (BigDecimal) VPRSUtils
					.convertObject2BigDecimal(score);
			userInfo.setScore(similarity);
		}
		return outMessage;
	}


	@Bizlet("验证后更新用户信息表")
	public DataObject updateModelUserInfo(DataObject inMessage,DataObject outMessage, UserInfo userInfo) {
		logger.info("进入更新用户注册信息表");
		// 首先获取报文传递的参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio");
		String audioType = inMessage.getString("audioType");
		String closeAudioFormat=inMessage.getString("closeAudioFormat");

		userInfo.setCertNo(certNo);
		userInfo.setUserName(userName);
		userInfo.setCertType(certType);
		
		//验证更新下时间就好了，没必要更新音频
//		userInfo.setCloseAudio(closeAudio);

		userInfo.setCrtChannel(channel);
		if (audioType.equals("TD")) {
			userInfo.setTdStatus(Constants.TD_STATUS_OPEN);
		}
		if (audioType.equals("FS")) {
			userInfo.setFsStatus(Constants.FS_STATUS_OPEN);
		}
		
		String updateSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.updateUserInfo";

		try {
			DatabaseExt.executeNamedSql("default", updateSqlId, userInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", "更新用户信息表失败！");
			logger.error("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("更新用户信息表数据库操作异常，异常信息:"
					+ e1.getMessage(), e1);
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "更新用户信息成功！");
		return outMessage;

	}

	

	
	
	/**
	 * 声纹验证J_VPRS_001_0003 相关运算逻辑独有
	 */

	@Bizlet("声纹验证J_VPRS_001_0003")
	public static DataObject verifyAudio(DataObject inMessage,UserInfo userInfo, DataObject outMessage) {

		// 首先根据业务场景和渠道类型选择所要比较的声纹
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String audioType = inMessage.getString("audioType");
		

		// 查询阈值,获取到对应的阈值
		BigDecimal threshold = VPRSDaoUtils.queryThreshold(channel,businessType,Constants.BIOMETRIC_TYPE_AUDIO, audioType);
		if (threshold == null || threshold.compareTo(new BigDecimal(0)) == 0) {
			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
			outMessage.set("returnMsg", "获取阈值异常");
			logger.info("获取阈值异常");
			 return outMessage;
		}

		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			if (audioType.equals("TD")) {
				outMessage = f.verifySpeaker(inMessage, outMessage, threshold);
				
			}
			if (audioType.equals("FS")) {
				outMessage = f.verifySpeakerIndependent(inMessage, outMessage,threshold);
			}
		}
		
		return outMessage;
		
	}
	
	@Bizlet("随意说声纹验证")
	public static DataObject verifyFsAudioModel(DataObject inMessage,UserInfo userInfo, DataObject outMessage) {

		// 首先根据业务场景和渠道类型选择所要比较的声纹
//		String channel = inMessage.getString("channel");
//		String businessType = inMessage.getString("businessType");
//		String audioType = inMessage.getString("audioType");
		

//		// 查询阈值,获取到对应的阈值
//		BigDecimal threshold = VPRSDaoUtils.queryThreshold(channel,businessType,Constants.BIOMETRIC_TYPE_AUDIO, audioType);
//		if (threshold == null || threshold.compareTo(new BigDecimal(0)) == 0) {
//			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//			outMessage.set("returnMsg", "获取阈值异常");
//			logger.info("获取阈值异常");
//			 return outMessage;
//		}

		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.verifySpeakerIndependent(inMessage, outMessage,null);
		}
		
		return outMessage;
		
	}
	
	
	@Bizlet("大平台随意说声纹验证")
	public static DataObject verifyFsAudioModelCQVoice(DataObject inMessage, DataObject outMessage) {

		
		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.verifySpeakerIndependentCQVoice(inMessage, outMessage);
		}
		
		return outMessage;
		
	}
	
	@Bizlet("固定文本声纹验证")
	public static DataObject verifyTdAudioModel(DataObject inMessage,UserInfo userInfo, DataObject outMessage) {

		// 首先根据业务场景和渠道类型选择所要比较的声纹
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String audioType = inMessage.getString("audioType");
		

		// 查询阈值,获取到对应的阈值
		BigDecimal threshold = VPRSDaoUtils.queryThreshold(channel,businessType,Constants.BIOMETRIC_TYPE_AUDIO, audioType);
		if (threshold == null || threshold.compareTo(new BigDecimal(0)) == 0) {
			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
			outMessage.set("returnMsg", "获取阈值异常");
			logger.info("获取阈值异常");
			 return outMessage;
		}

		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.verifySpeaker(inMessage, outMessage,threshold);
		}
		
		return outMessage;
		
	}
	

	/*
	 * J_VPRS_001_0003 固定文本情况下需更新声纹模型
	 */
	@Bizlet("声纹验证J_VPRS_001_0003  更新声纹模型")
	public static DataObject updateAudioModel(DataObject inMessage,UserInfo userInfo, DataObject outMessage) {
		
		//此处做个校验，因为随意说需要更新模型，但固定文本无需更新模型
		String audioType=inMessage.getString("audioType");
		if(audioType.equals("FS")){
		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.updateSpeakerIndependent(inMessage, outMessage);
		}
		}
        return outMessage;
	}
	
	
	/*
	 * J_VPRS_001_0003 随意说更新声纹模型
	 */
	@Bizlet("随意说声纹更新")
	public static DataObject updateFsAudioModel(DataObject inMessage, DataObject outMessage) {
		logger.info("进入随意说声纹更新");

		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.updateSpeakerIndependent(inMessage, outMessage);
		}
		
        return outMessage;
	}
	
	/*
	 * J_VPRS_001_0006 随意说删除声纹模型
	 */
	@Bizlet("随意说声纹删除")
	public static DataObject deleteFsAudioModel(DataObject inMessage, DataObject outMessage) {
		logger.info("进入随意说声纹删除");

		audioService f = AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.deleteSpeakerIndependent(inMessage, outMessage);
		}
		
        return outMessage;
	}
	
	
	/*
	 * J_VPRS_001_0006 随意说删除声纹用户表
	 */
	@Bizlet("随意说声纹用户删除")
	public static DataObject deleteFsAudioUserInfo(DataObject inMessage,UserInfo userInfo, DataObject outMessage) {
		logger.info("进入随意说声纹用户删除");
		userInfo.setHasFSModel("2");
		String deleteSqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.updateUserInfo";

		try {
			DatabaseExt.executeNamedSql("default", deleteSqlId, userInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", "删除用户信息表失败！");
			logger.error("删除用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("删除用户信息表数据库操作异常，异常信息:"
					+ e1.getMessage(), e1);
		}

		
        return outMessage;
	}
	
	

	@Bizlet("声纹验证J_VPRS_001_0001  随意说验证声纹模型")
	public static DataObject verifyAudioIndependent(DataObject inMessage,
			UserInfo userInfo, DataObject outMessage) {
		// 首先根据业务场景和渠道类型选择所要比较的声纹
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");

		// 获取渠道阈值表的阈值并对输入参数做一定的校验
		if (StringUtils.isBlank(inMessage.getString("certNo"))
				|| StringUtils.isBlank(inMessage.getString("closeAudio"))
				|| StringUtils.isBlank(inMessage.getString("channel"))
				|| StringUtils.isBlank(inMessage.getString("businessType"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return null;
		}

		// 查询阈值,获取到对应的阈值
		BigDecimal threshold = VPRSDaoUtils.queryThreshold(inMessage
				.getString("channel"), inMessage.getString("businessType"),
				Constants.BIOMETRIC_TYPE_AUDIO, "FS");
		if (threshold == null || threshold.compareTo(new BigDecimal(0)) == 0) {
			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
			outMessage.set("returnMsg", "获取阈值异常");
			//			return outMessage;
		}

		//阈值对比
		//调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory
				.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.verifySpeakerIndependent(inMessage, outMessage,
					threshold);
		}

		return outMessage;
	}

	/**
	 * 远鉴的两段语音比较
	 * @param inMessage
	 * @param userInfo
	 * @param outMessage
	 * @return
	 */
	@Bizlet("比较最近音频和数据库中的音频，看是否为同一个人的音频")
	public static DataObject compareCloseAudioWithLocalAudioTest(DataObject inMessage,UserInfo userInfo,DataObject outMessage){
		logger.error("比对现场照与数据库库中所存照片");
		if (StringUtils.isBlank(inMessage.getString("certNo"))
				|| StringUtils.isBlank(inMessage.getString("closeAudio"))
				|| StringUtils.isBlank(inMessage.getString("channel"))
				|| StringUtils.isBlank(inMessage.getString("businessType"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return null;
		}

		String audioPath = userInfo.getCloseAudio();
		if (StringUtils.isBlank(audioPath)) {
			userInfo = null;
			outMessage.set("returnCode", ReturnCode.FILE_NOT_FOUND_EXCEPTION);
			outMessage.set("returnMsg", "用户的近声为空");
			return outMessage;
		}

		// 获取所需比较的两段音频
		String audioA = inMessage.getString("closeAudio");
		String audioB = AudioFileUtils.getAudioFile(audioPath);
		if (StringUtils.isBlank(audioB)) {
			userInfo = null;
			outMessage.set("returnCode", ReturnCode.FILE_NOT_FOUND_EXCEPTION);
			outMessage.set("returnMsg", "用户数据库已存的近声为空");
			return outMessage;
		}

		// 查询阈值
		BigDecimal threshold = VPRSDaoUtils.queryThreshold(inMessage
				.getString("channel"), inMessage.getString("businessType"),
				Constants.BIOMETRIC_TYPE_AUDIO, Constants.COMPARE_AUDIO);
		if (threshold == null || threshold.compareTo(new BigDecimal(0)) == 0) {
			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
			outMessage.set("returnMsg", "获取阈值异常");
			return outMessage;
		}

		// 调用底层算法比较声纹模型(其实就是比较分数吧)
		audioService f = AudioFactory
				.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if (null != f) {
			outMessage = f.compareSpeaker(inMessage, outMessage);
			// 将验证服务中返回值中的得分取出并赋给userInfo
			String score = outMessage.get("info/similarity").toString();
			BigDecimal similarity = (BigDecimal) VPRSUtils
					.convertObject2BigDecimal(score);
			userInfo.setScore(similarity);
		}
		return outMessage;
	}
	
	@Bizlet("用户注册查询 从声纹模型查询")
	public static DataObject selectUserEnrollStatus(DataObject inMessage, DataObject outMessage){
		audioService f=AudioFactory.getInstance(VprsConstant.AUDIO_SERVICE_PROVIDER);
		if(null!=f){
			outMessage=f.isExistSpeaker(inMessage, outMessage);
		}
		return outMessage;
	}
	
	@Bizlet("用户注册查询 从用户注册信息表查询")
	public static DataObject queryFromDataBase(DataObject inMessage, DataObject outMessage,UserInfo userInfo){
		logger.error("进入根据证件类型和证件号查询用户信息");		
		if (StringUtils.isBlank(inMessage.getString("certType"))
				|| StringUtils.isBlank(inMessage.getString("certNo"))
				|| StringUtils.isBlank(inMessage.getString("audioType"))
				|| StringUtils.isBlank(inMessage.getString("userName"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}

		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userName=inMessage.getString("userName");
		String audioType=inMessage.getString("audioType");
		
		userInfo.setCertType(certType);
		userInfo.setCertNo(certNo);
		userInfo.setUserName(userName);

		String sqlId = "com.pccc.vprs.servicecustom.sql.AudioDetect.queryUserInfo";
		try {

			Object[] objs = DatabaseExt.queryByNamedSql("default", sqlId,
					userInfo);
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					userInfo = (UserInfo) obj;
				}
				if("FS".equals(audioType)&&("1".equals(userInfo.getHasFSModel()))){
					outMessage.set("returnCode",
							ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "用户已开通声纹认证");
					return outMessage;
				}
				if("TD".equals(audioType)&&("1".equals(userInfo.getHasTDModel()))){
					outMessage.set("returnCode",
							ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "用户已开通声纹认证");
					return outMessage;
				}
				
			} 
		} catch (Exception e) {
			logger.error("根据证件类型和证件号码和用户姓名查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
			throw new BTPRuntimeException("根据证件类型和证件号码查询用户信息数据库操作异常，异常信息:"
					+ e.getMessage(), e);
		}
		
		
		outMessage.set("returnCode",ReturnCode.TOUDA_FAIL);
		outMessage.set("returnMsg", "用户未开通声纹认证");
		
		return outMessage;
	}
	
	@Bizlet("随意说除未注册的情况下，对传入的base64字符串转码为路径，以便存入注册明细流水表中,否则不需要处理")
	public static DataObject noEnrollFs(DataObject inMessage,DataObject outMessage) {	
		
		logger.info("开始将语音文件存入ToudaDFS");
		
		String closeAudio = inMessage.getString("closeAudio");
		String channel=inMessage.getString("channel");
		//获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat").toLowerCase(); 
		
//		String directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);

		//当传递方式为base64时(实时,需将音频存储下来)
		if (closeAudioFormat.equals("base64")) {
			try {
				logger.info("开始将语音文件存入ToudaDFS");
                //将音频存储到toudaDfs
				String fileName = FilenameUtils.getName("VPRS_Audio_Fs_Register"
				+ System.currentTimeMillis());
				Map<String, String> metaInfo = new HashMap<String, String>();				
				metaInfo.put("operUser", operUser);// 上传用户
				metaInfo.put("busiCode", "J_VPRS_001_0001");// 必填
				String fileAbsolutePath = "";
				fileAbsolutePath=AudioFileUtils.convertBase64DataToToudaDfs(closeAudio, fileName, metaInfo);
				// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
				inMessage.set("closeAudio", fileAbsolutePath);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("存储音频文件到文件服务器出错"+ e.getMessage());
			}

		}
		// 当传递方式为url时（从ToudaDfs下载，跑批，将音频地址存储到数据库中）
		else if (closeAudioFormat.equals("url")) {
             //此时音频地址无需处理
		}
		// 当传递方式为path时（暂定）
		else if (closeAudioFormat.equals("path")) {
			String directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("从path拷贝文件存储到指定路径出错" + e.getMessage());
			}
			// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);

		}
	
		return outMessage;
	}
	
	
	@Bizlet("随意说除更新的情况下，对传入的字符进行处理")
	public static DataObject noUpdateFs(DataObject inMessage,DataObject outMessage) {	
		
		logger.info("开始将语音文件存入ToudaDFS");
		
		String closeAudio = inMessage.getString("closeAudio");
		String channel=inMessage.getString("channel");
		//获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat").toLowerCase(); 
		
//		String directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);

		//当传递方式为base64时(实时,需将音频存储下来)
		if (closeAudioFormat.equals("base64")) {
			try {
				logger.info("开始将语音文件存入ToudaDFS");
                //将音频存储到toudaDfs
				String fileName = FilenameUtils.getName("VPRS_Audio_"
				+ System.currentTimeMillis());
				Map<String, String> metaInfo = new HashMap<String, String>();				
				metaInfo.put("operUser", operUser);// 上传用户
				metaInfo.put("busiCode", "J_VPRS_001_0001");// 必填
				String fileAbsolutePath = "";
				fileAbsolutePath=AudioFileUtils.convertBase64DataToToudaDfs(closeAudio, fileName, metaInfo);
				// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
				inMessage.set("closeAudio", fileAbsolutePath);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("存储音频文件到文件服务器出错"+ e.getMessage());
			}

		}
		// 当传递方式为url时（从ToudaDfs下载，跑批，将音频地址存储到数据库中）
		else if (closeAudioFormat.equals("url")) {
             //此时音频地址无需处理
		}
		// 当传递方式为path时（暂定）
		else if (closeAudioFormat.equals("path")) {
			String directory = AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("从path拷贝文件存储到指定路径出错" + e.getMessage());
			}
			// 将path拷贝后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);

		}
	
		return outMessage;
	}
	
	
	
	
	
	@Bizlet("除未验证的情况下，对传入的base64字符串转码为路径，以便存入验证明细流水表中,否则不需要处理")
	public static DataObject noVerify(DataObject inMessage,DataObject outMessage) {
//		String closeAudioFormat = inMessage.getString("closeAudioFormat");
//		String closeAudio = inMessage.getString("closeAudio");
//		String channel = inMessage.getString("channel");
//		String audioType=inMessage.getString("audioType");
//		
//		if (closeAudioFormat.equals("base64")) {
//			try {
//				String separator = File.separator;
//				String audioWebPathIn = VPRSUtils.getTime("yyyyMMdd") + separator
//						+ channel + separator + VPRSUtils.getUUID() + ".wav";
//				String audioLocalPath="";
//				if(audioType.equals("TD")){
//					audioLocalPath=VprsConstant.JILIXUN_SHARE_PATH_VERIFY_TD
//					+ separator + audioWebPathIn;
//				}
//				if(audioType.equals("FS")){
//					audioLocalPath=VprsConstant.JILIXUN_SHARE_PATH_VERIFY_FS
//					+ separator + audioWebPathIn;
//				}
//				logger.info("个人近声存储路径：" + audioLocalPath);
//				AudioFileUtils.convertBase64DataToAudio(closeAudio,
//						audioLocalPath);
//				// 报文中保存的转码之后的路径
//				inMessage.set("closeAudio", audioLocalPath);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.info("存储音频文件到文件服务器出错");
//			}
//		}
//		
//		 //当传递方式为url时（前提是该地址文件能够访问）
//		if(closeAudioFormat.equals("url")){
//			String directory="";
//			if(audioType.equals("TD")){
//				directory=AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
//			}else{
//				directory=AudioFileUtils.crtFsVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
//			}
//			
//			try {
//				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
//			} catch (IOException e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
//				return outMessage;	
//			}
//			//将url下载后音频的存放位置给报文，以便后面存入数据库
//			inMessage.set("closeAudio", directory);
//		}
//		
//		
//        //当传递方式为path时
//		if(closeAudioFormat.equals("path")){
//			String directory="";
//			if(audioType.equals("TD")){
//				directory=AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
//			}else{
//				directory=AudioFileUtils.crtFsVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
//			}
//			try {
//				AudioFileUtils.copyToPath(closeAudio, directory);
//			} catch (Exception e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
//				return outMessage;
//			
//			}
//			
//            //将url下载后音频的存放位置给报文，以便后面存入数据库
//			inMessage.set("closeAudio", directory);
//			
//		}
//		
//		
//		
//		return outMessage;
		
		String isStore = inMessage.getString("isStore").toUpperCase();//是否存储
		
		String closeAudio = inMessage.getString("closeAudio");
		String channel=inMessage.getString("channel");
		//获取最近音频的格式 path/url/base64
		String closeAudioFormat = inMessage.getString("closeAudioFormat").toLowerCase(); 
//		String audioType=inMessage.getString("audioType");
		
		String directory="";
//		if("TD".equals(audioType)){
//			directory=AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
//		}else if("FS".equals(audioType)){
//			directory=AudioFileUtils.crtFsVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
//		}
		
		//音频对应的base64编码
		String base64Audio = "";
		
		// 当传递方式为url时（从ToudaDfs下载）
		if (closeAudioFormat.equals("url")) {

			try {
			byte[] data=FastDFSClientAlone.download(closeAudio);
			
			//对字节数组Base64编码  
			BASE64Encoder encoder = new BASE64Encoder();
			
			base64Audio=encoder.encode(data);
			
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
		else if (closeAudioFormat.equals("base64") && isStore.equals("Y")) {//暂定Y为存储,N为不存储
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
		

		inMessage.set("closeAudio", directory);
		return outMessage;
		
		
		
	}
	
}
