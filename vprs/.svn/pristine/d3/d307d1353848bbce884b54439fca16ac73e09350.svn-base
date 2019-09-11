/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.proxy.Invoker;
import com.pccc.touda.proxy.api.Result;
import com.pccc.vprs.servicecustom.common.ReturnCode;
import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicecustom.transflow.TransDetailModel;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicedisplay.bams.util.TimeUtils;
import com.pccc.vprs.servicedisplay.vprs.audio.AudioDetect;
import com.pccc.vprs.servicedisplay.vprs.audio.service.audioService;
import com.pccc.vprs.servicedisplay.vprs.common.AbstractService;
import com.pccc.vprs.servicedisplay.vprs.common.Constants;
import com.pccc.vprs.servicedisplay.vprs.model.UserInfo;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.engine.core.impl.Const;

import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-21 15:45:35
 * 
 */
@Bizlet("基立迅声纹识别核心算法引擎接口")
public class JiLiXunService extends AbstractService implements audioService {

	private static ILogger logger = LoggerFactory
			.getLogger(JiLiXunService.class);

	static String VPUrl;

	static String input1 = "", input2 = "", input3 = "", input4 = "",
			input5 = "", input6 = "", input7 = "", input8 = "", input9 = "",
			input10 = "", input11 = "", input12 = "", input13 = "",
			input14 = "", input15 = "", input16 = "";

