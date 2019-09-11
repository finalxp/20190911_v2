package com.pccc.vprs.servicedisplay.bams.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import com.google.gson.Gson;
import com.iflytek.mt_scylla.mt_scylla;
import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicedisplay.vprs.audio.AudioDetect;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

import commonj.sdo.DataObject;

public class AudioIat {
	
	private static String allResult;
	private static String ssbparam;
	private static mt_scylla mt;
	private static String session_id;
	private static Gson gson = new Gson();
	private static Attribute product = new Attribute();	
	//private static ArrayList<byte[]> buffers = new ArrayList<byte[]>();
	private static boolean b_online = true;  //在线离线控制,false则为离线识别
	//private static int last_length = 0;
	private static HashMap<String , ArrayList<byte[]>> audio_map = new HashMap<String, ArrayList<byte[]>>();
	private static HashMap<String , Integer> last_audio_len = new HashMap<String, Integer>();
	
	private static ILogger logger = LoggerFactory.getLogger(AudioIat.class);
	
    public static void readFile(String path){
		
    	if(audio_map.get(path) != null)
    	{
    		return;
    	}
    	
		File file = new File(path);
		ArrayList<byte[]> buffers = new ArrayList<byte[]>();
		int last_length = 0;
		buffers.clear();
		int length = 0;
		try {
		    InputStream stream = new FileInputStream(file);
		    for(;;)
		    {
		    	byte[] bts = null;
				if(b_online)
				{
					bts = new byte[2560];
				}
				else
				{
					bts = new byte[64000];  //发包率需要小于总音频大小
				}
				
		    	length = stream.read(bts);
		    	if (length <= 0)
		    	{
		    		last_audio_len.put(path, last_length);
		    		break;
		    	}
		    	last_length = length;
		    	//System.out.println(length);
		    	buffers.add(bts);
		    }
		  
		    if (buffers.size() == 1)
		    {
		    	byte[] bts = new byte[1];
		    	buffers.add(bts);
		    }
		    
		    stream.close(); 
		    audio_map.put(path, buffers);
		    
		} catch (Exception e) { 
		    e.printStackTrace();
		}
		return ; 
		
	}
    
    public static void record_iat() {
    
    }

