package com.pccc.vprs.servicecustom.transflow;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易流水明细
 * @author A174669
 *
 */
public class TransDetailModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 流水编号 */
	private  String  jnlSeqNo ;
	/** 渠道流水号 */
	private  String  channelSeqNo ; 
	/** 渠道号 */
	private  String  channel ;
	/** 业务场景 */
	private  String  businessType;
	/** 交易日期 */
	private  Date  transDate; 
	/** 交易时间 */
	private  Date  transTime ; 
	/** 交易名称 */
	private  String  transCode ; 
	/** 交易描述 */
	private  String  transDesc ;   
	/** 用户唯一标识 */
	private  String  userUniqueId    ;   
	/** 用户姓名*/
	private  String  userName;
	/** 证件类型 */
	private  String  certType;
	/** 证件号码 */
	private  String  certNo;
	/** 最近音频*/
	private  String  closeAudio;
//	/** 个人近照 */
//	private  String  closeImg;
//	/** 证件照 */
//	private  String  certImg;
//	/** 证件正面照 */
//	private  String  certFaceImg;
//	/** 证件反面照 */
//	private  String  certBackImg;
//	/** 证件头像照 */
//	private  String  certHeadImg;
//	/** 视屏 */
//	private  String  video;
	/** 请求报文 */
	private  String  transRequestMsg;   
	/** 响应报文 */
	private  String  transResponseMsg;  
	/** 声纹类型 */
	private  String  audioType;
	/** 状态 */
	private  String  status;      
	/** 返回码 */
	private  String  returnCode ; 
	/** 比对分数 */
	private  String  score;
	/** 返回信息 */
	private  String  returnMsg;
	/** 服务器节点 */
	private  String  serverNode  ;  
	/** 创建时间 */
	private Date   crtTime;
	/** 最后更新时间 */
	private Date   lstUpdTime;
	/** 最后更新日期 */
	private Date   lstUpdDate;
	/** 最后操作人 */
	private String lstUpdUser;
	/** 保密级别 */
	private String scrLevel ; 
	private String reserveCoulumn1;
	private String reserveCoulumn2;
	private String reserveCoulumn3;
	private String reserveCoulumn4;
	private String reserveCoulumn5;
	
	private String closeAudioFormat;
	private String audioSamplingRate;
	private String audioEncodeFormat;
	private String isStore;
	
	private String riskLevel; //风险等级
	private String userType;	//用户类型
	private String phoneNo; //手机号码
	
	private String compareResult; //比较结果
	
	//userinfo表中字段
	private int    userinfo_id;
	private String userinfo_userName;
	private String userinfo_certType;
	private String userinfo_certNo;
	
	private String userinfo_hasFSModel;  //是否有随意说模型
	private String userinfo_hasTDModel;  //是否有固定文本模型
	private String userinfo_fsStatus;    //随意说模型服务状态
	private String userinfo_tdStatus;    //固定文本服务状态

	private String userinfo_closeAudioFs;   //随意说注册成功的音频存放地址
	
	private String userinfo_closeAudioTd1;  //固定文本注册成功的音频存放地址
	
	private String userinfo_closeAudioTd2;  //固定文本注册成功顺延第一条
	
	private String userinfo_closeAudioTd3;  //固定文本注册成功顺延第二条
	
	private String userinfo_userStatus;      //用户状态（控风险）
	
	private String userinfo_riskLevel;       //风险等级
	
	private Date   userinfo_crtTime;
	private String userinfo_crtChannel;
	private Date   userinfo_lstUpdTime;
	private Date   userinfo_lstUpdDate;
	private String userinfo_lstUpdUser;
	private String userinfo_scrLevel ; 
	private String userinfo_reserveCoulumn1;
	private String userinfo_reserveCoulumn2;
	private String userinfo_reserveCoulumn3;
	private String userinfo_reserveCoulumn4;
	private String userinfo_reserveCoulumn5;
	private String userinfo_userUniqueId;
	
	
	public int getUserinfo_id() {
		return userinfo_id;
	}



	public void setUserinfo_id(int userinfo_id) {
		this.userinfo_id = userinfo_id;
	}



	public String getUserinfo_userName() {
		return userinfo_userName;
	}



	public void setUserinfo_userName(String userinfo_userName) {
		this.userinfo_userName = userinfo_userName;
	}



	public String getUserinfo_certType() {
		return userinfo_certType;
	}



	public void setUserinfo_certType(String userinfo_certType) {
		this.userinfo_certType = userinfo_certType;
	}



	public String getUserinfo_certNo() {
		return userinfo_certNo;
	}



	public void setUserinfo_certNo(String userinfo_certNo) {
		this.userinfo_certNo = userinfo_certNo;
	}



	public String getUserinfo_hasFSModel() {
		return userinfo_hasFSModel;
	}



	public void setUserinfo_hasFSModel(String userinfo_hasFSModel) {
		this.userinfo_hasFSModel = userinfo_hasFSModel;
	}



	public String getUserinfo_hasTDModel() {
		return userinfo_hasTDModel;
	}



	public void setUserinfo_hasTDModel(String userinfo_hasTDModel) {
		this.userinfo_hasTDModel = userinfo_hasTDModel;
	}



	public String getUserinfo_fsStatus() {
		return userinfo_fsStatus;
	}



	public void setUserinfo_fsStatus(String userinfo_fsStatus) {
		this.userinfo_fsStatus = userinfo_fsStatus;
	}



	public String getUserinfo_tdStatus() {
		return userinfo_tdStatus;
	}



	public void setUserinfo_tdStatus(String userinfo_tdStatus) {
		this.userinfo_tdStatus = userinfo_tdStatus;
	}



	public String getUserinfo_closeAudioFs() {
		return userinfo_closeAudioFs;
	}



	public void setUserinfo_closeAudioFs(String userinfo_closeAudioFs) {
		this.userinfo_closeAudioFs = userinfo_closeAudioFs;
	}



	public String getUserinfo_closeAudioTd1() {
		return userinfo_closeAudioTd1;
	}



	public void setUserinfo_closeAudioTd1(String userinfo_closeAudioTd1) {
		this.userinfo_closeAudioTd1 = userinfo_closeAudioTd1;
	}



	public String getUserinfo_closeAudioTd2() {
		return userinfo_closeAudioTd2;
	}



	public void setUserinfo_closeAudioTd2(String userinfo_closeAudioTd2) {
		this.userinfo_closeAudioTd2 = userinfo_closeAudioTd2;
	}



	public String getUserinfo_closeAudioTd3() {
		return userinfo_closeAudioTd3;
	}



	public void setUserinfo_closeAudioTd3(String userinfo_closeAudioTd3) {
		this.userinfo_closeAudioTd3 = userinfo_closeAudioTd3;
	}



	public String getUserinfo_userStatus() {
		return userinfo_userStatus;
	}



	public void setUserinfo_userStatus(String userinfo_userStatus) {
		this.userinfo_userStatus = userinfo_userStatus;
	}



	public String getUserinfo_riskLevel() {
		return userinfo_riskLevel;
	}



	public void setUserinfo_riskLevel(String userinfo_riskLevel) {
		this.userinfo_riskLevel = userinfo_riskLevel;
	}



	public Date getUserinfo_crtTime() {
		return userinfo_crtTime;
	}



	public void setUserinfo_crtTime(Date userinfo_crtTime) {
		this.userinfo_crtTime = userinfo_crtTime;
	}



	public String getUserinfo_crtChannel() {
		return userinfo_crtChannel;
	}



	public void setUserinfo_crtChannel(String userinfo_crtChannel) {
		this.userinfo_crtChannel = userinfo_crtChannel;
	}



	public Date getUserinfo_lstUpdTime() {
		return userinfo_lstUpdTime;
	}



	public void setUserinfo_lstUpdTime(Date userinfo_lstUpdTime) {
		this.userinfo_lstUpdTime = userinfo_lstUpdTime;
	}



	public Date getUserinfo_lstUpdDate() {
		return userinfo_lstUpdDate;
	}



	public void setUserinfo_lstUpdDate(Date userinfo_lstUpdDate) {
		this.userinfo_lstUpdDate = userinfo_lstUpdDate;
	}



	public String getUserinfo_lstUpdUser() {
		return userinfo_lstUpdUser;
	}



	public void setUserinfo_lstUpdUser(String userinfo_lstUpdUser) {
		this.userinfo_lstUpdUser = userinfo_lstUpdUser;
	}



	public String getUserinfo_scrLevel() {
		return userinfo_scrLevel;
	}



	public void setUserinfo_scrLevel(String userinfo_scrLevel) {
		this.userinfo_scrLevel = userinfo_scrLevel;
	}



	public String getUserinfo_reserveCoulumn1() {
		return userinfo_reserveCoulumn1;
	}



	public void setUserinfo_reserveCoulumn1(String userinfo_reserveCoulumn1) {
		this.userinfo_reserveCoulumn1 = userinfo_reserveCoulumn1;
	}



	public String getUserinfo_reserveCoulumn2() {
		return userinfo_reserveCoulumn2;
	}



	public void setUserinfo_reserveCoulumn2(String userinfo_reserveCoulumn2) {
		this.userinfo_reserveCoulumn2 = userinfo_reserveCoulumn2;
	}



	public String getUserinfo_reserveCoulumn3() {
		return userinfo_reserveCoulumn3;
	}



	public void setUserinfo_reserveCoulumn3(String userinfo_reserveCoulumn3) {
		this.userinfo_reserveCoulumn3 = userinfo_reserveCoulumn3;
	}



	public String getUserinfo_reserveCoulumn4() {
		return userinfo_reserveCoulumn4;
	}



	public void setUserinfo_reserveCoulumn4(String userinfo_reserveCoulumn4) {
		this.userinfo_reserveCoulumn4 = userinfo_reserveCoulumn4;
	}



	public String getUserinfo_reserveCoulumn5() {
		return userinfo_reserveCoulumn5;
	}



	public void setUserinfo_reserveCoulumn5(String userinfo_reserveCoulumn5) {
		this.userinfo_reserveCoulumn5 = userinfo_reserveCoulumn5;
	}



	public String getUserinfo_userUniqueId() {
		return userinfo_userUniqueId;
	}



	public void setUserinfo_userUniqueId(String userinfo_userUniqueId) {
		this.userinfo_userUniqueId = userinfo_userUniqueId;
	}



	public String getCompareResult() {
		return compareResult;
	}



	public void setCompareResult(String compareResult) {
		this.compareResult = compareResult;
	}



	public String getRiskLevel() {
		return riskLevel;
	}



	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}



	public String getCloseAudioFormat() {
		return closeAudioFormat;
	}



	public void setCloseAudioFormat(String closeAudioFormat) {
		this.closeAudioFormat = closeAudioFormat;
	}



	public String getBusinessType() {
		return businessType;
	}



	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getAudioType() {
		return audioType;
	}



	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}

	
	public String getCloseAudio() {
		return closeAudio;
	}



	public void setCloseAudio(String closeAudio) {
		this.closeAudio = closeAudio;
	}
	
