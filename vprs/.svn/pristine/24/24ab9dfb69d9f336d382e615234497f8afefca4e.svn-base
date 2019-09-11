package com.pccc.vprs.servicecustom.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.pccc.touda.common.event.EventBusManager;
import com.pccc.touda.common.util.NetUtils;
import com.pccc.vprs.servicecustom.common.Constants;
import com.pccc.vprs.servicecustom.common.ReturnCode;
import com.pccc.vprs.servicecustom.transflow.InsertTransDetailEvent;
import com.pccc.vprs.servicecustom.transflow.TransDetailModel;
import com.pccc.vprs.servicecustom.transflow.TransFlowConstant;
import com.pccc.vprs.servicecustom.transflow.TransRegisterModel;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.btp.api.engine.context.IServiceResponse;
import com.primeton.btp.api.engine.trans.ITransServiceContext;
import com.primeton.btp.api.engine.trans.ITransServiceDefinition;
import com.primeton.btp.api.engine.trans.handler.ITransServiceHandler;
import com.primeton.btp.spi.transportservice.def.TransportServiceDefinition;
import com.primeton.esb.message.impl.DefaultMessagePayload;
import commonj.sdo.DataObject;

/**
 * @Title:TransServiceHandler.java
 * @Description:交易服务拦截器记录交易流水
 * 
 */
public class TransServiceHandler implements ITransServiceHandler {

	private static ILogger logger = LoggerFactory
			.getLogger(TransServiceHandler.class);

