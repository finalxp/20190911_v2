package com.pccc.vprs.servicedisplay.tts.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicedisplay.bams.util.AudioFormatChange;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

/**
 * 将pcm格式的音频文件转化为amr文件
 * @author A174669
 *
 */
public class PcmToAmr {
private static ILogger logger = LoggerFactory.getLogger(PcmToAmr.class);
	
    /**
     * 将pcm格式的音频文件转化为amr文件
     * @param sourcePath
     * @param targetPath
     */
	public static void changeToAmr(String sourcePath, String targetPath) {
		 	
		
		//./ffmpeg -f s16le -ar 8000 -ac 1 -i test.pcm target.amr
		  InputStreamReader stdISR = null;
		  InputStreamReader errISR = null;
		  String command= VprsConstant.AUDIO_FFMPEG_PATH+" -f "+"s16le -ar 8000 -ac 1 -i "+sourcePath+" "+targetPath; 
		  Process process = null;
		  try {
				   process = Runtime.getRuntime().exec(command);
				   int exitValue = process.waitFor();
				   String line = null;
				   stdISR = new InputStreamReader(process.getInputStream());
				   BufferedReader stdBR = new BufferedReader(stdISR);
				   while ((line = stdBR.readLine()) != null) {
				    System.out.println("STD line:" + line);
				   }
				   errISR = new InputStreamReader(process.getErrorStream());
				   BufferedReader errBR = new BufferedReader(errISR);
				   while ((line = errBR.readLine()) != null) {
				    System.out.println("ERR line:" + line);
				   }
			  
		  } catch (IOException  e) {
		   e.printStackTrace();
		  }
		   catch( InterruptedException e){
			   e.printStackTrace();   
		  } finally {
		   try {
		    if (stdISR != null) {
		     stdISR.close();
		    }
		    if (errISR != null) {
		     errISR.close();
		    }
		    if (process != null) {
		     process.destroy();
		    }
		   } catch (IOException e) {
			   logger.info("正式执行命令：" + command + "有IO异常");
		   }
		  }
		  }



	
}
