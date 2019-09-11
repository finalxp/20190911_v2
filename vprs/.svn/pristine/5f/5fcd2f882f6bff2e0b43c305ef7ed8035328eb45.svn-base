/**  
 * Copyright © 2016捷通. All rights reserved.
 * @Title: AddResource.java
 * @Prject: jtts_http
 * @Package: com.sinovoice.hcicloud.analyze
 * @date: 2016年8月9日 下午1:27:23
 * @version: V1.0  
 */
package com.pccc.vprs.servicedisplay.tts.util;

import com.pccc.vprs.servicedisplay.tts.util.HttpHead;
import com.pccc.vprs.servicedisplay.tts.util.HttpPost;
import com.pccc.vprs.servicedisplay.tts.util.XmlOperation;
import com.pccc.vprs.servicedisplay.tts.util.AnalyzeResp;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

/**
 * Copyright © 2016捷通. All rights reserved.
 * @Title: TextAnalyze.java
 * @ClassName: jtts_http
 * @date: 2016年10月25日 下午1:27:23
 */
public class TextAnalyze {
	
	private static ILogger logger = LoggerFactory.getLogger(TextAnalyze.class);
	
	/**
	 * @Title: textAnalyze
	 * @Description: 语音合成
	 * @param httpHead
	 * @param jsonRequest
	 * @return
	 * @return: AnalyzeResp
	 */
	public AnalyzeResp textAnalyze(HttpHead httpHead,String jsonRequest,String filePath){
		AnalyzeResp analyzeResp = null;
		HttpPost httpPost = new HttpPost();
//		String pcmfile = "d:\\test.pcm";
		String pcmfile = filePath;
		
		String result = httpPost.doHciQuery(httpHead, jsonRequest, pcmfile);
		//获取返回前面的xml，后面是音频数据，本Demo不做处理
//		System.out.println(result);
		logger.info("result:"+result);
		if(result!=null){
			try {
				analyzeResp = (AnalyzeResp) XmlOperation.xmlToObject(AnalyzeResp.class, result);
			} catch (Exception e) {
				analyzeResp = null;
			}
		}
		return analyzeResp;
	}
}