//	public String getCertBackImg() {
//		return certBackImg;
//	}
//
//
//
//	public void setCertBackImg(String certBackImg) {
//		this.certBackImg = certBackImg;
//	}
//
//
//
//	public String getCertFaceImg() {
//		return certFaceImg;
//	}
//
//
//
//	public void setCertFaceImg(String certFaceImg) {
//		this.certFaceImg = certFaceImg;
//	}
//
//
//
//	public String getCertHeadImg() {
//		return certHeadImg;
//	}
//
//
//
//	public void setCertHeadImg(String certHeadImg) {
//		this.certHeadImg = certHeadImg;
//	}
//
//
//
//	public String getCertImg() {
//		return certImg;
//	}
//
//
//
//	public void setCertImg(String certImg) {
//		this.certImg = certImg;
//	}



	public String getCertNo() {
		return certNo;
	}



	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}



	public String getCertType() {
		return certType;
	}



	public void setCertType(String certType) {
		this.certType = certType;
	}



	public String getChannel() {
		return channel;
	}



	public void setChannel(String channel) {
		this.channel = channel;
	}



	public String getChannelSeqNo() {
		return channelSeqNo;
	}



	public void setChannelSeqNo(String channelSeqNo) {
		this.channelSeqNo = channelSeqNo;
	}



