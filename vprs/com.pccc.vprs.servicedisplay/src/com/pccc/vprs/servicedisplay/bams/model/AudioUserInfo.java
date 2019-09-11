package com.pccc.vprs.servicedisplay.bams.model;

import java.io.Serializable;
import java.util.Date;

public class AudioUserInfo implements Serializable {
	

	private int    id;	//主键
	private String userName;	//用户姓名
	private String certType;	//证件类型
	private String certNo;	//证件号码
	private String userType;	//用户类型
	private String phoneNo;	//手机号码 
	
	//private String fsAudioTotalDuration;
	private String isFsRegistered;	//是否已经注册自由说声纹
	
	private String lowDuration; //低风险等级语音时长
	private String middleDuration; //中风险等级语音时长
	private String highDuration; //高风险等级语音时长
	private String superHighDuration; //超高风险等级语音时长
	private String specialDuraion; //特殊风险等级语音时长
	private String pdaDuration; //PDA风险等级语音时长
	private String facetimeDuration; //视频核身风险等级语音时长
	private String audioIds;		//用于存储注册时使用了哪些音频ID
	
	private String maxRiskLevel;   //总时长大于30s的最高风险等级
	private String regRiskLevel;   //注册风险等级
	
	private Date   crtTime;
	private Date   crtDate;
	private String crtChannel;
	private Date   lstUpdTime;
	private Date   lstUpdDate;
	private String lstUpdUser;
	private String scrLevel ; 
	private String reserveCoulumn1;
	private String reserveCoulumn2;
	private String reserveCoulumn3;
	private String reserveCoulumn4;
	private String reserveCoulumn5;
	
	public String getAudioIds() {
		return audioIds;
	}
	public void setAudioIds(String audioIds) {
		this.audioIds = audioIds;
	}
	public String getMaxRiskLevel() {
		return maxRiskLevel;
	}
	public void setMaxRiskLevel(String maxRiskLevel) {
		this.maxRiskLevel = maxRiskLevel;
	}
	public String getRegRiskLevel() {
		return regRiskLevel;
	}
	public void setRegRiskLevel(String regRiskLevel) {
		this.regRiskLevel = regRiskLevel;
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
	public String getCrtChannel() {
		return crtChannel;
	}
	public void setCrtChannel(String crtChannel) {
		this.crtChannel = crtChannel;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getHighDuration() {
		return highDuration;
	}
	public void setHighDuration(String highDuration) {
		this.highDuration = highDuration;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIsFsRegistered() {
		return isFsRegistered;
	}
	public void setIsFsRegistered(String isFsRegistered) {
		this.isFsRegistered = isFsRegistered;
	}
	public String getLowDuration() {
		return lowDuration;
	}
	public void setLowDuration(String lowDuration) {
		this.lowDuration = lowDuration;
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
	public String getMiddleDuration() {
		return middleDuration;
	}
	public void setMiddleDuration(String middleDuration) {
		this.middleDuration = middleDuration;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
	public String getScrLevel() {
		return scrLevel;
	}
	public void setScrLevel(String scrLevel) {
		this.scrLevel = scrLevel;
	}
	public String getSpecialDuraion() {
		return specialDuraion;
	}
	public void setSpecialDuraion(String specialDuraion) {
		this.specialDuraion = specialDuraion;
	}
	public String getSuperHighDuration() {
		return superHighDuration;
	}
	public void setSuperHighDuration(String superHighDuration) {
		this.superHighDuration = superHighDuration;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getPdaDuration() {
		return pdaDuration;
	}
	public void setPdaDuration(String pdaDuration) {
		this.pdaDuration = pdaDuration;
	}
	
	
	public String getFacetimeDuration() {
		return facetimeDuration;
	}
	public void setFacetimeDuration(String facetimeDuration) {
		this.facetimeDuration = facetimeDuration;
	}
	@Override
	public String toString() {
		return "AudioUserInfo [id=" + id + ", userName=" + userName
				+ ", certType=" + certType + ", certNo=" + certNo
				+ ", userType=" + userType + ", phoneNo=" + phoneNo
				+ ", isFsRegistered=" + isFsRegistered + ", lowDuration="
				+ lowDuration + ", middleDuration=" + middleDuration
				+ ", highDuration=" + highDuration + ", superHighDuration="
				+ superHighDuration + ", specialDuraion=" + specialDuraion
				+ ", audioIds=" + audioIds + ", crtTime=" + crtTime
				+ ", crtDate=" + crtDate + ", crtChannel=" + crtChannel
				+ ", lstUpdTime=" + lstUpdTime + ", lstUpdDate=" + lstUpdDate
				+ ", lstUpdUser=" + lstUpdUser + ", scrLevel=" + scrLevel
				+ ", reserveCoulumn1=" + reserveCoulumn1 + ", reserveCoulumn2="
				+ reserveCoulumn2 + ", reserveCoulumn3=" + reserveCoulumn3
				+ ", reserveCoulumn4=" + reserveCoulumn4 + ", reserveCoulumn5="
				+ reserveCoulumn5 + "]";
	}
	
}
