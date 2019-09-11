package com.pccc.vprs.servicedisplay.vprs.model;

import java.util.Date;

public class TtsInfo {
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
	/** 生成的amr格式音频存放路径*/
	private  String  closeAudio;
	
	/** 需转化的文本*/
	private  String  text;

//	/** 请求报文 */
//	private  String  transRequestMsg;   
//	/** 响应报文 */
//	private  String  transResponseMsg;  
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
	
	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}
	




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



//	public String getTransRequestMsg() {
//		return transRequestMsg;
//	}
//
//
//
//	public void setTransRequestMsg(String transRequestMsg) {
//		this.transRequestMsg = transRequestMsg;
//	}
//
//
//
//	public String getTransResponseMsg() {
//		return transResponseMsg;
//	}
//
//
//
//	public void setTransResponseMsg(String transResponseMsg) {
//		this.transResponseMsg = transResponseMsg;
//	}



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

}