	/**
	 * 固定文本注册
	 */
	public DataObject enrolSpeaker(DataObject inMessage, DataObject outMessage) {
		// 首先获取参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 传递的是音频的url
		String closeAudioFormat=inMessage.getString("closeAudioFormat");

		// 根据四要素查询注册明细流水表的信息    ===修改为5要素，加上渠道 ，然后根据时间先后顺序，选出排名前四的四条记录
		TransDetailModel transDetail = new TransDetailModel();
		transDetail.setAudioType("TD");
		transDetail.setCertNo(certNo);
		transDetail.setCertType(certType);
		transDetail.setUserName(userName);
		transDetail.setChannel(channel);
		

		String queryRegisterDetailSql = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryRegisterTable";
		
		String closeAudioZuZhuang="";
		
		if(closeAudioFormat.equals("base64")){
			String separator = File.separator;
			//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
			//项目中音频的地址  符合/td/register/date/channel/file
			String audioWebPathIn = VPRSUtils.getTime("yyyyMMdd") + separator
					+ channel + separator + VPRSUtils.getUUID() + ".wav";
			String audioLocalPath = VprsConstant.JILIXUN_SHARE_PATH_REGISTER_TD + separator
					+ audioWebPathIn;

			try {
				AudioFileUtils.convertBase64DataToAudio(closeAudio,
						audioLocalPath);
//				inMessage.set("closeAudio", audioLocalPath);

//				//拼接参数传递
//				closeAudio = VprsConstant.AUDIO_IP_ADDRESS
//						+ audioWebPathIn;
				
				/*当base64情况下，使用共享盘的形式时，则AUDIO_IP_ADDRESS为空，传递的应是共享盘的地址，否则还是需要将其封装的*/
				if(StringUtils.isBlank(VprsConstant.AUDIO_IP_ADDRESS_REGISTER_TD)){
					closeAudio=audioLocalPath;
				}else{			
				//音频访问的url地址
				closeAudio = VprsConstant.AUDIO_IP_ADDRESS_REGISTER_TD+separator
						+ audioWebPathIn;
				}
				
				//将base64字符串转化为文件后，将文件存放的路径给请求报文，以便存到数据库字段中
				inMessage.set("closeAudio", audioLocalPath);
				
				
				
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logger.info("存储音频文件到文件服务器出错");
			}
			
			
            //用于拼接多个音频路径
			closeAudioZuZhuang = closeAudio + "";
			try {
	            //查询出前四条记录
				Object[] objs = DatabaseExt.queryByNamedSql("default",
						queryRegisterDetailSql, transDetail);
				if (objs != null && objs.length > 0) {
					for (int i = 0; i < objs.length; i++) {
						transDetail = (TransDetailModel) objs[i];
						//将里面的结果拆分一下
						String path="";
						String[] aa={};
						if(closeAudioFormat.equals("base64")){
							String ss=transDetail.getCloseAudio();
//							String separator = File.separator;
							try{
						    //是环境部署决定
							/** 本地 */
//							aa=ss.split("\\\\");
							/** linux*/
							aa=ss.split("/");
							}catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								logger.info(e.getMessage());
							}
						
							int length=aa.length;
							if(!StringUtils.isBlank(VprsConstant.AUDIO_IP_ADDRESS_REGISTER_TD)){
							path=VprsConstant.AUDIO_IP_ADDRESS_REGISTER_TD+"/"+aa[length-3]+"/"+aa[length-2]+"/"+aa[length-1];
							}else{
								path=ss;
							}
							transDetail.setCloseAudio(path);
						}
						closeAudioZuZhuang = closeAudioZuZhuang + "#"
								+ transDetail.getCloseAudio();
					}
				} else {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "请输入更多音频");
					outMessage.set("registerStatus", "1");
					return outMessage;
				}
			} catch (Exception e) {
				logger.error("根据四要素查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
				throw new BTPRuntimeException("根据四要素查询注册明细流水表数据库操作异常，异常信息:"
						+ e.getMessage(), e);
			}
			
			
			
			
			
		}
		
		
        //当传递方式为url时（前提是该地址文件能够访问）
		if(closeAudioFormat.equals("url")){
			String directory=AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			closeAudio=directory;
		
            //用于拼接多个音频路径
			closeAudioZuZhuang = closeAudio + "";
			try {
				// 查询出前四条记录
				Object[] objs = DatabaseExt.queryByNamedSql("default",
						queryRegisterDetailSql, transDetail);
				if (objs != null && objs.length > 0) {
					for (int i = 0; i < objs.length; i++) {
						transDetail = (TransDetailModel) objs[i];
						closeAudioZuZhuang = closeAudioZuZhuang + "#"
								+ transDetail.getCloseAudio();

					}
				}else {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "请输入更多音频");
					outMessage.set("registerStatus", "1");
					return outMessage;
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("根据四要素查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
				throw new BTPRuntimeException("根据四要素查询注册明细流水表数据库操作异常，异常信息:"
						+ e.getMessage(), e);
			}
			
		}
		
		
		//当传递方式为path时
		if(closeAudioFormat.equals("path")){
			String directory=AudioFileUtils.crtTdRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio,directory);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			closeAudio=directory;
		
            //用于拼接多个音频路径
			closeAudioZuZhuang = closeAudio + "";
			try {
				// 查询出前四条记录
				Object[] objs = DatabaseExt.queryByNamedSql("default",
						queryRegisterDetailSql, transDetail);
				if (objs != null && objs.length > 0) {
					for (int i = 0; i < objs.length; i++) {
						transDetail = (TransDetailModel) objs[i];
						closeAudioZuZhuang = closeAudioZuZhuang + "#"
								+ transDetail.getCloseAudio();

					}
				}else {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "请输入更多音频");
					outMessage.set("registerStatus", "1");
					return outMessage;
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("根据四要素查询注册明细流水表数据库操作异常，异常信息:" + e.getMessage(), e);
				throw new BTPRuntimeException("根据四要素查询注册明细流水表数据库操作异常，异常信息:"
						+ e.getMessage(), e);
			}
			
		}
		
		
		
		

		// 获取注册时音频的文件个数
		String[] eachCloseAudio = closeAudioZuZhuang.split("#");
		String stepID = eachCloseAudio.length + "";