//	public String getCloseImg() {
//		return closeImg;
//	}
//
//
//
//	public void setCloseImg(String closeImg) {
//		this.closeImg = closeImg;
//	}



	public Date getCrtTime() {
		return crtTime;
	}



	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}



	public String getJnlSeqNo() {
		return jnlSeqNo;
	}



	public void setJnlSeqNo(String jnlSeqNo) {
		this.jnlSeqNo = jnlSeqNo;
	}



	public Date getLstUpdDate() {
		return lstUpdDate;
	}



	public void setLstUpdDate(Date lstUpdDate) {
		this.lstUpdDate = lstUpdDate;
	}



	public Date getLstUpdTime() {
		return lstUpdTime;
	}



	public void setLstUpdTime(Date lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}



	public String getLstUpdUser() {
		return lstUpdUser;
	}



	public void setLstUpdUser(String lstUpdUser) {
		this.lstUpdUser = lstUpdUser;
	}



	public String getReserveCoulumn1() {
		return reserveCoulumn1;
	}



	public void setReserveCoulumn1(String reserveCoulumn1) {
		this.reserveCoulumn1 = reserveCoulumn1;
	}



	public String getReserveCoulumn2() {
		return reserveCoulumn2;
	}



	public void setReserveCoulumn2(String reserveCoulumn2) {
		this.reserveCoulumn2 = reserveCoulumn2;
	}



	public String getReserveCoulumn3() {
		return reserveCoulumn3;
	}



	public void setReserveCoulumn3(String reserveCoulumn3) {
		this.reserveCoulumn3 = reserveCoulumn3;
	}



	public String getReserveCoulumn4() {
		return reserveCoulumn4;
	}



	public void setReserveCoulumn4(String reserveCoulumn4) {
		this.reserveCoulumn4 = reserveCoulumn4;
	}



	public String getReserveCoulumn5() {
		return reserveCoulumn5;
	}



	public void setReserveCoulumn5(String reserveCoulumn5) {
		this.reserveCoulumn5 = reserveCoulumn5;
	}



	public String getReturnCode() {
		return returnCode;
	}



	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}



	public String getReturnMsg() {
		return returnMsg;
	}



	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}



	public String getScore() {
		return score;
	}



	public void setScore(String score) {
		this.score = score;
	}



	public String getScrLevel() {
		return scrLevel;
	}



	public void setScrLevel(String scrLevel) {
		this.scrLevel = scrLevel;
	}



	public String getServerNode() {
		return serverNode;
	}



	public void setServerNode(String serverNode) {
		this.serverNode = serverNode;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getTransCode() {
		return transCode;
	}



	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}



	public Date getTransDate() {
		return transDate;
	}



	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}



	public String getTransDesc() {
		return transDesc;
	}



	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}



	public String getTransRequestMsg() {
		return transRequestMsg;
	}



	public void setTransRequestMsg(String transRequestMsg) {
		this.transRequestMsg = transRequestMsg;
	}



	public String getTransResponseMsg() {
		return transResponseMsg;
	}



	public void setTransResponseMsg(String transResponseMsg) {
		this.transResponseMsg = transResponseMsg;
	}



	public Date getTransTime() {
		return transTime;
	}



	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}



	public String getUserUniqueId() {
		return userUniqueId;
	}



	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}


    
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



