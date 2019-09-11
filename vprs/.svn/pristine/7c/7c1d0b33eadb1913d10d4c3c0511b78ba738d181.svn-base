/**  
 * Copyright © 2016捷通. All rights reserved.
 * @Title: HttpPost.java
 * @Prject: jtts_http
 * @Package: com.sinovoice.hcicloud.common
 * @date: 2016年10月25日 下午1:54:59
 * @version: V1.0  
 */
package com.pccc.vprs.servicedisplay.tts.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Copyright © 2016捷通. All rights reserved.
 * @Title: HttpPost.java
 * @ClassName: HttpPost
 * @date: 2016年8月9日 下午1:54:59
 */
@SuppressWarnings("deprecation")
public class HttpPost {
	public static MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager=new MultiThreadedHttpConnectionManager();
	static{
		multiThreadedHttpConnectionManager.setMaxTotalConnections(400);
		multiThreadedHttpConnectionManager.setMaxConnectionsPerHost(200);
	}
	
	public int indexOf(byte [] source, byte [] tag) {
	    for (int i = 0; i < (source.length - tag.length) + 1; ++ i) {
	    	int j = 0;
	    	for (j = 0; j < tag.length; ++ j) {
	    		if (source[i + j] != tag[j]) {
	    			break;
	    		}
	    	}
	    	if (j == tag.length) {
	    		return i;
	    	}
	    }
	    
	    return -1;
	}

	public String doHciQuery(HttpHead HttpHead, String query, String pcmfile){
		String result = null;
		HttpClient client = new HttpClient(multiThreadedHttpConnectionManager);
		client.getHttpConnectionManager().getParams().setSoTimeout(10000);
		PostMethod method = new PostMethod(HttpHead.getUrl());
		method.setRequestHeader("Connection", "close");
		method.setRequestHeader("x-app-key", HttpHead.getAppkey());
		method.setRequestHeader("x-sdk-version", HttpHead.getSdkVersion());
		method.setRequestHeader("x-request-date", HttpHead.getRequestDate());
		
		String taskConfig ="";
		if(HttpHead.getTaskConfig()!=null&&HttpHead.getTaskConfig().size()>0){
			Iterator<Entry<String, String>> iter = HttpHead.getTaskConfig().entrySet().iterator();
			while (iter.hasNext()){
				Entry<String, String> e = iter.next();
				String key = e.getKey();
				String value = e.getValue();
				taskConfig += key+"="+value + ",";
			}
		}
		if(taskConfig.trim().length()>0){
			taskConfig = taskConfig.substring(0, taskConfig.length()-1);
		}
		method.setRequestHeader("x-task-config", taskConfig);
		method.setRequestHeader("x-session-key", HttpHead.getSessionKey());
		method.setRequestHeader("x-udid",HttpHead.getUdid());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(10000); 
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);  
		try {
				RequestEntity requestEntity = new ByteArrayRequestEntity(
						query.getBytes("UTF-8"), "UTF-8");
				method.setRequestEntity(requestEntity);
		} catch (Exception e) {
				e.printStackTrace();
		}
		//发出请求
	    int stateCode = 0;
		StopWatch stopWatch = new StopWatch();
		try {
			stopWatch.start();
			stateCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
			stopWatch.stop();
//			System.out.println((stopWatch.toString()+"----|----"+query));
			if(stateCode==HttpStatus.SC_OK) { 
				try {
					byte[] body = method.getResponseBody();
					byte[] tag = "</ResponseInfo>".getBytes();
					int taglen = "</ResponseInfo>".length();
					// 未考虑失败
					int septor = indexOf(body, tag) + taglen;

					byte[] txt = new byte[septor];
					System.arraycopy(body, 0, txt, 0, septor);

					result = new String(txt, "UTF-8");
//					System.out.println(result);
					
					if (body.length - septor > 0) {
						byte[] pcm = new byte[body.length - septor];
						System.arraycopy(body, septor, pcm, 0, body.length - septor);

						FileOutputStream fos = new FileOutputStream(pcmfile);
					    fos.write(body, septor,  body.length - septor);
					    fos.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
//				System.out.println(query+"----|----返回状态码："+stateCode);
			}
			}catch(Exception e){}
			finally{
				method.abort();
				method.releaseConnection();
			}
		}
		return result;
	}
}