	public void afterReply(ITransServiceDefinition definition,
			ITransServiceContext context) throws TerminationException {
		try {
			TransDetailModel transDetail = new TransDetailModel();
			DefaultMessagePayload payload = (DefaultMessagePayload) context
					.getServiceRequest().getRequestMessage().getPayload();
			// 获取请求报文
			Object requestObj = payload.getMessagePayload();
			DataObject requestSDO = null;
			if (requestObj != null && requestObj instanceof DataObject) {
				requestSDO = (DataObject) requestObj;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("==== TransServiceHandler.afterReply ====requestSDO error");
				}
				return;
			}
			String transId = (String) payload.getSystemHeaders().get(
					TransFlowConstant.HEAD_TRANS_ID);
			transDetail.setJnlSeqNo(transId);// 流水号
			String globalSeqNo = (String) requestSDO
					.get(TransFlowConstant.HEADER + "/"
							+ TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
			transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
			String transCode = "";
			// 获取交易码
			TransportServiceDefinition transportDefine = (TransportServiceDefinition) context
					.getServiceRequest().getRequestMessage().getHeaders().get(
							TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
			if (transportDefine != null) {
				transCode = transportDefine.getId();
			}
			transDetail.setTransCode(transCode);
			if (logger.isDebugEnabled()) {
				logger.debug("==== TransServiceHandler.afterReply ====transDetailInfo="
								+ transDetail.toString());
			}
			// 检验交易有无注册
			TransRegisterModel transRegister = TransFlowConstant
					.getTransRegister(transCode);
			if (transRegister == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("==== TransServiceHandler.afterReply ====transCode="
									+ transCode + " NO TransRegister");
				}
				return;
			}
			transDetail.setTransDesc(transRegister.getTransDesc());
			// 获取返回报文
			DataObject responseSDO = null;
			IServiceResponse iresponse = context.getServiceResponse();
			if (iresponse != null) {
				responseSDO = (DataObject) iresponse.getPayload();
			}
			addAttributesForTransDetail(requestSDO, responseSDO, transDetail);
			// 异步消息通知，由于不需要存报文，取消传输requestSDO对象
			EventBusManager.INSTANCE.postAsynEvent(new InsertTransDetailEvent(
					transDetail));

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			logger.error("==== TransServiceHandler.afterReply ===="
					+ sw.toString(), e);
			try {
				if (sw != null) {
					sw.close();
				}
			} catch (IOException e1) {
				logger.error("==== TransServiceHandler.afterReply ====", e);
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	public void afterError(ITransServiceDefinition definition,
			ITransServiceContext context, Throwable t)
			throws TerminationException {
		try {
			// 异常处理
			TransDetailModel transDetail = new TransDetailModel();
			DefaultMessagePayload payload = (DefaultMessagePayload) context
					.getServiceRequest().getRequestMessage().getPayload();
			// 获取请求报文
			Object requestObj = payload.getMessagePayload();
			DataObject requestSDO = null;
			if (requestObj != null && requestObj instanceof DataObject) {
				requestSDO = (DataObject) requestObj;
			} else {
				if (logger.isDebugEnabled()) {
					logger
							.debug("==== TransServiceHandler.afterError ====requestSDO error");
				}
				return;
			}
			String transId = (String) payload.getSystemHeaders().get(
					TransFlowConstant.HEAD_TRANS_ID);
			transDetail.setJnlSeqNo(transId);// 流水号
			String globalSeqNo = (String) requestSDO
					.get(TransFlowConstant.HEADER + "/"
							+ TransFlowConstant.HEAD_GLOBAL_SEQ_NO);
			transDetail.setChannelSeqNo(globalSeqNo); // 渠道流水号、全局流水号
			String transCode = "";
			// 获取交易码
			TransportServiceDefinition transportDefine = (TransportServiceDefinition) context
					.getServiceRequest().getRequestMessage().getHeaders().get(
							TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
			if (transportDefine != null) {
				transCode = transportDefine.getId();
			}
			transDetail.setTransCode(transCode);
			if (logger.isDebugEnabled()) {
				logger
						.debug("==== TransServiceHandler.afterError ====transDetailInfo="
								+ transDetail.toString());
			}
			// 检验交易有无注册
			TransRegisterModel transRegister = TransFlowConstant
					.getTransRegister(transCode);
			if (transRegister == null) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("==== TransServiceHandler.afterError ====transCode="
									+ transCode + " NO TransRegister");
				}
				return;
			}
			transDetail.setTransDesc(transRegister.getTransDesc());
			// 获取返回报文
			DataObject responseSDO = null;
			IServiceResponse iresponse = context.getServiceResponse();
			if (iresponse != null) {
				responseSDO = (DataObject) iresponse.getPayload();
			}
			addAttributesForTransDetail(requestSDO, responseSDO, transDetail);
			// 异步消息通知
			// EventBusManager.INSTANCE.postAsynEvent(new
			// InsertTransDetailEvent(requestSDO,responseSDO,transDetail));
			EventBusManager.INSTANCE.postAsynEvent(new InsertTransDetailEvent(
					transDetail));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			logger.error("==== TransServiceHandler.afterError ===="
					+ sw.toString(), e);
			try {
				if (sw != null) {
					sw.close();
				}
			} catch (IOException e1) {
				logger.error("==== TransServiceHandler.afterError ====", e);
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	public void beforeRequest(ITransServiceDefinition definition,
			ITransServiceContext context) throws TerminationException {
		try {
			String transStreamNo = UUID.randomUUID().toString().replaceAll("-",
					"");
			DefaultMessagePayload payload = (DefaultMessagePayload) context
					.getServiceRequest().getRequestMessage().getPayload();
			String transCode = "";
			TransportServiceDefinition transportDefine = (TransportServiceDefinition) context
					.getServiceRequest().getRequestMessage().getHeaders().get(
							TransFlowConstant.BTP_TRANSPORT_SERVICE_DEFINITION);
			if (transportDefine != null) {
				transCode = transportDefine.getId();
			}
			payload.getSystemHeaders().put(TransFlowConstant.HEAD_TRANS_ID,
					transStreamNo);
			payload.getSystemHeaders().put(TransFlowConstant.HEAD_TRANS_CODE,
					transCode);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			logger.error("==== TransServiceHandler.beforeRequest ===="
					+ sw.toString(), e);
			try {
				if (sw != null) {
					sw.close();
				}
			} catch (IOException e1) {
				logger.error("==== TransServiceHandler.beforeRequest ====", e);
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	public String getId() {
		return TransServiceHandler.class.getName() + ":" + hashCode();
	}

	public int getPriority() {
		return 1;
	}

	// 为TransDetail对象赋值
	private void addAttributesForTransDetail(DataObject requestSDO,
			DataObject responseSDO, TransDetailModel transDetail) {
		String channel = "";
		channel = (String) requestSDO.get("channel");
		if (StringUtils.isNotBlank(channel)) {
			transDetail.setChannel(channel);
		}

		/** 以下为试验，测试随意说注册流水明细表能否成功 */
		transDetail.setStatus(Constants.STATUS_PRE);
		if (StringUtils.isNotBlank((String) requestSDO.get("userUniqueId"))) {
			transDetail
					.setUserUniqueId((String) requestSDO.get("userUniqueId"));
		}
		// 整个流程只存一次，故将交易流中存储的文件路径传递给inMessage，即可
		if (StringUtils.isNotBlank((String) requestSDO.get("closeAudio"))) {
			try {
				String closeAudio = (String) requestSDO.get("closeAudio");
				if (TransFlowConstant.J_VPRS_001_0001.equals(transDetail
						.getTransCode())) {
					transDetail.setAudioType("FS");
				}

				requestSDO.set("closeAudio", closeAudio);
				transDetail.setCloseAudio(StringUtils.left(closeAudio, 255));
			} catch (Exception e) {
				logger
						.error(
								"==== InsertTransDetailEventProcessor.addTransDetail操作数据字典closeAudio时异常  ====",
								e);
			}
		}

		if (StringUtils.isNotBlank((String) requestSDO.get("businessType"))) {
			transDetail
					.setBusinessType((String) requestSDO.get("businessType"));
		}
		if (StringUtils.isNotBlank((String) requestSDO.get("userUniqueId"))) {
			transDetail
					.setUserUniqueId((String) requestSDO.get("userUniqueId"));
		}
		if (StringUtils.isNotBlank((String) requestSDO.get("certType"))) {
			transDetail.setCertType((String) requestSDO.get("certType"));
		}
		if (StringUtils.isNotBlank((String) requestSDO.get("userName"))) {
			transDetail.setUserName((String) requestSDO.get("userName"));
		}
		if (StringUtils.isNotBlank((String) requestSDO.get("certNo"))) {
			transDetail.setCertNo((String) requestSDO.get("certNo"));
		}

		if (StringUtils.isNotBlank((String) requestSDO.get("riskLevel"))) {
			transDetail.setRiskLevel((String) requestSDO.get("riskLevel"));
		}

		if (StringUtils.isNotBlank((String) requestSDO.get("userType"))) {
			transDetail.setUserType((String) requestSDO.get("userType"));
		}

		if (StringUtils.isNotBlank((String) requestSDO.get("phoneNo"))) {
			transDetail.setPhoneNo((String) requestSDO.get("phoneNo"));
		}
		
		
		if (StringUtils.isNotBlank((String) requestSDO.get("closeAudioName"))) {
			transDetail.setReserveCoulumn1((String)requestSDO.get("closeAudioName"));
		}
		
		if (StringUtils.isNotBlank((String) requestSDO.get("batchTime"))) {
			transDetail.setReserveCoulumn2((String)requestSDO.get("batchTime"));
		}
		
		if (StringUtils.isNotBlank((String) requestSDO.get("city"))) {
			transDetail.setReserveCoulumn3((String) requestSDO.get("city"));
		}
		
		
		if (StringUtils.isNotBlank((String) requestSDO.get("nosieTest"))) {
			transDetail.setReserveCoulumn4((String) requestSDO.get("nosieTest"));
		}
		
		if (StringUtils.isNotBlank((String) requestSDO.get("recodeWay"))) {
			transDetail.setReserveCoulumn5((String) requestSDO.get("recodeWay"));
		}

		if (StringUtils.isNotBlank((String) requestSDO.get("closeAudioFormat"))) {
			transDetail.setCloseAudioFormat((String) requestSDO
					.get("closeAudioFormat"));
			// 如果closeAudioFormat为base64,则将closeAudio置空
			if (TransFlowConstant.J_VPRS_003_0001.equals(transDetail
					.getTransCode())) {
				if ("base64".equals(transDetail.getCloseAudioFormat())) {
					transDetail.setCloseAudio("");
				}
			}
			
			
		}

		if (TransFlowConstant.J_VPRS_001_0003
				.equals(transDetail.getTransCode())) {
			if (StringUtils.isNotBlank((String) requestSDO.get("audioType"))) {
				transDetail.setAudioType((String) requestSDO.get("audioType"));
			}
		}

		String ip = NetUtils.getLocalHost();
		transDetail.setServerNode(ip);// 服务器节点
		if (logger.isDebugEnabled()) {
			logger
					.debug("==== InsertTransDetailEventProcessor.addTransDetail ====transDetailInfo="
							+ transDetail.toString());
		}

		// 报文无需存储
		// String
		// transRequestMsg=XMLToolKit.parseStringXml(requestSDO.toString());
		// transDetail.setTransRequestMsg(transRequestMsg);
		// if(transRequestMsg.length()>32700){
		// transDetail.setTransRequestMsg(StringUtils.left(transRequestMsg,
		// 32670));
		// }
		if (responseSDO != null) {
			if (StringUtils.isNotBlank(responseSDO.getString("returnCode"))) {
				transDetail.setReturnCode(responseSDO.getString("returnCode"));
				if (ReturnCode.TOUDA_SUCCESS.equals(responseSDO
						.getString("returnCode"))) {
					transDetail.setStatus(Constants.STATUS_SUCCESS);
				} else {
					transDetail.setStatus(Constants.STATUS_FAIL);
				}
			}
			if (StringUtils.isNotBlank(responseSDO.getString("returnMsg"))) {
				transDetail.setReturnMsg(responseSDO.getString("returnMsg"));

			}
			if (StringUtils.isNotBlank((String) responseSDO
					.get("info/similarity"))) {
				transDetail.setScore((String) responseSDO
						.get("info/similarity"));
			}

			// 报文无需存储
			// String
			// transResponseMsg=XMLToolKit.parseStringXml(responseSDO.toString());
			// transDetail.setTransResponseMsg(transResponseMsg);
			// if(transResponseMsg.length()>32700){
			// StringUtils.left(transResponseMsg, 32699);
			// transDetail.setTransResponseMsg(transResponseMsg);
			// }
		}
	}
}
