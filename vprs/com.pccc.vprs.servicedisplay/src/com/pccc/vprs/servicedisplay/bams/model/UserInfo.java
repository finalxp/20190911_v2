package com.pccc.vprs.servicedisplay.bams.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = -4968078839135233234L;
	
	private int    id;
	private String userName;
	private String certType;
	private String certNo;
	private String closeImg;
	private Date   closeImgLstUpdTime;
	private BigDecimal score = BigDecimal.ZERO;
	private BigDecimal certFaceScore;
	private String certImg;
	private Date   certChipImgLstUpdTime;
	private String certFaceImg;
	private String certBackImg;
	private Date   certImgLstUpdTime;
	private String certHeadImg;
	private Date   certHeadImgLstUpdTime;
	private String status;
	private Date   crtTime;
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
	private String userUniqueId;
	private String video; 
	
	public String getUserUniqueId() {
		return userUniqueId;
	}
	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getCertBackImg() {
		return certBackImg;
	}
	public void setCertBackImg(String certBackImg) {
		this.certBackImg = certBackImg;
	}
	public Date getCertChipImgLstUpdTime() {
		return certChipImgLstUpdTime;
	}
	public void setCertChipImgLstUpdTime(Date certChipImgLstUpdTime) {
		this.certChipImgLstUpdTime = certChipImgLstUpdTime;
	}
	public String getCertFaceImg() {
		return certFaceImg;
	}
	public void setCertFaceImg(String certFaceImg) {
		this.certFaceImg = certFaceImg;
	}
	public BigDecimal getCertFaceScore() {
		return certFaceScore;
	}
	public void setCertFaceScore(BigDecimal certFaceScore) {
		this.certFaceScore = certFaceScore;
	}
	public String getCertHeadImg() {
		return certHeadImg;
	}
	public void setCertHeadImg(String certHeadImg) {
		this.certHeadImg = certHeadImg;
	}
	public Date getCertHeadImgLstUpdTime() {
		return certHeadImgLstUpdTime;
	}
	public void setCertHeadImgLstUpdTime(Date certHeadImgLstUpdTime) {
		this.certHeadImgLstUpdTime = certHeadImgLstUpdTime;
	}
	public String getCertImg() {
		return certImg;
	}
	public void setCertImg(String certImg) {
		this.certImg = certImg;
	}
	public Date getCertImgLstUpdTime() {
		return certImgLstUpdTime;
	}
	public void setCertImgLstUpdTime(Date certImgLstUpdTime) {
		this.certImgLstUpdTime = certImgLstUpdTime;
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
	public String getCloseImg() {
		return closeImg;
	}
	public void setCloseImg(String closeImg) {
		this.closeImg = closeImg;
	}
	public Date getCloseImgLstUpdTime() {
		return closeImgLstUpdTime;
	}
	public void setCloseImgLstUpdTime(Date closeImgLstUpdTime) {
		this.closeImgLstUpdTime = closeImgLstUpdTime;
	}
	public String getCrtChannel() {
		return crtChannel;
	}
	public void setCrtChannel(String crtChannel) {
		this.crtChannel = crtChannel;
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
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getScrLevel() {
		return scrLevel;
	}
	public void setScrLevel(String scrLevel) {
		this.scrLevel = scrLevel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
