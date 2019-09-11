package com.pccc.vprs.servicecustom.interceptor;

import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
import com.primeton.btp.api.engine.trans.handler.TransServiceHandlerManager;
import com.primeton.btp.api.transportservice.handler.ITransportServiceHandler;

/**
 * 交易服务拦截器
 * @author A144803
 *
 */
public class HandlerLaunch implements IContributionListener {
	
	public void load(IContributionEvent event) {
		//交易流水-交易服务拦截器
		TransServiceHandlerManager.registerHandler(new TransServiceHandler());
		
	}
	public void loadFinished(IContributionEvent event) {
		
	}

	public void unLoad(IContributionEvent event) {
		ITransportServiceHandler.MANAGER.removeAll(); 
	}
	
	
	
	
}