//	public String getVideo() {
//		return video;
//	}
//
//
//
//	public void setVideo(String video) {
//		this.video = video;
//	}

	
	public String getAudioSamplingRate() {
		return audioSamplingRate;
	}



	public void setAudioSamplingRate(String audioSamplingRate) {
		this.audioSamplingRate = audioSamplingRate;
	}



	public String getAudioEncodeFormat() {
		return audioEncodeFormat;
	}



	public void setAudioEncodeFormat(String audioEncodeFormat) {
		this.audioEncodeFormat = audioEncodeFormat;
	}



	public String getIsStore() {
		return isStore;
	}



	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "TransDetailModel [jnlSeqNo=" + jnlSeqNo + ", channelSeqNo="
				+ channelSeqNo + ", channel=" + channel + ", businessType="
				+ businessType + ", transDate=" + transDate + ", transTime="
				+ transTime + ", transCode=" + transCode + ", transDesc="
				+ transDesc + ", userUniqueId=" + userUniqueId + ", userName="
				+ userName + ", certType=" + certType + ", certNo=" + certNo
				+ ", closeAudio=" + closeAudio + ", audioType=" + audioType
				+ ", status=" + status + ", returnCode=" + returnCode
				+ ", score=" + score + ", returnMsg=" + returnMsg
				+ ", serverNode=" + serverNode + ", crtTime=" + crtTime
				+ ", lstUpdTime=" + lstUpdTime + ", lstUpdDate=" + lstUpdDate
				+ ", lstUpdUser=" + lstUpdUser + ", scrLevel=" + scrLevel
				+ ", reserveCoulumn1=" + reserveCoulumn1 + ", reserveCoulumn2="
				+ reserveCoulumn2 + ", reserveCoulumn3=" + reserveCoulumn3
				+ ", reserveCoulumn4=" + reserveCoulumn4 + ", reserveCoulumn5="
				+ reserveCoulumn5 + ", closeAudioFormat=" + closeAudioFormat
				+ ", riskLevel=" + riskLevel + ", userType=" + userType
				+ ", phoneNo=" + phoneNo + "]";
	}
	
	


	public String getPhoneNo() {
		return phoneNo;
	}



	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}

}
