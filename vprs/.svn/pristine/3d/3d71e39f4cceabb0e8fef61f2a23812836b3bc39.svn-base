package com.pccc.vprs.servicecustom.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.pccc.touda.common.util.ConfigUtils;
import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import com.primeton.ext.infra.security.BASE64Encoder;

import sun.misc.BASE64Decoder;

/**
 * 音频存储到文件系统的操作类
 * @author pccc
 *
 */
public class AudioFileUtils {
	private static String imgFileSuffix = ".jpg";

	private static String videoFileSuffix = ".avi";

	private final static ILogger logger = LoggerFactory
			.getLogger(AudioFileUtils.class);

	private static String audioFileSuffix = ".wav";
	
	public static final String fastDfsGroup = ConfigUtils
			.getProperty("fastdfs.group");

	public static final String operUser = ConfigUtils.getProperty("operUser");

	
	/**
	 * 通过uuid值创建音频路径字符串
	 */
	public static String crtAudioPathByUUID(String uuid) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.TMP_AUDIO_PATH + separator + VPRSUtils.getTime("yyyyMMdd")
				+ separator + uuid + audioFileSuffix;
	}

	/**
	 * 通过uuid值创建音频路径字符串
	 * uuid UUID
	 * channel 调用渠道（app、PDA等）
	 * 返回注册路径/渠道/日期/哈希路径/文件
	 */
	public static String crtAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.REGIST_AUDIO_PATH + separator + VPRSUtils.getTime("yyyyMMdd") + separator
				+ channel  + separator + uuid
				+ audioFileSuffix;
	}
	
	
	/**
	 * 通过uuid值创建音频路径字符串
	 * uuid UUID
	 * channel 调用渠道（app、PDA等）
	 * 返回注册路径/渠道/日期/哈希路径/文件  ==A174669 重载
	 */
	public static String crtAudioPathByUUID(String uuid, String channel,String audioFileSuffix) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.AUDIO_IAT_PATH + separator + VPRSUtils.getTime("yyyyMMdd") + separator
				+ channel  + separator + uuid
				+ audioFileSuffix;
	}
	
	public static String crtAudioPathByUUIDTTS(String uuid, String channel,String audioFileSuffix) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.AUDIO_TTS_PATH + separator + VPRSUtils.getTime("yyyyMMdd") + separator
				+ channel  + separator + uuid
				+ audioFileSuffix;
	}
	
	
	//获取基立迅共享路径
	public static String[] crtAudioPathByUUIDJILIXUN(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		//项目中音频的地址
		String[] array={};
		String audioWebPathIn=VPRSUtils.getTime("yyyyMMdd") + separator+ channel  + separator + uuid+ audioFileSuffix;
		String audioLocalPath=VprsConstant.JILIXUN_SHARE_PATH+ separator +audioWebPathIn;
		array[0]=audioWebPathIn;
		array[1]=audioLocalPath;
		return array;
	}
	
	
	/**
	 * 通过uuid值创建音频路径字符串
	 * uuid UUID
	 * channel 调用渠道（app、PDA等）
	 * 返回日志流水路径/渠道/日期/哈希路径/文件
	 */
	public static String crtLogAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.LOGS_AUDIO_PATH + separator + VPRSUtils.getTime("yyyyMMdd") + separator
				+ channel + separator + uuid
				+ audioFileSuffix;
	}
	
	
	/**
	 * 通过uuid值创建音频路径字符串
	 * uuid UUID
	 * channel 调用渠道（app、PDA等）
	 * 返回日志流水路径/渠道/日期/哈希路径/文件
	 */
	public static String crtLoginAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		
		return VprsConstant.LOGS_AUDIO_PATH + separator + "login" +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
	}
	
	
	
	/**
	 * 通过uuid值创建音频路径字符串   （重构）
	 * uuid UUID
	 * channel 调用渠道（app、PDA等）
	 * 返回日志流水路径 声纹类型/声纹调用方式/渠道/日期/哈希路径/文件
	 */
	public static String crtFsRegisterAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.SHARE_PATH_REGISTER_FS +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
		
//		return VprsConstant.JILIXUN_SHARE_PATH_REGISTER_FS +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
	}
	
	public static String crtTdRegisterAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.SHARE_PATH_REGISTER_TD +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
		
//		return VprsConstant.JILIXUN_SHARE_PATH_REGISTER_TD+separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
	}
	
	public static String crtFsVerifyAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.SHARE_PATH_VERIFY_FS +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
		