 public static DataObject local_iat(String path,DataObject outMessage) throws IOException {
//    	String resultMsg="";
    	//定义一个标识，让我知道识别是有结果的
//    	boolean flag=false;
		int[] errorCode = new int[1];  
		session_id = mt.SCYMTSessionBeginEx(ssbparam, errorCode,null);

	     outMessage.set("returnCode", "999999");
	     outMessage.set("returnMsg","语音识别失败");
		
		if(errorCode[0] != 0){
		String error = "请检查IP地址是否正确、网络是否正常开启,错误码是" + errorCode[0];
//		     System.out.println(error);
		     logger.info(error+"222222222");
//		     resultMsg=error;
		     outMessage.set("returnCode", "999999");
		     outMessage.set("returnMsg",error);
		     return outMessage;
		   // System.exit(0);
		}
		
		
		// 音频路径，此时使用相对路径，可根据实际情况自己修改 
		readFile(path);
		ArrayList<byte[]> buffers = audio_map.get(path);
		for(int i=0; i< buffers.size(); i++){
		     byte[] wave = buffers.get(i); 
		     int len = wave.length;
		     // 音频缓存区位置信息
		     int audioStatus;
		     if(i == 0){ // 第一段音频
		           audioStatus = 1;
		     } else if(i == buffers.size()-1){   // 最后一段音频
		           audioStatus = 4;
		           len = last_audio_len.get(path);
		     } else {// 中间音频
		           audioStatus = 2;
		     }
		     int[] epStatus = new int[1];
		     int[] recogStatus = new int[1];
			 int[] ret = new int[1];
		     //写入音频
		     String cur_res = mt.SCYMTAudioWriteEx(session_id, wave, len, audioStatus, epStatus, recogStatus, ret, null);
		     
		     if (ret[0] != 0) {
		           String err = "上传音频出错，错误码为：" + ret[0];
		           logger.info(err);
//		           System.out.println(err);
//		           resultMsg=err;
//		           return resultMsg;
		           outMessage.set("returnCode", "999999");
				   outMessage.set("returnMsg",err);
				   return outMessage;
		         //  System.exit(0);
		     }
			 		     
		     // 判断引擎返回pgs是否为1，为1表示有识别结果可获取
			 if (ret[0] == 0 && cur_res.length() != 0){
         		product = gson.fromJson(cur_res, Attribute.class);
         		//输出                	
             	if(product.getPgs() == 1)
             	{
                 	allResult = "" + product.getResult(); //获取识别结果
                 	if(product.getResult()!=null&&!"".equals(product.getResult())){
//                 		resultMsg=allResult;
//                 		flag=true;
         	           outMessage.set("returnCode", "000000");
    				   outMessage.set("returnMsg",allResult);
//    				   return outMessage;
//           		        return resultMsg;
                 	}
             	}
			 }
			 	 
			 if(recogStatus[0] == 5)
			 {
				 break;   //识别结束
			 }
			 		 
		     if( i == buffers.size()-1){      	 
		           int[] errCode = new int[1];
		           int timeout = 5;
		           BufferedWriter writer = null;
				   if(!b_online)
				   {
				       writer = new BufferedWriter(new FileWriter(new File("./offline_result.txt"),true));
				       writer.write(path + "----\n");
				   }
		           
		           while(recogStatus[0] != 5 && 0 == errCode[0]){
		                 String cur_result = mt.SCYMTGetResultEx(session_id, recogStatus, timeout, errCode, null);
		                 if(cur_result != null && cur_result.length() != 0){
		         			if(b_online)
		        			{
			                	   product = gson.fromJson(cur_result, Attribute.class);
			                		//输出                	
			                    	if(product.getPgs() == 1)
			                    	{
			                        	allResult = "" + product.getResult();  //获取识别结果
			                        	if(product.getResult()!=null&&!"".equals(product.getResult())){
//			                         		resultMsg=allResult;
//			                         		flag=true;
			                  	           outMessage.set("returnCode", "000000");
			            				   outMessage.set("returnMsg",allResult);
//			            				   return outMessage;
//			                   		        return resultMsg;
			                         	}
			                    	}
		        			}
		         			else
		         			{
		         				writer.write(cur_result);
//		         				System.out.println(cur_result);
		         			}
		                	       
		                 }  
		            }
				   if(!b_online)
				   {
					   writer.close();
				   }
		     }

			if(b_online)
			{
				try {
					  Thread.sleep(60);
				    } catch (Exception e) {
				         e.printStackTrace();
				 }
			}
		     

		           
		  }
		 
		// 结束一路会话
		int endret = mt.SCYMTSessionEndEx(session_id);
		if(endret != 0){
		    String error = "会话关闭失败,错误码是" + endret;
//		    System.out.println(error);
		    logger.info(error);
//		    resultMsg=error;
	        outMessage.set("returnCode", "999999");
			outMessage.set("returnMsg",error);
			return outMessage;
//		    return resultMsg;
		  //  System.exit(0);
		}
		
		try {
			  Thread.sleep(1500);
	     } catch (Exception e) {
	          e.printStackTrace();
	    }
	     return outMessage;
//        if(flag){
//        	return resultMsg;
//        }
//        else{
//        	return "识别出错或未识别出";
//        }
    }
    
    
    private  ArrayList<String> readFileOnLine(String filePath){//输入文件路径
    	
    	 
    	File file = new File(filePath);
    			if (!file.exists()) {
//    			System.out.println("file open failed");
    			logger.info("file open failed");
    			}
    	
    	ArrayList<String> list = new ArrayList<String>();
   
    	 try{
    	   FileInputStream fis =new FileInputStream(file);
    	   //FileInputStream fis = openFileInput(filePath);//打开文件输入流
    	   
    	   //StringBuffer sBuffer = new StringBuffer();
    	   DataInputStream dataIO = new DataInputStream(fis);//读取文件数据流
    	   String strLine = null;
    	   while((strLine =  dataIO.readLine()) != null) {//通过readline按行读取
    		  StringBuffer sBuffer = new StringBuffer();
    	      sBuffer.append(strLine);//strLine就是一行的内容
    	      String s = new String(sBuffer.toString().getBytes(), "UTF-8");
    	      //System.out.println("s = " + s);
    	      list.add(s);
    	   }
    	  dataIO.close();
    	  fis.close();
    	  	
    	 } catch (Exception e) { 	    
    		 e.printStackTrace();
    	 } 
	
    	  return list;
    	}
    
	public byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);

		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
