package com.pccc.vprs.servicecustom.transflow;

import java.io.Serializable;

/**
 * 交易登记表
 * @author A144803
 *
 */
public class TransRegisterModel implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private String registerNo;
	
	/** 交易号 */
	private String transCode;
	
	/** 交易描述 */
	private String transDesc ;
	
	/** 操作类型 */
	private String operType;
	
	/** 业务类型 */
	private String volType;

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getTransCode() {
		return transCode;
	}
	
	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getVolType() {
		return volType;
	}

	public void setVolType(String volType) {
		this.volType = volType;
	}
	
	
}
