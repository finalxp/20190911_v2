package com.pccc.vprs.servicedisplay.vprs.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 临时表的pojo类
 * @author A174669
 *
 */
public class InfoTemporary {
	private int    id;
	private String userName;
	private String certType;
	private String certNo;
	private String closeAudio;
	private Date   crtTime;
	private String channel;
	private String businessType;
	private Date   lstUpdTime;
	private Date   lstUpdDate;
	private String lstUpdUser;
	private String scrLevel ; 
	private String reserveCoulumn1;
	private String reserveCoulumn2;
	private String reserveCoulumn3;
	private String reserveCoulumn4;
	private String reserveCoulumn5;
	private String userUniqueId;
	
	public String getUserUniqueId() {
		return userUniqueId;
	}
	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
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
	public String getCloseAudio() {
		return closeAudio;
	}
	public void setCloseAudio(String closeAudio) {
		this.closeAudio = closeAudio;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getScrLevel() {
		return scrLevel;
	}
	public void setScrLevel(String scrLevel) {
		this.scrLevel = scrLevel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