//			System.out.println("file too big...");
			logger.info("file too big...");
			return null;
		}

		FileInputStream fi = new FileInputStream(file);

		byte[] buffer = new byte[(int) fileSize];

		int offset = 0;

		int numRead = 0;

		while (offset < buffer.length

		&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {

			offset += numRead;

		}

		// 确保所有数据均被读取

		if (offset != buffer.length) {

			throw new IOException("Could not completely read file "
					+ file.getName());

		}

		fi.close();

		return buffer;
	}
	
	public DataObject start(String[] args,String filePath,DataObject outMessage) throws IOException{
		logger.info("进入了科大讯飞语音识别iat");
		
		  //定义输入ip端口
		  String inputIp=VprsConstant.KEDA_IAT_IP;
		  String result="";		  
		  String inputRecord="2";
		  
		  
			// TODO Auto-generated method stub
			allResult = "识别结果为:  ";
			
			mt = new mt_scylla();
			// 初始化语音识别引擎
			int initret = mt.SCYMTInitializeEx(null);
			
			String parL = "appid=pc20onli,sn=c" + ",url="+ inputIp;
			mt.SCYMTAuthLogin(parL, null);
			
			if(initret != 0){
			     String error = "请检查IP地址是否正确、网络是否正常开启,错误码是" + initret;
//			     System.out.println(error);
			     logger.info(error+"====");
			     System.exit(0);
			}
			
			//--------压缩、解压音频开始-------------
			boolean b_decode = false;
			if(b_decode)
			{
				int errc[] = new int[1];
				String sid_audio = mt.SCYMTAudioCreate("aue=speex-wb", errc, null);
				
				byte[] bts1 = null;
				try {
					bts1 = getContent("audio_data.pcm");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(bts1 == null)
				{
//					return;
					outMessage.set("returnCode", "999999");
					outMessage.set("returnMsg","压缩、解压音频过程出错");
					return outMessage;
				}
				
				int[] epStatus = new int[1];
				int[] ret1 = new int[1];
				int[] datalen1 = new int[1];
	            byte[] data1 = mt.SCYMTAudioHandel(sid_audio, bts1, bts1.length,
						 epStatus, ret1,datalen1);
				
				 FileOutputStream fos1 = null;
				 fos1 = new FileOutputStream("./audio_data_encode.pcm");
				try {
					fos1.write(data1);   					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fos1.close();
	            
				
				mt.SCYMTAudioDestory(sid_audio);
			}

			//--------解压音频结束-------------
			
			//--------------------个性化上传、下载--
			boolean b_pers = false;
			if(b_pers)
			{
				String lparam ="appid=pc20onli" + ",url="+ inputIp;
				int rett = mt.SCYMTPersLogin(lparam,null);
				
				//上传
				byte[] bts = null;
				try {
					bts = getContent("data.doc");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(bts == null)
				{
//					return;
					outMessage.set("returnCode", "999999");
					outMessage.set("returnMsg","上传过程出错");
					return outMessage;
				}
				
				String uparam ="res_type=1,subcmd=upload,appid=pc20onli" + ",url="+ inputIp;
				int uret = mt.SCYMTUploadData(uparam, bts, bts.length, null); 
				
				
				//下载
				String dparam ="res_type=0,subcmd=download,appid=pc20onli" + ",url="+ inputIp;
				int[] ret = new int[1];
				int[] datalen = new int[1];
				byte[] data = mt.SCYMTDownloadData(dparam, datalen, ret, null);
				

				if(datalen[0] > 0){
					FileOutputStream fos;
					try {
						fos = new FileOutputStream("download_data.bin");

						fos.write(data);
						fos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}
//			b_online = false;
			if(b_online)
			{
//				ssbparam = "svc=iat,auf=audio/L16;rate=16000,aue=raw,type=1,uid=660Y5r,appid=pc20onli,url=" + inputIp; ===修改处
				ssbparam = "svc=iat,auf=audio/L16;rate="+VprsConstant.KEDA_AUDIO_RATE+",aue=raw,type=1,uid=660Y5r,appid=pc20onli,url=" + inputIp;
			}
			else
			{
				//离线识别
				ssbparam = "svc=iat,auf=audio/L16;rate=8000,aue=raw,type=1,uid=660Y5r,appid=pc20onli,url=" + inputIp +"," +"extend_params={\"params\":\"seginfo=1,vspp=1,online=off\"}";
			}
			
			//识别后翻译
			//ssbparam = "url=" + inputIp + ",svc=src,appid=pc20onli,scenes=iat;itr,scenes_params={\"iat\":\"svc=iat,auf=audio/L16;rate=16000,aue=raw,type=1,uid=660Y5r\"|\"itr\":\"type=cnug\"}";

			// 启动一路识别
							
			if(inputRecord.equals("1"))
			{
				record_iat();       //录音识别
			}
			else if(inputRecord.equals("2"))
			{				  
				String count="1";				
				int loop_count = 1;       //本地识别循环次数
				try {
				    loop_count = Integer.valueOf(count).intValue();
				} catch (NumberFormatException e) {
					String error = "输入错误：" + count;
//				    System.out.println(error);
				    logger.info(error);
				    System.exit(0);
				    e.printStackTrace();
				}
				  
					outMessage=local_iat(filePath,outMessage);
				
			}
			
			
			// 逆初始化
			int uniret = mt.SCYMTUninitializeEx(null);
			if(uniret != 0){
			    String error = "逆初始化失败,错误码是" + uniret;
			    logger.info(error);
			  //  System.exit(0);
			}

            return outMessage;
	}
}
