package com.pccc.vprs.servicecustom.transflow;

import commonj.sdo.DataObject;
/**
 * @Title:InsertTransDetailEvent.java
 * @Description:异步消息通知对象类
 *
 */
public class InsertTransDetailEvent {
	private  DataObject requestSDO;
	private  DataObject responseSDO;
	private  TransDetailModel transDetail;
	
	public InsertTransDetailEvent( DataObject requestSDO,DataObject responseSDO,TransDetailModel transDetail){
		this.requestSDO = requestSDO;
		this.responseSDO = responseSDO;
		this.transDetail = transDetail;
		
	}
	
	public InsertTransDetailEvent(TransDetailModel transDetail){
		this.transDetail = transDetail;
	}
	

	public DataObject getRequestSDO() {
		return requestSDO;
	}

	public void setRequestSDO(DataObject requestSDO) {
		this.requestSDO = requestSDO;
	}

	public DataObject getResponseSDO() {
		return responseSDO;
	}

	public void setResponseSDO(DataObject responseSDO) {
		this.responseSDO = responseSDO;
	}

	public TransDetailModel getTransDetail() {
		return transDetail;
	}

	public void setTransDetail(TransDetailModel transDetail) {
		this.transDetail = transDetail;
	}
	
	
}
