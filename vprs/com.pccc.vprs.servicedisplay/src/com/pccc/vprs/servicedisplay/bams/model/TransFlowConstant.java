package com.pccc.vprs.servicedisplay.bams.model;

import java.util.Map;

import com.pccc.vprs.servicecustom.transflow.TransRegisterModel;


/**
 * @Title:TransFlowConstant.java
 * @date 2017-2-21 下午05:01:06
 * @Copyright: 2017
 */
public class TransFlowConstant {


	public static final String HEADER = "Head";
	
	/** 交易流水号 */
	public static final String HEAD_TRANS_ID = "transId";
	
	/** 全局流水号 */
	public static final String HEAD_GLOBAL_SEQ_NO = "globalSeqNo";
	
	/** 交易码 */
	public static final String HEAD_TRANS_CODE = "transCode";
	
	/** 操作员号 */
	public static final String HEAD_USER_ID = "userId";
	
	/** 业务编号 */
	public static final String HEAD_BUSINESS_ID = "businessId";
	
//	/** 渠道号 */
//	public static final String BODY_CHANNEL  = "channel";
//	/** 业务场景 */
//	public static final String BODY_BUSINESS_TYPE  = "businessType";
//	/** 用户编号 */
//	public static final String BODY_USER_ID  = "userId";
//	/** 工号 */
//	public static final String BODY_EMP_NO  = "empNo";
//	/** 证件类型 */
//	public static final String BODY_CERT_TYPE  = "certType";
//	/** 证件号码 */
//	public static final String BODY_CERT_NO  = "certNo";
//	/** 个人近照 */
//	public static final String BODY_CLOSE_IMG  = "closeImg";
//	/** 证件照  */
//	public static final String BODY_CERT_IMG  = "certImg";
//	/** 证件头像照 */
//	public static final String BODY_CERT_HEAD_IMG  = "certHeadImg";
//	/** 视屏 */
//	public static final String BODY_VIDEO  = "video";
//	/** 手机号*/
//	public static final String BODY_MOBILE  = "mobile";
	
	public static final String BTP_TRANSPORT_SERVICE_DEFINITION = "$btp.transportservice.definition";
	
	//交易名称
	public static final String J_BAMS_001_0001  = "J_BAMS_001_0001";
	public static final String J_BAMS_001_0002  = "J_BAMS_001_0002";
	public static final String J_BAMS_001_0003  = "J_BAMS_001_0003";
	public static final String J_BAMS_001_0004  = "J_BAMS_001_0004";
	public static final String J_BAMS_001_0005  = "J_BAMS_001_0005";
	public static final String J_BAMS_001_0006  = "J_BAMS_001_0006";
	public static final String J_BAMS_001_0007  = "J_BAMS_001_0007";
	public static final String J_BAMS_001_0008  = "J_BAMS_001_0008";
	public static final String J_BAMS_001_0009  = "J_BAMS_001_0009";
	public static final String J_BAMS_001_0010  = "J_BAMS_001_0010";
	public static final String J_BAMS_001_0011  = "J_BAMS_001_0011";
	public static final String J_BAMS_001_0012  = "J_BAMS_001_0012";
	public static final String X_BAMS_001_0001  = "X_BAMS_001_0001";
	
	//流水表表名
	public static final String TBL_BAMS_REGISTER_DETAIL  = "tbl_bams_register_detail";
	//登录明细流水表
	public static final String TBL_BAMS_LOGIN_DETAIL  = "tbl_bams_login_detail";
	//脸脸比对流水表
	public static final String TBL_BAMS_FACE_COMPARE_DETAIL  = "tbl_bams_face_compare_detail";
	//人证比对流水表
	public static final String TBL_BAMS_CERT_COMPARE_DETAIL  = "tbl_bams_cert_compare_detail";
	//人脸视频校验流水表
	public static final String TBL_BAMS_VIDEO_COMPARE_DETAIL  = "TBL_BAMS_VIDEO_COMPARE_DETAIL";
	//本地和人证双重校验流水表
	public static final String TBL_BAMS_MULTI_COMPARE_DETAIL  = "tbl_bams_multi_compare_detail";
	//其他交易明细流水表
	public static final String TBL_BAMS_TRANS_DETAIL  = "tbl_bams_trans_detail";
	
	
	
	/**注册交易MAP*/
	public static Map<String, TransRegisterModel> transRegisterMap = null;
	
	/**初始化注册交易map*/
	public static void setTransRegisterMap(Map<String, TransRegisterModel> registerMap) {
		transRegisterMap = registerMap;
	}
	
	/**根据交易码获取交易注册信息*/
	public static TransRegisterModel getTransRegister(String transCode) {
		if (transRegisterMap == null || transRegisterMap.get(transCode) == null) {
			return null;
		}
		return transRegisterMap.get(transCode);
	}
	
	
}
