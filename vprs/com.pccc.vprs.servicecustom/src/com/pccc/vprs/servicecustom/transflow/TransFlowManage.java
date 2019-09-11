package com.pccc.vprs.servicecustom.transflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.eos.foundation.database.DatabaseExt;
import com.pccc.touda.common.util.ConfigUtils;
import com.pccc.vprs.servicecustom.kafka.KafukaSender;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

/**
 * 交易流水处理
 * @author A144803
 *
 */
public class TransFlowManage {

	private static ILogger logger = LoggerFactory.getLogger(TransFlowManage.class);

	/**	查询所有交易登记表信息 */
	//com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryTransRegisterData
	public static final String QRY_TRANS_REGISTER_SQL = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryTransRegisterData";
	/**	添加交易流水明细数据 */
	public static final String INSERT_TRANS_DETAIL_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertTransDetail";
	
	public static final String INSERT_INTO_REGISTER_TABLE_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertIntoRegisterTable";
	public static final String INSERT_INTO_LOGIN_TABLE_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertIntoLoginTable";
	public static final String INSERT_INTO_FACE_TABLE_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertIntoFaceTable";
	public static final String INSERT_INTO_CERT_TABLE_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertIntoCertTable";
	public static final String INSERT_INTO_VIDEO_TABLE_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertIntoVideoTable";
	public static final String INSERT_INTO_MULTI_TABLE_SQL = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.insertIntoMultiTable";
	
	public static final String INSERT_INTO_REGISTER_FS_TABLE_SQL = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoRegisterTableNew";
	
	public static final String INSERT_INTO_VERIFY_TABLE_SQL="com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoVerifyTable";
	
	public static final String  INSERT_INTO_AUDIO_TRANS_DETAIL = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.insertIntoAudioTransDetail";
	/**
	 * @Description:查询交易登记表
	 * @return
	 */
	public static Map<String, TransRegisterModel> queryTransRegisterInfo() {
		Object[] transRegisters = DatabaseExt.queryByNamedSql(
				"default", QRY_TRANS_REGISTER_SQL, "");
		if (transRegisters == null || transRegisters.length == 0) {
			return null;
		}
		Map<String, TransRegisterModel> registerMap = new HashMap<String, TransRegisterModel>();
		for (int i = 0; i < transRegisters.length; i++) {
			TransRegisterModel transRegister = (TransRegisterModel) transRegisters[i];
			registerMap.put(transRegister.getTransCode(), transRegister);
		}
		return registerMap;
	}
	
	/**
	 * @Description:查询指定交易登记信息
	 * @param transCode 交易码
	 * @return List<TransRegisterModel>
	 */
	public static TransRegisterModel queryTransRegisterInfoByTransCode(String transCode) {
		Object[] transRegisters = DatabaseExt.queryByNamedSql(
				"default",QRY_TRANS_REGISTER_SQL, transCode);
		if (transRegisters == null || transRegisters.length == 0) {
			return null;
		}
		return (TransRegisterModel) transRegisters[0];
	}
	
	/**
	 * @Description:添加交易流水明细信息
	 * @param transDetail
	 */
	public static void addTransDetailRecord(TransDetailModel transDetail) {
		if (logger.isDebugEnabled()) {
			logger.debug("==== addTransDetailRecord===globalId="+transDetail.getChannelSeqNo());
		}
		Date date = new Date();
		transDetail.setCrtTime(date);
		transDetail.setLstUpdDate(date);
		transDetail.setLstUpdTime(date);
		transDetail.setScrLevel("00");
		transDetail.setTransDate(date);
		transDetail.setTransTime(date);
		insertIntoTable(transDetail);
	}

	private static void insertIntoTable(TransDetailModel transDetail) {
		
		if(TransFlowConstant.J_VPRS_001_0001.equals(transDetail.getTransCode()) || TransFlowConstant.J_VPRS_001_0003.equals(transDetail.getTransCode()) || TransFlowConstant.J_VPRS_001_0005.equals(transDetail.getTransCode())){ // 随意说 注册  验证 更新的数据推到kafka
			long start = System.currentTimeMillis();
			pushFlowToKafka(transDetail,transDetail.getTransCode());
			 long end = System.currentTimeMillis();
			 logger.error("推送kafka耗时--->" + (end - start));
		}else if(TransFlowConstant.J_VPRS_003_0001.equals(transDetail.getTransCode()) ){
			long start = System.currentTimeMillis();
			pushToKafka(transDetail);
			 long end = System.currentTimeMillis();
			 logger.error("推送kafka耗时--->" + (end - start));
			//if(!"000000".equals(transDetail.getReturnCode())){
			 long start2 = System.currentTimeMillis();
			DatabaseExt.executeNamedSql("default", INSERT_INTO_AUDIO_TRANS_DETAIL, transDetail);
			 long end2 = System.currentTimeMillis();
			 logger.error("记录交易流水耗时--->" + (end2 - start2));
			//}
		}
		
	}
	
	private static void pushFlowToKafka(TransDetailModel transDetail,String transCode){
		JSONObject sendObject = new JSONObject();
		List<JSONObject> sendList = new ArrayList<JSONObject>();
		
			sendObject.put("TransCode", transCode);
			sendObject.put("UserName", transDetail.getUserName());
			sendObject.put("UserType", transDetail.getUserType());
			sendObject.put("CertNo",transDetail.getCertNo());
			sendObject.put("CertType",transDetail.getCertType());
			sendObject.put("Channel",transDetail.getChannel());
			sendObject.put("UserUniqueId", transDetail.getUserUniqueId());
			sendObject.put("BusinessType",transDetail.getBusinessType());
			sendObject.put("CloseAudio",transDetail.getCloseAudio());
			sendObject.put("CloseAudioFormat",transDetail.getCloseAudioFormat());
			sendObject.put("AudioSamplingRate",transDetail.getAudioSamplingRate());
			sendObject.put("AudioEncodeFormat",transDetail.getAudioEncodeFormat());
			sendObject.put("RiskLevel", transDetail.getRiskLevel());
			
			sendObject.put("IsStore", transDetail.getIsStore());
			
			sendObject.put("TransTime", transDetail.getTransTime());
			String TransTimeStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(transDetail.getTransTime()) + "+0800";
			sendObject.put("TransTimeStr", TransTimeStr);
			sendObject.put("TransCode", transDetail.getTransCode());
			sendObject.put("TransDate", transDetail.getTransDate());
			
		KafukaSender sender = new KafukaSender(ConfigUtils.getProperty("streamVprs.topic"));
		sendList.add(sendObject);
		sender.accept(sendList);
		
	}
	
	private static void pushToKafka(TransDetailModel transDetail){
		JSONObject sendObject = new JSONObject();
		List<JSONObject> sendList = new ArrayList<JSONObject>();
		KafukaSender sender = new KafukaSender(ConfigUtils.getProperty("stream.topic"));

		sendObject.put("ReturnCode", transDetail.getReturnCode());
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
		sendObject.put("TransDate", transDetail.getTransDate());
		
		sendList.add(sendObject);
		sender.accept(sendList);
		
	}
	
}

