package com.pccc.vprs.servicecustom.eventbus;

import com.google.common.eventbus.Subscribe;
import com.pccc.vprs.servicecustom.transflow.InsertTransDetailEvent;
import com.pccc.vprs.servicecustom.transflow.TransDetailModel;
import com.pccc.vprs.servicecustom.transflow.TransFlowManage;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
/**
 * @Title:InsertTransDetailEventProcessor.java
 * @Description:异步消息通知实现类
 *
 */
public class InsertTransDetailEventProcessor {

	private static ILogger logger = LoggerFactory.getLogger(InsertTransDetailEventProcessor.class);

	/**
	 * 插入交易流水
	 */
	@Subscribe
	public void  addTransDetail(InsertTransDetailEvent event ){
		logger.info("异步记录流水信息");
		//由于请求报文过大，在调用eventbus之前为transDetail赋值
//		DataObject requestSDO = event.getRequestSDO();
//		DataObject responseSDO = event.getResponseSDO();
		TransDetailModel transDetail = event.getTransDetail();

		
		TransFlowManage.addTransDetailRecord(transDetail);
		
		/** 以上为试验，测试随意说注册流水明细表能否成功*/
		
		
		
		
		
		
		
		
		
		
		
		

	}
}
