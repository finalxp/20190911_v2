package com.pccc.vprs.servicedisplay.tts.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import ch.qos.logback.classic.Logger;

import com.pccc.vprs.servicecustom.constants.VprsConstant;
//import com.pccc.vprs.servicedisplay.vprs.audio.tts.Tts;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

public class AudioTts {
	private static ILogger logger = LoggerFactory.getLogger(AudioTts.class);
	
	public static String start(String content,String filePath){
		TextAnalyze ta = new TextAnalyze();
		HttpHead httpHead = new HttpHead();
		httpHead.setAppkey("ac5d5452");
		String ttsurl=VprsConstant.KEDA_TTS_URL;
//		httpHead.setUrl("http://182.180.117.36:8080/tts/SynthText");
		httpHead.setUrl(ttsurl);
		//httpHead.setUrl("http://182.180.113.71:5060/tts/SynthText");
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		httpHead.setRequestDate(retStrFormatNowDate);
		httpHead.setSdkVersion("3.8");
		String developKey = "developer_key";
		developKey = MD5.getMD5Code(retStrFormatNowDate+developKey);
		httpHead.setSessionKey(developKey);
		httpHead.setUdid("101:1234567890");
		Map<String,String> taskConfig = new HashMap<String, String>();
		taskConfig.put("capkey", "tts.cloud.synth");
		taskConfig.put("property", "cn_xiaokunv9_common");
		taskConfig.put("audioformat", "pcm8k16bit");
		httpHead.setTaskConfig(taskConfig);
		String json = content;
		AnalyzeResp res = ta.textAnalyze(httpHead, json , filePath);
		logger.info("返回错误码:" + res.getErrorCode());
		logger.info("音频长度:" + res.getResultText());
//		System.out.println("返回错误码:" + res.getErrorCode());
//		System.out.println("音频长度:" + res.getResultText());
		return res.getErrorCode();
	}
}
