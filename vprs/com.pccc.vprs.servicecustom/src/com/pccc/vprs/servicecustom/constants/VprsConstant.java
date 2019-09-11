package com.pccc.vprs.servicecustom.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.eos.data.datacontext.DataContextManager;
import com.pccc.touda.common.util.ConfigUtils;
import com.primeton.btp.api.core.logger.LoggerFactory;


public class VprsConstant {
	
//	private final static ILogger logger = LoggerFactory.getLogger(PayConstant.class);
	public static final String CONFIG_PATH = DataContextManager.current().getContributionMetaData().getContributionDirectory().getAbsolutePath() + File.separator + "META-INF" + File.separator + "vprs" + File.separator;

	public static final String UNIONPAY_CONFIG_PATH = CONFIG_PATH + "vprs.properties";

	
	public static String STREAM_TOPIC = "";
	public static String STREAM_IP = "";
	
	//目标系统
	public static String MULTITARGET = "";
	//调用方式
	public static String LOCALTION = "";
	//注册图片保存根路径
	public static String REGIST_IMG_PATH="";
	//临时图片保存根路径
	public static String TMP_IMG_PATH="";
	//日志流水图片保存根路径
	public static String LOGS_IMG_PATH="";
	//视屏保存根路径
	public static String VIDEO_IMG_PATH="";
	//人脸识别核心算法提供商
	public static String FACE_SERVICE_PROVIDER="";
	//云从app_id
	public static String APP_ID="";
	//云从app_secret
	public static String APP_SECRET="";
	
	//声纹识别核心算法提供商
	public static String AUDIO_SERVICE_PROVIDER="";
    //注册音频保存根路径
	public static String REGIST_AUDIO_PATH="";
	//临时音频保存根路径
	public static String TMP_AUDIO_PATH="";
	//日志流水音频保存根路径
	public static String LOGS_AUDIO_PATH="";
	//音频保存根路径
	public static String VIDEO_AUDIO_PATH="";
	
	//用于语音识别，音频转文本的，传入音频存放路径
	public static String AUDIO_IAT_PATH="";
	//TTS
	public static String AUDIO_TTS_PATH="";
	
	
	
	
    //基立迅随意说声纹引擎调用地址
	public static String JILIXUN_FS="";
	//基立迅固定文本声纹引擎调用地址
	public static String JILIXUN_TD="";
	
	//基立迅音频共享服务器地址
	public static String JILIXUN_SHARE_PATH="";
	
	//audio.ip.address声纹识别服务器部署地址
	public static String AUDIO_IP_ADDRESS="";
	
	//共享盘地址
	public static String JILIXUN_SHARE_PATH_REGISTER_TD="";
	public static String JILIXUN_SHARE_PATH_VERIFY_TD="";
	public static String JILIXUN_SHARE_PATH_REGISTER_FS="";
	public static String JILIXUN_SHARE_PATH_VERIFY_FS="";
	
	//音频存放地址
	public static String SHARE_PATH_REGISTER_FS="";
	
	public static String SHARE_PATH_REGISTER_TD="";
	
	public static String SHARE_PATH_VERIFY_FS="";
	
	public static String SHARE_PATH_VERIFY_TD="";
	
	
	
	//tomacat部署地址
	public static String AUDIO_IP_ADDRESS_REGISTER_TD="";
	public static String AUDIO_IP_ADDRESS_VERIFY_TD="";
	public static String AUDIO_IP_ADDRESS_REGISTER_FS="";
	public static String AUDIO_IP_ADDRESS_VERIFY_FS="";
	
	//科大讯飞iat调用地址
	public static String KEDA_IAT_IP="";
	
	//科大讯飞tts调用地址
	public static String KEDA_TTS_URL="";
	
	//科大讯飞采样率
	public static String KEDA_AUDIO_RATE="";
	
	//ffmpeg的存放地址
	public static String AUDIO_FFMPEG_PATH="";
	