//		return VprsConstant.JILIXUN_SHARE_PATH_VERIFY_FS +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
	}
	
	public static String crtTdVerifyAudioPathByUUID(String uuid, String channel) {
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.SHARE_PATH_VERIFY_TD +separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
		
//		return VprsConstant.JILIXUN_SHARE_PATH_VERIFY_TD+separator+ VPRSUtils.getTime("yyyyMMdd") + separator+ channel + separator + uuid+ audioFileSuffix;
	}
	
	
	/** 
	 * base64解码文件保存到指定目录（不管该目录存不存在）
	 * @param base64ImgData 
	 * @param filePath 
	 * @throws IOException 
	 */
	public static void convertBase64DataToAudio(String base64AudioData,
			String directory) throws IOException {
		createNewFileByPath(directory);
		generateAudio(base64AudioData, directory);
	}
	
	/** 
	 * base64解码文件
	 * @param base64ImgData 
	 * @param filePath 
	 * @throws IOException 
	 */
	public static byte[] convertBase64DataToAudioNoAdress(String base64AudioData) throws IOException {
		byte[] b=generateAudio2(base64AudioData);
		return b;
	}

//	/** 
//	 * 根据文件base64字符串在指定路径解码生成对应文件--前提是该路径已存在
//	 * @date 2017年1月18日下午1:43:16 
//	 * @param imgStr 
//	 * @param imgFilePath 
//	 * @return boolean 
//	 * @Des:base解码 
//	 */
//	public static boolean generateImage(String imgStr, String imgFilePath) {
//		if (imgStr == null) {
//			return false;
//		}
//		boolean flag=false; 
//		OutputStream out=null;
//		try {
//
//			byte[] b = Base64.decodeBase64(new String(imgStr).getBytes());
//
//			for (int i = 0; i < b.length; ++i) {
//				if (b[i] < 0) {//调整异常数据  
//					b[i] += 256;
//				}
//			}
//			//生成JPEG图片  
//			out = new FileOutputStream(imgFilePath);
//			out.write(b);
//			flag=true;
//		} catch (Exception e) {
//			logger.error("根据文件base64字符串在指定路径解码生成对应文件异常：" + e.getMessage());
//		}finally{		
//			try {
//				if(out!=null){
//					out.flush();
//					out.close();
//				}			
//			} catch (IOException e) {
//				logger.error("根据文件base64字符串在指定路径解码生成对应文件，关闭文件异常：" + e.getMessage());
//			}			
//		}		
//		return  flag;
//	}

	/** 
	 * 根据文件base64字符串在指定路径解码生成对应文件--前提是该路径已存在
	 * @date 2017年1月18日下午1:43:16 
	 * @param imgStr 
	 * @param imgFilePath 
	 * @return boolean 
	 * @Des:base解码 
	 */
	public static boolean generateAudio(String audioStr, String audioFilePath) {
		if (audioStr == null) {
			return false;
		}
		boolean flag=false; 
		OutputStream out=null;
		try {

			byte[] b = Base64.decodeBase64(new String(audioStr).getBytes());

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {//调整异常数据  
					b[i] += 256;
				}
			}
			//生成JPEG图片  
			out = new FileOutputStream(audioFilePath);
			out.write(b);
			flag=true;
		} catch (Exception e) {
			logger.error("根据文件base64字符串在指定路径解码生成对应文件异常：" + e.getMessage());
		}finally{		
			try {
				if(out!=null){
					out.flush();
					out.close();
				}			
			} catch (IOException e) {
				logger.error("根据文件base64字符串在指定路径解码生成对应文件，关闭文件异常：" + e.getMessage());
			}			
		}		
		return  flag;
	}
	
	/** 
	 * 根据文件base64字符串解码生成对应文件
	 * @date 2017年1月18日下午1:43:16 
	 * @param adoStr 
	 * @return byte 
	 * @Des:base解码 
	 */
	public static byte[] generateAudio2(String adoStr) {
		byte[] buffer=null;
		try {
			byte[] b = Base64.decodeBase64(new String(adoStr).getBytes());

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {//调整异常数据  
					b[i] += 256;
				}
			}
			buffer=b;
		} catch (Exception e) {
			logger.error("文件base64字符串解码生成对应文件异常：" + e.getMessage());
		}
		return buffer;
	}
	
