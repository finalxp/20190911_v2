package com.pccc.vprs.servicecustom.common;

public class Constants {
	//流水表状�?
	/** 流水表状�?预记 */
	public static final String STATUS_PRE = "0";
	/** 流水表状�?成功 */
	public static final String STATUS_SUCCESS = "1";
	/** 流水表状�?失败 */
	public static final String STATUS_FAIL = "2";
	
	
	//用户注册表状�?
	/** 用户注册表信�?�?? */
	public static final String STATUS_OPEN = "0";	
	/** 用户注册表信�?关闭 */
	public static final String STATUS_CLOSE = "1";
	
	
	//比对类型
	/** 高清照比�?*/
	public static final String COMPARE_CLEAR_WITH_CLEAR = "0";	
	/** 高清照与网纹照比�?*/
	public static final String COMPARE_CLEAR_WITH_WATERMARK= "1";
	/** 网纹照比�?*/
	public static final String COMPARE_WATERMARK_WITH_WATERMARK = "2";	
	
	
	//生物类型
	/** 人脸 */
	public static final String BIOMETRIC_TYPE_FACE= "face";	
	/** 虹膜 */
	public static final String BIOMETRIC_TYPE_IRIS = "iris";	
	
	
	
	//图片类型
	public static final String IMAGE_TYPE_DEFAULT= "";
	public static final String IMAGE_TYPE_SYSTEM= "0";
	public static final String IMAGE_TYPE_HIGH= "1";
	public static final String IMAGE_TYPE_WATERMASK= "2";
	
	//身份证OCR是否返回证件头像�?
	/** 默认，不返回 */
	public static final String GET_FACE_FLAG_FALSE= "0";
	/** 返回 */
	public static final String GET_FACE_FLAG_TRUE= "1";

	
	
	//风险等级
	/** 低风险等级 */
	public static final String LOW_RISK_LEVEL= "1";
	/** 中风险等级 */
	public static final String MIDDLE_RISK_LEVEL= "2";
	/** 高风险等级 */
	public static final String HIGH_RISK_LEVEL= "3";
	/** 超高风险等级 */
	public static final String SUPER_HIGH_RISK_LEVEL= "3.5";
	/** 特殊风险等级 */
	public static final String SPECIAL_RISK_LEVEL= "4";
	/** 视频核身等级 */
	public static final String FACETIME_RISK_LEVEL= "5";
	/** PDA风险等级 */
	public static final String PDA_RISK_LEVEL= "6";
}
