	static {
			
		Properties prop = new Properties();
		try {
			
			InputStream inStream = new FileInputStream(new File (UNIONPAY_CONFIG_PATH));
			prop.load(inStream);
			
			STREAM_TOPIC = prop.getProperty("stream.topic");
			STREAM_IP = prop.getProperty("stream.ip");
			
			MULTITARGET = prop.getProperty("multiTarget");
			LOCALTION = prop.getProperty("localtion");	
			TMP_IMG_PATH = prop.getProperty("tmp.img.path");
			REGIST_IMG_PATH = prop.getProperty("regist.img.path");
			LOGS_IMG_PATH = prop.getProperty("logs.img.path");
			VIDEO_IMG_PATH = prop.getProperty("video.img.path");
			FACE_SERVICE_PROVIDER= prop.getProperty("face.service.provider");
			APP_ID= prop.getProperty("IBISService.compareByBase64Imgs.app_id");
			APP_SECRET=prop.getProperty("IBISService.compareByBase64Imgs.app_secret");
			
			//此处获取声纹提供商
			AUDIO_SERVICE_PROVIDER=prop.getProperty("audio.service.provider");
			//此处获取声纹临时音频保存根路径
			//TMP_AUDIO_PATH=prop.getProperty("tmp.audio.path");
			TMP_AUDIO_PATH=ConfigUtils.getProperty("tmp.audio.path");
			
			REGIST_AUDIO_PATH = prop.getProperty("regist.audio.path");
			
			LOGS_AUDIO_PATH=prop.getProperty("logs.audio.path");
			
			
			//语音识别，音频转文本的，传入音频存放路径
			AUDIO_IAT_PATH=prop.getProperty("audio.iat.path");
            //TTS，文本转视频，生成的音频存放路径
			AUDIO_TTS_PATH=prop.getProperty("audio.tts.path");
			
            //基立迅随意说声纹引擎调用地址
			JILIXUN_FS=prop.getProperty("wsurl");
			//基立迅固定文本声纹引擎调用地址
			JILIXUN_TD=prop.getProperty("vpurl");
			
            //基立迅音频共享服务器地址
			JILIXUN_SHARE_PATH=prop.getProperty("jilixun.audio.sharepath");
			
			//声纹服务器地址
			AUDIO_IP_ADDRESS=prop.getProperty("audio.ip.address");
			
			//共享盘地址
			JILIXUN_SHARE_PATH_REGISTER_TD=prop.getProperty("jilixun.audio.sharepath.register.td");
			JILIXUN_SHARE_PATH_VERIFY_TD=prop.getProperty("jilixun.audio.sharepath.verify.td");
			JILIXUN_SHARE_PATH_REGISTER_FS=prop.getProperty("jilixun.audio.sharepath.register.fs");
			JILIXUN_SHARE_PATH_VERIFY_FS=prop.getProperty("jilixun.audio.sharepath.verify.fs");
			
			//音频存放地址
			SHARE_PATH_REGISTER_FS=prop.getProperty("audio.sharepath.register.fs");
			SHARE_PATH_VERIFY_TD=prop.getProperty("audio.sharepath.verify.td");
			SHARE_PATH_REGISTER_TD=prop.getProperty("audio.sharepath.register.td");
			SHARE_PATH_VERIFY_FS=prop.getProperty("audio.sharepath.verify.fs");
			
			//tomcat部署地址
			AUDIO_IP_ADDRESS_REGISTER_TD=prop.getProperty("audio.ip.address.register.td");
			AUDIO_IP_ADDRESS_VERIFY_TD=prop.getProperty("audio.ip.address.verify.td");
			AUDIO_IP_ADDRESS_REGISTER_FS=prop.getProperty("audio.ip.address.register.fs");
			AUDIO_IP_ADDRESS_VERIFY_FS=prop.getProperty("audio.ip.address.verify.fs");
			
			
			//科大讯飞iat调用地址
			KEDA_IAT_IP=prop.getProperty("keda.iat.ip");
			
			//科大讯飞tts调用地址
			KEDA_TTS_URL=prop.getProperty("keda.tts.url");
			
			//科大讯飞采样率
			KEDA_AUDIO_RATE=prop.getProperty("keda.audio.rate");
			
			//ffmpeg的存放地址
			//AUDIO_FFMPEG_PATH=prop.getProperty("audio.ffmpeg.path");
			AUDIO_FFMPEG_PATH=ConfigUtils.getProperty("audio.ffmpeg.path");
			inStream.close();
		} catch (Exception e) {
//			logger.error("加载BAMS配置信息失败！错误信息为：" + e, e);
		}
	}
}