//	/** 
//	 * 根据文件路径返回文件base64编码字符串
//	 * @date 2017年1月18日下午1:43:00 
//	 * @param imgFilePath 
//	 * @return String 
//	 * @Des:Base64编码 
//	 */
//	public static String getImageFile(String imgFilePath) {
//		byte[] data = null;
//		if (StringUtils.isEmpty(imgFilePath)) {
//			return null;
//		}
//		//读取图片字节数组  
//		InputStream in=null;
//		try {
//			File file = new File(imgFilePath);
//			if (!file.exists() || file.length() <= 0) {
//				return null;
//			}
//			in = new FileInputStream(imgFilePath);
//			data = new byte[in.available()];
//			in.read(data);
//			
//		} catch (Exception e) {
//			logger.error("根据文件路径返回文件base64编码字符串异常：" + e.getMessage());
//		} finally {
//			try {
//				if(in!=null){
//					in.close();
//				}		
//			} catch (IOException e) {
//				logger.error("根据文件路径返回文件base64编码字符串,关闭文件异常：" + e.getMessage());
//			}
//		}
//		//对字节数组Base64编码  
//		BASE64Encoder encoder = new BASE64Encoder();
//		//返回Base64编码过得字节数组字符串  
//		return encoder.encode(data);
//	}

	
	/** 
	 * 根据文件路径返回文件base64编码字符串
	 * @date 2017年1月18日下午1:43:00 
	 * @param imgFilePath 
	 * @return String 
	 * @Des:Base64编码 
	 */
	public static String getAudioFile(String adoFilePath) {
		byte[] data = null;
		if (StringUtils.isEmpty(adoFilePath)) {
			return null;
		}
		//读取图片字节数组  
		InputStream in=null;
		try {
			File file = new File(adoFilePath);
			if (!file.exists() || file.length() <= 0) {
				return null;
			}
			in = new FileInputStream(adoFilePath);
			data = new byte[in.available()];
			in.read(data);
			
		} catch (Exception e) {
			logger.error("根据文件路径返回文件base64编码字符串异常：" + e.getMessage());
		} finally {
			try {
				if(in!=null){
					in.close();
				}		
			} catch (IOException e) {
				logger.error("根据文件路径返回文件base64编码字符串,关闭文件异常：" + e.getMessage());
			}
		}
		//对字节数组Base64编码  
		BASE64Encoder encoder = new BASE64Encoder();
		//返回Base64编码过得字节数组字符串  
		return encoder.encode(data);
	}
	
	/**
	 * 对wav文件去除44字节，再转换为base64编码
	 * @param adoFilePath  文件地址
	 * @return
	 */
	public static String getBase64FromAudioExceptTop(String path) {
		    byte[] bytes=null;
		    if (StringUtils.isEmpty(path)) {
				return null;
			}
			int position = path.endsWith(".wav") ? 44 : 0;
			InputStream in = null;
			try {
				
				File file = new File(path);
				if (!file.exists() || file.length() <= 0) {
					return null;
				}
				
				in = new FileInputStream(file);
				in.skip(position);
				bytes = new byte[in.available() - position];
				in.read(bytes);
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//对字节数组Base64编码  
			BASE64Encoder encoder = new BASE64Encoder();
			//返回Base64编码过得字节数组字符串  
			return encoder.encode(bytes);
		
	}
	
	/**
	 * 创建文件路径
	 * @param directory
	 */
	public static void createNewFileByPath(String directory) {
		File f = new File(directory);
		if (!f.exists()) {
			// 如果文件夹不存在，先创建文件所在的目录
			f.getParentFile().mkdirs();
			try {
				// 创建新文件
				f.createNewFile();
			} catch (IOException e) {
				logger.error("创建文件时出现错误，错误信息：" + e.getMessage());
			}
		}
	}
	
	/**
	 * path 共享盘时，拷贝文件到指定路径
	 */
	public static void copyToPath(String directory,String directorydst) {
		File file1=new File(directory);
		File file2=new File(directorydst);
		try {
			if(!file2.exists()) {
				// 如果文件夹不存在，先创建文件所在的目录
				file2.getParentFile().mkdirs();
			}
			Files.copy(file1.toPath(), file2.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("拷贝文件到指定路径时出现错误，错误信息："+e.getMessage());
		}
	}
	
	/**
	 * url 从url下载文件存储到指定路径
	 */
	public static void  downLoadFromUrl(String urlStr,String fileName) throws IOException{    
		InputStream inputStream=null;
		FileOutputStream fos=null;
		
		URL url = new URL(urlStr);      
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();      
        //设置超时间为3秒    
        conn.setConnectTimeout(3*1000);    
        //防止屏蔽程序抓取而返回403错误    
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");    
        try{
        //得到输入流    
        inputStream = conn.getInputStream();      
        //获取自己数组    
        byte[] getData = readInputStream(inputStream);        
    
        
        File f = new File(fileName);
		if (!f.exists()) {
			// 如果文件夹不存在，先创建文件所在的目录
			f.getParentFile().mkdirs();
			try {
				// 创建新文件
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("创建文件时出现错误，错误信息：" + e.getMessage());
			}
		}
              
        fos = new FileOutputStream(f);         
        fos.write(getData); 
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	logger.info(e.getMessage());
		}finally{
        if(fos!=null){    
            fos.close();      
        }    
        if(inputStream!=null){    
            inputStream.close();    
        }   
		} 
		logger.info("info:"+url+" download success");    
    }    
    
    
    
    /**   
     * 从输入流中获取字节数组   
     * @param inputStream   
     * @return   
     * @throws IOException   
     */    
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {      
        byte[] buffer = new byte[1024];      
        int len = 0;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        while((len = inputStream.read(buffer)) != -1) {      
            bos.write(buffer, 0, len);      
        }      
        bos.close();      
        return bos.toByteArray();      
    }      
    
    /**
     * 将base64字符串形式的音频存放到toudaDFS
     * @param base64AudioData   base64字符串
     * @param fileName  文件名称
     * @param metaInfo  元信息
     * @return
     */
    public static String convertBase64DataToToudaDfs(String base64AudioData,
			String fileName,Map<String, String> metaInfo) {
    	byte[] file_buff = null;
    	BASE64Decoder decoder = new BASE64Decoder();
		try {
			file_buff = decoder.decodeBuffer(base64AudioData);
		} catch (IOException e) {
			logger.error("文件操作异常，异常信息:" + e.getMessage(), e);
		}
		
		String fileAbsolutePath = "";
		try {
//			fileName = FilenameUtils.getName("VPRS_Audio_"
//					+ System.currentTimeMillis());
			fileAbsolutePath = FastDFSClientAlone.upload(fastDfsGroup,
					fileName, file_buff, null, metaInfo);
		} catch (Exception e) {
			logger.error("上传语音文件操作异常，异常信息:" + e.getMessage(), e);
		}
    	return fileAbsolutePath;
    }
	
    public static int toInt(byte[] b) {
        return (((0x000000ff & ((int)b[3])) << 24) + ((0x000000ff & ((int)b[2])) << 16) + ((0x000000ff & ((int)b[1])) << 8) + ((0x000000ff & ((int)b[0])) << 0));
    }

    public static short toShort(byte[] b) {
        return (short)(((0x000000ff & ((int)b[1])) << 8) + ((0x000000ff & ((int)b[0])) << 0));
    }
	
	public static byte[] read(RandomAccessFile rdf,int pos,int length) throws IOException {
		rdf.seek(pos);
		byte result[]=new  byte[length];
		for (int i = 0; i < length; i++) {
			result[i]=rdf.readByte();
		}
		return result;
	}
	
	
	public static Map<String,Object> readSampleRateAndEncodeFormat(String filepath) throws IOException {
		// ulaw 7 //6 alaw //1 pcm
		File f = new File(filepath);
		RandomAccessFile rdf = null;
		rdf = new RandomAccessFile(f, "r");
		
		Map<String,Object> audioInfo=new HashMap<String,Object>();
		
		// 编码格式
		Short encodeNum=toShort(read(rdf, 20, 2));
		// 采样率
		int sampleRate=toInt(read(rdf, 24, 4));
		
		
		System.out.println(encodeNum);
		
		System.out.println(sampleRate);
		audioInfo.put("sampleRate", sampleRate);
		if(encodeNum==1) {
			audioInfo.put("encodeFormat", "pcm16");
		}else if(encodeNum==6) {
			audioInfo.put("encodeFormat", "alaw");
		}else if(encodeNum==7) {
			audioInfo.put("encodeFormat", "ulaw");
		}
		System.out.println(audioInfo);
		return audioInfo;
	}

	
	
}
