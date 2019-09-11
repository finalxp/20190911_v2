package com.pccc.vprs.servicedisplay.bams.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.File;
import java.io.IOException;

import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicedisplay.vprs.audio.iat.Iat;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

/**
 * 将输入的amr音频文件转化为wav文件，比特率16*16k
 * @author A174669
 *
 */

public class AudioFormatChange {
	
	private static ILogger logger = LoggerFactory.getLogger(AudioFormatChange.class);
	
	public static void changeToWav(String sourcePath, String targetPath) {
		 
//		File source = new File(sourcePath);
//		File target = new File(targetPath);
//		
//        //先创建文件吧
////		File f = new File(directory);
////		if (!target.exists()) {
////			// 如果文件夹不存在，先创建文件所在的目录
////			target.getParentFile().mkdirs();
////			try {
////				// 创建新文件
////				target.createNewFile();
////			} catch (IOException e) {
////				logger.error("创建文件时出现错误，错误信息：" + e.getMessage());
////			}
////		}
//		
//		logger.info("source###"+sourcePath);
//		logger.info("target###"+targetPath);
//		AudioAttributes audio = new AudioAttributes();
//		Encoder encoder = new Encoder();
//
//		audio.setCodec("pcm_s32le");
//		audio.setBitRate(new Integer(256000));
//		EncodingAttributes attrs = new EncodingAttributes();
//		attrs.setFormat("wav");
//		attrs.setAudioAttributes(audio);
//		
//		try {
//			encoder.encode(source, target, attrs);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			logger.info("IllegalArgumentException");
//		} catch (InputFormatException e) {
//			e.printStackTrace();
//			logger.info("InputFormatException");
//		} catch (EncoderException e) {
//			e.printStackTrace();
//			logger.info("EncoderException");
//		}
		
		
		
		//转格式 转属性
		 //./ffmpeg  -i test.amr  -b:v  256k  -acodec pcm_s32le  test.wav
		//./ffmpeg -y  -i test.wav  -ar 16000 -ac 1  thefinal.wav
		  InputStreamReader stdISR = null;
		  InputStreamReader errISR = null;
		  String[] command = new String[2];
		  command[0] = VprsConstant.AUDIO_FFMPEG_PATH+" -i"+" "+sourcePath+" "+"-b:v 256k -acodec pcm_s32le"+" "+targetPath; 
//		  command[1] = "/app/testffmpeg/ffmpeg  -y -i"+" "+targetPath+" "+"-ar 16000 -ac 1"+" "+" "+targetPath;
		  command[1] = VprsConstant.AUDIO_FFMPEG_PATH+"  -y -i"+" "+targetPath+" "+"-ar "+VprsConstant.KEDA_AUDIO_RATE+" -ac 1"+" "+" "+targetPath;
		  for(int i=0;i<command.length;i++)
		  {
			  Process process = null;
		  try {
			  
				   
				  
				   process = Runtime.getRuntime().exec(command[i]);
				   
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
}
