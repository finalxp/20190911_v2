/**  
 * Copyright © 2016捷通. All rights reserved.
 * @Title: AnalyzeResp.java
 * @Prject: jtts_http
 * @Package: com.sinovoice.hcicloud.model
 * @date: 2016年10月25日 下午2:24:35
 * @version: V1.0  
 */
package com.pccc.vprs.servicedisplay.tts.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Copyright © 2016捷通. All rights reserved.
 * @Title: AnalyzeResp.java
 * @ClassName: AnalyzeResp
 * @Description: TODO
 * @author: wudong
 * @date: 2016年8月9日 下午2:24:35
 */
@XmlRootElement(name = "ResponseInfo")
public class AnalyzeResp implements Serializable{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 2672194967244128558L;
	/**
	 * 处理状态
	 * Success/Failed	完毕/处理失败
	 */
	private String resCode;
	/**
	 * 返回消息
	 */
	private String resMsg;
	/**
	 * 返回码
	 */
	private String errorCode;
	/**
	 * 结果令牌
	 */
	private String resultToken;

	private String Data_Len;
	
	@XmlElement(name = "ResCode")
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	@XmlElement(name = "ResMessage")
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	
	@XmlElement(name = "ErrorNo")
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@XmlElement(name = "ResultToken")
	public String getResultToken() {
		return resultToken;
	}
	public void setResultToken(String resultToken) {
		this.resultToken = resultToken;
	}
	@XmlElement(name = "Data_Len")
	public String getResultText() {
		return Data_Len;
	}
	public void setResultText(String resultText) {
		this.Data_Len = resultText;
	}
	
	
}
