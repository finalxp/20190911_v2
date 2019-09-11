package com.pccc.vprs.servicecustom.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
import com.pccc.touda.common.event.EventBusManager;
import com.pccc.vprs.servicecustom.eventbus.InsertTransDetailEventProcessor;
import com.pccc.vprs.servicecustom.transflow.TransFlowConstant;
import com.pccc.vprs.servicecustom.transflow.TransFlowManage;
import com.pccc.vprs.servicecustom.transflow.TransRegisterModel;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

/**
 * 初始化交易数据
 * @author A144803
 *
 */
public class TransFlowDataListerer implements IContributionListener {

	private static ILogger logger = LoggerFactory.getLogger(TransFlowDataListerer.class);

	
	public void load(IContributionEvent event) {

	}

	public void loadFinished(IContributionEvent event) {
		Map<String, TransRegisterModel> transRregister = TransFlowManage.queryTransRegisterInfo();
		TransFlowConstant.setTransRegisterMap(transRregister);
		if (logger.isDebugEnabled()) {
			logger.debug("###TransFlowConstant.registerBodyMap.size:"+TransFlowConstant.transRegisterMap.size());
		}
		logger.info("=================eventBus register=======================");
		EventBusManager.INSTANCE.registerAsynProcessor(new InsertTransDetailEventProcessor());
		if (logger.isDebugEnabled()) {
			logger.debug("###EventBusManager.registerAsynProcessor");
		}
		
		
		//当容器加载完毕后，为ffmpeg赋予可执行权限
		addAuthorityForFFMPEG();
	}

	public void unLoad(IContributionEvent event) {
		// TODO 自动生成方法存根

	}

	
//	为ffmpeg文件赋予权限
	public static void addAuthorityForFFMPEG(){
		logger.info("为ffmpeg文件赋予可执行权限");
		List<String> cmd = new ArrayList<String>();
		cmd.add("chmod");
		cmd.add("+x");
		cmd.add("/app/ToudaServerAlone/work/war/WEB-INF/lib/ffmpeg");
		
		logger.info("cmd--->"+cmd);
		List<String> strs = exec(cmd);
		logger.info("执行结果为--->"+strs);
	}
	
	private static List<String> exec(List<String> cmd) {
		
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(cmd);
		builder.redirectErrorStream(true);
		Process proc = null;
		try {
			proc = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		List<String> strs = new ArrayList<String>();
		String line;
		try {
			while((line = stdout.readLine())!=null){
				strs.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			proc.waitFor();
			stdout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strs;
	}
}