		// 目标url
		VPUrl = VprsConstant.JILIXUN_TD;
		// 注册请求号为200
		input1 = Constants.INPUT1_REGISTER;
		// 声纹的唯一标识 取证件类型加证件号吧 与自由说声纹标识不应相同
		input2 = certType + certNo;
		// 主叫号码 这里暂时用证件号吧
		input3 = certNo;
		// 是否为注册号码的标识
		input4 = "1";
		// 卡号 这里暂时也用证件号吧
		input5 = "certNo";
		// 会话号且唯一,这里设置为时间戳
		input6 = TimeUtils.generateTimestamp();
		// 操作步号
		input7 = stepID;
		// 通道标识 这边应该是约定好的，譬如PDA就是108（pad）
		input8 = Constants.INPUT8_IVR;
		// 音频文件路径绝对路径
		input9 = closeAudioZuZhuang;
		// 应用约定
		input10 = Constants.INPUT10;
		try {
			URL url = new URL(VPUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter out = new OutputStreamWriter(urlConnection
					.getOutputStream());
			out.write("input1=" + URLEncoder.encode(input1, "utf-8")
					+ "&input2=" + URLEncoder.encode(input2, "utf-8")
					+ "&input3=" + URLEncoder.encode(input3, "utf-8")
					+ "&input4=" + URLEncoder.encode(input4, "utf-8")
					+ "&input5=" + URLEncoder.encode(input5, "utf-8")
					+ "&input6=" + URLEncoder.encode(input6, "utf-8")
					+ "&input7=" + URLEncoder.encode(input7, "utf-8")
					+ "&input8=" + URLEncoder.encode(input8, "utf-8")
					+ "&input9=" + URLEncoder.encode(input9, "utf-8")
					+ "&input10=" + URLEncoder.encode(input10, "utf-8")
					+ "&input11=" + URLEncoder.encode(input11, "utf-8")
					+ "&input12=" + URLEncoder.encode(input12, "utf-8")
					+ "&input13=" + URLEncoder.encode(input13, "utf-8")
					+ "&input14=" + URLEncoder.encode(input14, "utf-8")
					+ "&input15=" + URLEncoder.encode(input15, "utf-8")
					+ "&input16=" + URLEncoder.encode(input16, "utf-8"));
			out.flush();
			out.close();

			InputStream inputStream = urlConnection.getInputStream();

			byte[] resultBytes = new byte[urlConnection.getContentLength()];
			inputStream.read(resultBytes, 0, urlConnection.getContentLength());

			String resultStr = new String(resultBytes, "UTF-8");
			resultStr = resultStr.substring(resultStr
					.indexOf("<string xmlns=\"http://tempuri.org/\">")
					+ "<string xmlns=\"http://tempuri.org/\">".length());
			resultStr = resultStr.substring(0, resultStr.indexOf("</string>"));

            logger.info(resultStr);
      //      System.out.println(resultStr);
			// 这边根据resultStr返回判断接下里的步骤，若成功，清除临时表的数据，并往注册用户信息表添加用户表示声纹注册成功
			String result[] = resultStr.split("\\|");
			for (int i = 0; i < result.length; i++) {
//	               System.out.println(result[i]);
	               logger.info(result[i]);
			}
			if (result[0].equals("001")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "声纹服务调用失败");
			} else if (result[0].equals("000")
					&& result[1].equals("NoEnoughAudio")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "请输入更多音频");
				outMessage.set("registerStatus", "1");
			} else if (result[0].equals("000") && result[1].equals("success")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "固定文本注册成功");
				// 注册成功表示模型有了，没模型都表示模型正在注册中
				outMessage.set("registerStatus", "0");
			} else {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", result[1]);
			}
		} catch (IOException e) {
			// Logger.getLogger(RequestTest.class.getName()).log(Level.SEVERE,
			// null, e);
			logger.info(e.getMessage());
			outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
			outMessage.set("returnMsg", "声纹声纹服务调用异常");
			e.printStackTrace();
		}


		return outMessage;
	}



	/**
	 * 随意说注册
	 */
	public DataObject enrolSpeakerIndependent(DataObject inMessage,
			DataObject outMessage) {
		// 首先获取参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio");         // 传递的是音频的url
		String closeAudioFormat = inMessage.getString("closeAudioFormat");

		//当传递形式为base64时，做相应处理，将 base64 字符串解析存储到服务器下发布项目目录下，然后将文件的url地址传递给closeAudio
		if (closeAudioFormat.equals("base64")) {
			String separator = File.separator;
			//音频在发布项目子目录下的地址  符合/td/register/date/channel/file
			String audioWebPathIn = VPRSUtils.getTime("yyyyMMdd") + separator
			+ channel + separator + VPRSUtils.getUUID() + ".wav";
			//linux中音频的地址
			String audioLocalPath = VprsConstant.JILIXUN_SHARE_PATH_REGISTER_FS + separator
			+ audioWebPathIn;
			//此处将转化后的音频地址赋给inMessage
			try {
				AudioFileUtils.convertBase64DataToAudio(closeAudio,
						audioLocalPath);
				
				
				/*当base64情况下，使用共享盘的形式时，则AUDIO_IP_ADDRESS为空，传递的应是共享盘的地址，否则还是需要将其封装的*/
				if(StringUtils.isBlank(VprsConstant.AUDIO_IP_ADDRESS_REGISTER_FS)){
					closeAudio=audioLocalPath;
				}else{			
				//音频访问的url地址
				closeAudio = VprsConstant.AUDIO_IP_ADDRESS_REGISTER_FS+ separator
						+ audioWebPathIn;
				}
				
				//将base64字符串转化为文件后，将文件存放的路径给请求报文，以便存到数据库字段中
				inMessage.set("closeAudio", audioLocalPath);
				
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			}
		}
		
		//当传递方式为url时（前提是该地址文件能够访问）
		if(closeAudioFormat.equals("url")){
			String directory=AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			
		}
		
		//当传递方式为path时
		if(closeAudioFormat.equals("path")){
			String directory=AudioFileUtils.crtFsRegisterAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			
		}
		

       
		
		// 目标url
		VPUrl = VprsConstant.JILIXUN_FS;
		// 注册请求号为200
		input1 = Constants.INPUT1_REGISTER;
		// 声纹的唯一标识 取证件类型加证件号吧 （同一用户的随意说标识应与固定文本标识不同）
		input2 = userName + certType + certNo;
		// 主叫号码 这里暂时用证件号吧
		input3 = certNo;
		// 是否为注册号码的标识
		input4 = "1";
		// 卡号 这里暂时也用证件号吧
		input5 = "certNo";
		// 会话号且唯一,这里设置为时间戳
		input6 = TimeUtils.generateTimestamp();
		// 操作步号
		input7 = "1";
		// 通道标识 这边应该是约定好的，譬如PDA就是108（pad）
		input8 = Constants.INPUT8_IVR;
		// 音频文件路径绝对路径
		input9 = closeAudio;
		// 应用约定
		input10 = Constants.INPUT10;
		try {
			URL url = new URL(VPUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter out = new OutputStreamWriter(urlConnection
					.getOutputStream());
			out.write("input1=" + URLEncoder.encode(input1, "utf-8")
					+ "&input2=" + URLEncoder.encode(input2, "utf-8")
					+ "&input3=" + URLEncoder.encode(input3, "utf-8")
					+ "&input4=" + URLEncoder.encode(input4, "utf-8")
					+ "&input5=" + URLEncoder.encode(input5, "utf-8")
					+ "&input6=" + URLEncoder.encode(input6, "utf-8")
					+ "&input7=" + URLEncoder.encode(input7, "utf-8")
					+ "&input8=" + URLEncoder.encode(input8, "utf-8")
					+ "&input9=" + URLEncoder.encode(input9, "utf-8")
					+ "&input10=" + URLEncoder.encode(input10, "utf-8")
					+ "&input11=" + URLEncoder.encode(input11, "utf-8")
					+ "&input12=" + URLEncoder.encode(input12, "utf-8")
					+ "&input13=" + URLEncoder.encode(input13, "utf-8")
					+ "&input14=" + URLEncoder.encode(input14, "utf-8")
					+ "&input15=" + URLEncoder.encode(input15, "utf-8")
					+ "&input16=" + URLEncoder.encode(input16, "utf-8"));
			out.flush();
			out.close();

			InputStream inputStream = urlConnection.getInputStream();

			byte[] resultBytes = new byte[urlConnection.getContentLength()];
			inputStream.read(resultBytes, 0, urlConnection.getContentLength());

			String resultStr = new String(resultBytes, "UTF-8");
			resultStr = resultStr.substring(resultStr
					.indexOf("<string xmlns=\"http://tempuri.org/\">")
					+ "<string xmlns=\"http://tempuri.org/\">".length());
			resultStr = resultStr.substring(0, resultStr.indexOf("</string>"));
			
			logger.info("随意说声纹返回值："+resultStr);

			// 这边根据resultStr返回判断接下里的步骤，若成功，往注册用户信息表添加用户表示声纹注册成功
			String result[] = resultStr.split("\\|");

			if (result[0].equals("001")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "声纹服务调用失败");
			} else if (result[0].equals("000") && result[1].equals("success")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "注册成功");
			} else {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", result[1]);
			}
		} catch (IOException e) {
			logger.info("声纹服务调用异常："+e.getMessage());
			outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
			outMessage.set("returnMsg", "声纹服务调用异常:"+e.getMessage());
		}

		return outMessage;
	}

	/**
	 * 固定文本验证
	 */
	public DataObject verifySpeaker(DataObject inMessage,
			DataObject outMessage, BigDecimal threshold) {
		// 首先获取参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 传递的是音频的url
		String closeAudioFormat = inMessage.getString("closeAudioFormat");

		if (closeAudioFormat.equals("base64")) {
			// 当传递形式为base64时，做相应处理，先留出来
			// 将 base64 字符串解析存储到指定服务器下，然后将文件的url地址传递给closeAudio

			String separator = File.separator;
			//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
			//项目中音频的地址  符合/td/register/date/channel/file
			String audioWebPathIn = VPRSUtils.getTime("yyyyMMdd") + separator
					+ channel + separator + VPRSUtils.getUUID() + ".wav";
			String audioLocalPath = VprsConstant.JILIXUN_SHARE_PATH_VERIFY_TD + separator
					+ audioWebPathIn;

			try {
				AudioFileUtils.convertBase64DataToAudio(closeAudio,
						audioLocalPath);
//				//拼接参数传递
//				closeAudio = VprsConstant.AUDIO_IP_ADDRESS
//						+ audioWebPathIn;
				
				/*当base64情况下，使用共享盘的形式时，则AUDIO_IP_ADDRESS为空，传递的应是共享盘的地址，否则还是需要将其封装的*/
				if(StringUtils.isBlank(VprsConstant.AUDIO_IP_ADDRESS_VERIFY_TD)){
					closeAudio=audioLocalPath;
				}else{			
				//音频访问的url地址
				closeAudio = VprsConstant.AUDIO_IP_ADDRESS_VERIFY_TD+separator
						+ audioWebPathIn;
				}
				
				//将base64字符串转化为文件后，将文件存放的路径给请求报文，以便存到数据库字段中(只要存放验证流水表，信息表不需要)
				inMessage.set("closeAudio", audioLocalPath);
				
				
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logger.info(e.getMessage());
			}

		}

		
         //当传递方式为url时（前提是该地址文件能够访问）
		if(closeAudioFormat.equals("url")){
			String directory=AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;	
			}
			//将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
		}
		
		
        //当传递方式为path时
		if(closeAudioFormat.equals("path")){
			String directory=AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			
		}
		
		
			// 目标url
			VPUrl = VprsConstant.JILIXUN_TD;
			// 验证请求号为300
			input1 = Constants.INPUT1_VERIFY;
			// 声纹的唯一标识 取证件类型加证件号吧
			input2 = certType + certNo;
			// 主叫号码 这里暂时用证件号吧
			input3 = certNo;
			// 是否为注册号码的标识
			input4 = "1";
			// 卡号 这里暂时也用证件号吧
			input5 = "certNo";
			// 会话号且唯一,这里设置为时间戳
			input6 = TimeUtils.generateTimestamp();
			// 操作步号
			input7 = "1";
			// 通道标识 这边应该是约定好的，譬如PDA就是108（pad）,IVR位101
			input8 = Constants.INPUT8_IVR;
			// 音频文件路径绝对路径
			input9 = closeAudio;
			// 应用约定
			input10 = Constants.INPUT10;

			try {
				URL url = new URL(VPUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.setRequestProperty("content-type",
						"application/x-www-form-urlencoded");
				OutputStreamWriter out = new OutputStreamWriter(urlConnection
						.getOutputStream());
				out.write("input1=" + URLEncoder.encode(input1, "utf-8")
						+ "&input2=" + URLEncoder.encode(input2, "utf-8")
						+ "&input3=" + URLEncoder.encode(input3, "utf-8")
						+ "&input4=" + URLEncoder.encode(input4, "utf-8")
						+ "&input5=" + URLEncoder.encode(input5, "utf-8")
						+ "&input6=" + URLEncoder.encode(input6, "utf-8")
						+ "&input7=" + URLEncoder.encode(input7, "utf-8")
						+ "&input8=" + URLEncoder.encode(input8, "utf-8")
						+ "&input9=" + URLEncoder.encode(input9, "utf-8")
						+ "&input10=" + URLEncoder.encode(input10, "utf-8")
						+ "&input11=" + URLEncoder.encode(input11, "utf-8")
						+ "&input12=" + URLEncoder.encode(input12, "utf-8")
						+ "&input13=" + URLEncoder.encode(input13, "utf-8")
						+ "&input14=" + URLEncoder.encode(input14, "utf-8")
						+ "&input15=" + URLEncoder.encode(input15, "utf-8")
						+ "&input16=" + URLEncoder.encode(input16, "utf-8"));
				out.flush();
				out.close();

				InputStream inputStream = urlConnection.getInputStream();

				byte[] resultBytes = new byte[urlConnection.getContentLength()];
				inputStream.read(resultBytes, 0, urlConnection
						.getContentLength());

				String resultStr = new String(resultBytes, "UTF-8");
				resultStr = resultStr.substring(resultStr
						.indexOf("<string xmlns=\"http://tempuri.org/\">")
						+ "<string xmlns=\"http://tempuri.org/\">".length());
				resultStr = resultStr.substring(0, resultStr
						.indexOf("</string>"));

                logger.info(resultStr);
				// 这边根据resultStr返回判断接下里的步骤，若成功，清除临时表的数据，并往注册用户信息表添加用户表示声纹注册成功
				String result[] = resultStr.split("\\|");


				// 为了满足调通接口，后期让基立迅提供分数
				if (result[0].equals("001")) {
					outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
					outMessage.set("returnMsg", "声纹服务调用失败");
				} else if (result[0].equals("000")
						&& result[1].equals("success")) {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "固定文本验证成功");
					outMessage.set("info/similarity", "");
					outMessage.set("info/threshold", "");
					outMessage.set("info/compareResult", "success");
				} else {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "固定文本验证不成功");
					outMessage.set("info/similarity", "");
					outMessage.set("info/threshold", "");
					outMessage.set("info/compareResult", "fail");
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
				outMessage.set("returnMsg", "声纹服务调用异常");
				e.printStackTrace();
			}

		
		return outMessage;
	}

	/**
	 * 随意说验证
	 */
	public DataObject verifySpeakerIndependent(DataObject inMessage,
			DataObject outMessage, BigDecimal threshold) {
		// 首先获取参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 传递的是音频的url
		String closeAudioFormat = inMessage.getString("closeAudioFormat"); // 传递地址形式

		if (closeAudioFormat.equals("base64")) {
			// 当传递形式为base64时，做相应处理，先留出来
			// 将 base64 字符串解析存储到指定服务器下，然后将文件的url地址传递给closeAudio

			String separator = File.separator;
			//项目中音频的地址   符合/td/register/date/channel/file
			String audioWebPathIn = VPRSUtils.getTime("yyyyMMdd") + separator
					+ channel + separator + VPRSUtils.getUUID() + ".wav";
			String audioLocalPath = VprsConstant.JILIXUN_SHARE_PATH_VERIFY_FS + separator
					+ audioWebPathIn;

			try {
				AudioFileUtils.convertBase64DataToAudio(closeAudio,
						audioLocalPath);
//				//拼接参数传递
//				closeAudio = VprsConstant.AUDIO_IP_ADDRESS
//						+ audioWebPathIn;
				
				/*当base64情况下，使用共享盘的形式时，则AUDIO_IP_ADDRESS为空，传递的应是共享盘的地址，否则还是需要将其封装的*/
				if(StringUtils.isBlank(VprsConstant.AUDIO_IP_ADDRESS_VERIFY_FS)){
					closeAudio=audioLocalPath;
				}else{			
				//音频访问的url地址
				closeAudio = VprsConstant.AUDIO_IP_ADDRESS_VERIFY_FS+separator
						+ audioWebPathIn;
				}
				
				//将base64字符串转化为文件后，将文件存放的路径给请求报文，以便存到数据库字段中(只要存放验证流水表，信息表不需要)
				inMessage.set("closeAudio", audioLocalPath);
				
				
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logger.info(e.getMessage());
			}

		}
		

//		当传递方式为url时（前提是该地址文件能够访问）
		if(closeAudioFormat.equals("url")){
			String directory=AudioFileUtils.crtFsVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.downLoadFromUrl(closeAudio, directory);
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
		}
		
//		当传递方式为path时
		if(closeAudioFormat.equals("path")){
			String directory=AudioFileUtils.crtTdVerifyAudioPathByUUID(VPRSUtils.getUUID(), channel);
			try {
				AudioFileUtils.copyToPath(closeAudio, directory);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "存储音频文件到文件服务器出错："+e.getMessage());
				return outMessage;
			
			}
			
            //将url下载后音频的存放位置给报文，以便后面存入数据库
			inMessage.set("closeAudio", directory);
			
		}
		
		
		
		
			// 目标url
			VPUrl = VprsConstant.JILIXUN_FS;
			// 验证请求号为300
			input1 = Constants.INPUT1_VERIFY;
			// 声纹的唯一标识 取证件类型加证件号吧
			input2 = userName + certType + certNo;
			// 主叫号码 这里暂时用证件号吧
			input3 = certNo;
			// 是否为注册号码的标识
			input4 = "1";
			// 卡号 这里暂时也用证件号吧
			input5 = "certNo";
			// 会话号且唯一,这里采用时间戳
			input6 = TimeUtils.generateTimestamp();
			// 操作步号
			input7 = "1";
			// 通道标识 这边应该是约定好的，譬如PDA就是108（pad）,IVR位101
			input8 = Constants.INPUT8_IVR;
			// 音频文件路径绝对路径
			input9 = closeAudio;
			// 应用约定
			input10 = Constants.INPUT10;

			try {
				URL url = new URL(VPUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.setRequestProperty("content-type",
						"application/x-www-form-urlencoded");
				OutputStreamWriter out = new OutputStreamWriter(urlConnection
						.getOutputStream());
				out.write("input1=" + URLEncoder.encode(input1, "utf-8")
						+ "&input2=" + URLEncoder.encode(input2, "utf-8")
						+ "&input3=" + URLEncoder.encode(input3, "utf-8")
						+ "&input4=" + URLEncoder.encode(input4, "utf-8")
						+ "&input5=" + URLEncoder.encode(input5, "utf-8")
						+ "&input6=" + URLEncoder.encode(input6, "utf-8")
						+ "&input7=" + URLEncoder.encode(input7, "utf-8")
						+ "&input8=" + URLEncoder.encode(input8, "utf-8")
						+ "&input9=" + URLEncoder.encode(input9, "utf-8")
						+ "&input10=" + URLEncoder.encode(input10, "utf-8")
						+ "&input11=" + URLEncoder.encode(input11, "utf-8")
						+ "&input12=" + URLEncoder.encode(input12, "utf-8")
						+ "&input13=" + URLEncoder.encode(input13, "utf-8")
						+ "&input14=" + URLEncoder.encode(input14, "utf-8")
						+ "&input15=" + URLEncoder.encode(input15, "utf-8")
						+ "&input16=" + URLEncoder.encode(input16, "utf-8"));
				out.flush();
				out.close();

				InputStream inputStream = urlConnection.getInputStream();

				byte[] resultBytes = new byte[urlConnection.getContentLength()];
				inputStream.read(resultBytes, 0, urlConnection
						.getContentLength());

				String resultStr = new String(resultBytes, "UTF-8");
				resultStr = resultStr.substring(resultStr
						.indexOf("<string xmlns=\"http://tempuri.org/\">")
						+ "<string xmlns=\"http://tempuri.org/\">".length());
				resultStr = resultStr.substring(0, resultStr
						.indexOf("</string>"));

                 logger.info(resultStr);
				// 这边根据resultStr返回判断接下里的步骤，若成功，清除临时表的数据，并往注册用户信息表添加用户表示声纹注册成功
				String result[] = resultStr.split("\\|");
				if (result[0].equals("001")) {
					outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
					outMessage.set("returnMsg", "声纹服务调用失败");
				}
				if (result[0].equals("000") && result[1].equals("success")) {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "随意说验证成功");
					outMessage.set("info/similarity", "");
					outMessage.set("info/threshold", "");
					outMessage.set("info/compareResult", "success");
				} else {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "随意说验证不成功");
					outMessage.set("info/similarity", "");
					outMessage.set("info/threshold", "");
					outMessage.set("info/compareResult", "fail");
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
				outMessage.set("returnMsg", "服务调用异常");
			}

		
		return outMessage;
	}

	public DataObject updateSpeaker(DataObject inMessage, DataObject outMessage) {
		return outMessage;
	}

	public DataObject updateSpeakerIndependent(DataObject inMessage,
			DataObject outMessage) {
		return outMessage;
	}

	public DataObject compareSpeaker(DataObject inMessage, DataObject outMessage) {
		return outMessage;
	}

	/**
	 * 声纹注册查询 根据audioType设置地址及约定的参数
	 */
	public DataObject isExistSpeaker(DataObject inMessage, DataObject outMessage) {
		// 首先获取参数
		String userName = inMessage.getString("userName");
		String userType = inMessage.getString("userType");
		String certType = inMessage.getString("certType");
		String certNo = inMessage.getString("certNo");
		String userUniqueId = inMessage.getString("userUniqueId");
		String channel = inMessage.getString("channel");
		String businessType = inMessage.getString("businessType");
		String closeAudio = inMessage.getString("closeAudio"); // 传递的是音频的url
		String audioType = inMessage.getString("audioType");

		if (audioType.equals("TD")) {
			// 目标url
			VPUrl = "http://182.180.116.161/vpws_jhtest/Service.asmx/CallVP";
			// 声纹的唯一标识 取证件类型加证件号吧 与自由说声纹标识不应相同
			input2 = certType + certNo;
		}
		if (audioType.equals("FS")) {
			// 目标url
			VPUrl = "http://182.180.116.161/fsws/Service.asmx/CallWS";
			// 声纹的唯一标识 取证件类型加证件号吧 与自由说声纹标识不应相同
			input2 = userName + certType + certNo;
		}
		// 声纹注册查询为101
		input1 = "101";

		// 会话号且唯一,这里设置为时间戳
		input6 = TimeUtils.generateTimestamp();

		// 通道标识 这边应该是约定好的，譬如PDA就是108（pad）
		input8 = "101";

		// 应用约定
		input10 = "102";
		try {
			URL url = new URL(VPUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter out = new OutputStreamWriter(urlConnection
					.getOutputStream());
			out.write("input1=" + URLEncoder.encode(input1, "utf-8")
					+ "&input2=" + URLEncoder.encode(input2, "utf-8")
					+ "&input3=" + URLEncoder.encode(input3, "utf-8")
					+ "&input4=" + URLEncoder.encode(input4, "utf-8")
					+ "&input5=" + URLEncoder.encode(input5, "utf-8")
					+ "&input6=" + URLEncoder.encode(input6, "utf-8")
					+ "&input7=" + URLEncoder.encode(input7, "utf-8")
					+ "&input8=" + URLEncoder.encode(input8, "utf-8")
					+ "&input9=" + URLEncoder.encode(input9, "utf-8")
					+ "&input10=" + URLEncoder.encode(input10, "utf-8")
					+ "&input11=" + URLEncoder.encode(input11, "utf-8")
					+ "&input12=" + URLEncoder.encode(input12, "utf-8")
					+ "&input13=" + URLEncoder.encode(input13, "utf-8")
					+ "&input14=" + URLEncoder.encode(input14, "utf-8")
					+ "&input15=" + URLEncoder.encode(input15, "utf-8")
					+ "&input16=" + URLEncoder.encode(input16, "utf-8"));
			out.flush();
			out.close();

			InputStream inputStream = urlConnection.getInputStream();
			byte[] resultBytes = new byte[urlConnection.getContentLength()];
			inputStream.read(resultBytes, 0, urlConnection.getContentLength());

			String resultStr = new String(resultBytes, "UTF-8");
			resultStr = resultStr.substring(resultStr
					.indexOf("<string xmlns=\"http://tempuri.org/\">")
					+ "<string xmlns=\"http://tempuri.org/\">".length());
			resultStr = resultStr.substring(0, resultStr.indexOf("</string>"));

            logger.info(resultStr);
			// 这边根据resultStr返回判断接下里的步骤，若成功，清除临时表的数据，并往注册用户信息表添加用户表示声纹注册成功
			String result[] = resultStr.split("\\|");
			if (result[0].equals("000") && result[1].equals("success")
					&& result[4].equals("QueryTrue")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "用户已开通声纹认证");
			} else if (result[0].equals("000") && result[1].equals("success")
					&& result[4].equals("QueryFalse")) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "用户没有声纹");
			} else {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", result[4]);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			outMessage.set("returnCode", ReturnCode.NETWORK_COMMUNICATION_EXCEPTION);
			outMessage.set("returnMsg", "服务调用异常");
		}

		return outMessage;
	}



	@Override
	public DataObject verifySpeakerIndependentCQVoice(DataObject inMessage, DataObject outMessage) {
		// TODO 自动生成的方法存根
		return null;
	}



	public DataObject deleteSpeakerIndependent(DataObject inMessage, DataObject outMessage) {
		// TODO 自动生成的方法存根
		return null;
	}
}
