/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.common.util.ConfigUtils;
import com.pccc.vprs.servicecustom.common.Constants;
import com.pccc.vprs.servicecustom.constants.VprsConstant;
import com.pccc.vprs.servicecustom.transflow.TransDetailModel;
import com.pccc.vprs.servicecustom.util.AudioFileUtils;
import com.pccc.vprs.servicecustom.util.FastDFSClientAlone;
import com.pccc.vprs.servicecustom.util.VPRSUtils;
import com.pccc.vprs.servicedisplay.bams.model.AudioInfo;
import com.pccc.vprs.servicedisplay.bams.model.AudioInfoVO;
import com.pccc.vprs.servicedisplay.bams.model.AudioUserInfo;
import com.pccc.vprs.servicedisplay.bams.model.TransDetailModelVO;
import com.pccc.vprs.servicedisplay.vprs.common.ReturnCode;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;
import commonj.sdo.DataObject;

/**
 * @author A146901
 * @date 2018-07-09 10:12:41
 * 
 */
@Bizlet("语音采集处理类")
public class AudioCollect {
	private static ILogger logger = LoggerFactory.getLogger(AudioCollect.class);

	private static String INSERT_AUDIO_INFO = "com.pccc.vprs.servicecustom.sql.AudioCollect.insertAudioInfo"; // 将语音插入语音信息表

	private static String QUERY_USER_INFO = "com.pccc.vprs.servicecustom.sql.AudioCollect.queryUserInfo"; // 查询用户信息

	private static String INSERT_USER_INFO = "com.pccc.vprs.servicecustom.sql.AudioCollect.insertUserInfo"; // 插入用户信息

	private static String UPDATE_USER_INFO = "com.pccc.vprs.servicecustom.sql.AudioCollect.updateUserInfo"; // 更新用户信息

	private static String QUERY_USER_AUDIO_INFO = "com.pccc.vprs.servicecustom.sql.AudioCollect.queryUserAudioInfo"; // 查询用户语音文件信息

	public static final String QUERY_AUDIO_TRANS_DETAIL = "com.pccc.vprs.servicecustom.sql.TransFlowSqlMap.queryAudioTransDetail"; // 查询语音导入流水表

	private static String QUERY_USER_AUDIO_INFO_BY_AUDIO_NAME = "com.pccc.vprs.servicecustom.sql.AudioCollect.queryUserAudioInfoByAudioName"; // 根据语音文件名查询用户语音文件信息

	public static final String fastDfsGroup = ConfigUtils
			.getProperty("fastdfs.group");

	public static final String operUser = ConfigUtils.getProperty("operUser");

	public static final String audio_duration_threshold = ConfigUtils
			.getProperty("audioDurationThreshold");

	// 检测语音文件。无论语音检测成功或失败，语音文件都要被存储
	@Bizlet("检测语音属性")
	public DataObject detectAudio(DataObject inMessage, AudioInfo audioInfo,
			DataObject outMessage) {
		logger.error("检测语音属性");

		if (StringUtils.isBlank(inMessage.getString("userName"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		if (StringUtils.isBlank(inMessage.getString("certType"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		if (StringUtils.isBlank(inMessage.getString("certNo"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		if (StringUtils.isBlank(inMessage.getString("channel"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}

		if (StringUtils.isBlank(inMessage.getString("businessType"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		if (StringUtils.isBlank(inMessage.getString("closeAudio"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		if (StringUtils.isBlank(inMessage.getString("closeAudioFormat"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}

		// 为AudioInfo注值
		audioInfo.setUserName(inMessage.getString("userName"));
		audioInfo.setCertType(inMessage.getString("certType"));
		audioInfo.setCertNo(inMessage.getString("certNo").toUpperCase());
		audioInfo.setChannel(inMessage.getString("channel"));
		audioInfo.setBusinessType(inMessage.getString("businessType"));
		audioInfo.setCloseAudio(inMessage.getString("closeAudio"));
		audioInfo.setCloseAudioFormat(inMessage.getString("closeAudioFormat"));

		if (StringUtils.isNotBlank(inMessage.getString("userUniqueId"))) {
			audioInfo.setUserUniqueId(inMessage.getString("userUniqueId"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("riskLevel"))) {
			audioInfo.setRiskLevel(inMessage.getString("riskLevel"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("userType"))) {
			audioInfo.setUserType(inMessage.getString("userType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("phoneNo"))) {
			audioInfo.setPhoneNo(inMessage.getString("phoneNo"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("closeAudioName"))) {
			audioInfo.setReserveCoulumn1(inMessage.getString("closeAudioName"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("batchTime"))) {
			audioInfo.setReserveCoulumn2(inMessage.getString("batchTime"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("city"))) {
			audioInfo.setReserveCoulumn3(inMessage.getString("city"));
		}
		if (StringUtils.isNotBlank(inMessage.getString("nosieTest"))) {
			audioInfo.setReserveCoulumn4(inMessage.getString("nosieTest"));
		}
		if (StringUtils.isNotBlank(inMessage.getString("recodeWay"))) {
			audioInfo.setReserveCoulumn5(inMessage.getString("recodeWay"));
		}
		
		if (StringUtils.isNotBlank(inMessage.getString("processResult"))) {
			audioInfo.setProcessResult(inMessage.getString("processResult"));
		}
		
		
		//设置PDA的风险等级
		if("PDA".equals(audioInfo.getChannel())){
			audioInfo.setRiskLevel(Constants.PDA_RISK_LEVEL);
		}
		
		// @TODO - 检测用户语音是否已经存在，用于重跑
		if (StringUtils.isNotBlank(inMessage.getString("closeAudioName"))
				&& StringUtils.isNotBlank(inMessage.getString("batchTime"))) {
			String tableName = getTableNameByCertNo(audioInfo.getCertNo(),
					audioInfo.getCertType());
			audioInfo.setTableName(tableName);
			// HashMap map = DatabaseExt.queryByNamedSql("default",
			// QUERY_USER_AUDIO_INFO_BY_AUDIO_NAME, audioInfo);
			int count = DatabaseExt.countByNamedSql("default",
					QUERY_USER_AUDIO_INFO_BY_AUDIO_NAME, audioInfo);
			if (count > 0) {// 表明该语音已经上传成功
				outMessage.set("returnCode", ReturnCode.FILE_ALREADY_EXIST);
				outMessage.set("returnMsg", "文件已经存在，请勿重复上传");
				logger.error("文件已经存在，请勿重复上传");
				return outMessage;
			}
		}

		String tempDirectory = "";// 存放临时语音的目录
		// 校测音频属性
		if ("file".equals(audioInfo.getCloseAudioFormat())) {
			// 设置音频的后缀名
			if (audioInfo.getCloseAudio().lastIndexOf(".") != -1) {
				audioInfo.setExtName(audioInfo.getCloseAudio().substring(
						audioInfo.getCloseAudio().lastIndexOf(".") + 1));
			}
			tempDirectory = audioInfo.getCloseAudio();

		}

		// 临时存储语音文件用于检测其属性
		if ("base64".equals(audioInfo.getCloseAudioFormat())) {
			tempDirectory = crtAudioPathByUUID(VPRSUtils.getUUID());
			logger.error("个人语音文件临时存储路径：" + tempDirectory);
			try {
				AudioFileUtils.convertBase64DataToAudio(audioInfo
						.getCloseAudio(), tempDirectory);
			} catch (IOException e) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "临时语音文件存储失败");
				logger.error("临时语音文件存储失败，异常信息:" + e.getMessage(), e);
				return outMessage;
			}
		}

		File f = new File(tempDirectory);

		if (!f.exists()) {
			outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
			outMessage.set("returnMsg", "语音文件不存在");
			logger.error("语音文件不存在，导入语音失败");
			return outMessage;
		}

		if ("PDA".equals(inMessage.getString("channel").toUpperCase())) {
			//如果渠道类型为pda,则人工设置值
			audioInfo.setBitRate("8");
			audioInfo.setCodeRate("128");
			audioInfo.setEncodingFormat("pcm");
			audioInfo.setExtName("pcm");
			audioInfo.setSoundTrack("1 channels");
			audioInfo.setSamplingRate("16000");
			int duration = (int)f.length()*8/16000/8/1;
			audioInfo.setAudioDuration(duration+"");
		} else {
			try {
				detectAudioInfo(audioInfo, VprsConstant.AUDIO_FFMPEG_PATH,
						tempDirectory);
			} catch (Exception e) {
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "检测语音文件属性失败");
				logger.error("检测语音文件属性失败，异常信息:" + e.getMessage(), e);
				// 检测失败，也需要删除临时语音文件
				try {
					if ("base64".equals(audioInfo.getCloseAudioFormat())) {
						FileUtils.forceDelete(new File(tempDirectory));
					}
				} catch (IOException e1) {
					outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
					outMessage.set("returnMsg", "临时语音文件删除失败");
					logger.error("临时语音文件删除失败，异常信息:" + e.getMessage(), e1);
					return outMessage;
				}
				return outMessage;
			}
		}

		// 检测完毕之后，删除临时语音文件
		try {
			if ("base64".equals(audioInfo.getCloseAudioFormat())) {
				FileUtils.forceDelete(new File(tempDirectory));
			}
		} catch (IOException e) {
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "临时语音文件删除失败");
			logger.error("临时语音文件删除失败，异常信息:" + e.getMessage(), e);
			return outMessage;
		}

		if (audioInfo.getAudioDuration() != null) {
			try {
				if (Integer.parseInt(audioInfo.getAudioDuration()) < Integer
						.parseInt(audio_duration_threshold)) {
					outMessage.set("returnCode",
							ReturnCode.AUDIO_DURATION_SHORT);
					outMessage.set("returnMsg", "语音时长短于"
							+ audio_duration_threshold + "s,不导入!");
					logger.error("语音时长过短，不导入");
					return outMessage;
				}
			} catch (NumberFormatException e) {
				outMessage.set("returnCode", ReturnCode.TOUDA_FAIL);
				outMessage.set("returnMsg", "语音时长格式转化错误!");
				logger.error("语音时长格式转化错误，异常信息:" + e.getMessage(), e);
				return outMessage;
			}
		}

		logger.error("audioInfo--->" + audioInfo);

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "语音文件属性检查成功");
		return outMessage;
	}

	@Bizlet("存储语音文件属性")
	public DataObject savaAudioInfo(DataObject inMessage, AudioInfo audioInfo,
			DataObject outMessage) {
		logger.error("存储语音文件属性");

		try {
			audioInfo.setTableName(getTableNameByCertNo(audioInfo.getCertNo(),
					audioInfo.getCertType()));
			DatabaseExt
					.executeNamedSql("default", INSERT_AUDIO_INFO, audioInfo);
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", "保存用户语音文件属性失败！");
			logger.error("保存用户语音文件属性数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}
		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "保存用户语音成功");
		return outMessage;
	}

	@Bizlet("将语音文件存入ToudaDFS")
	public DataObject savaAudio2DFS(DataObject inMessage, AudioInfo audioInfo,
			DataObject outMessage) {
		logger.error("开始将语音文件存入ToudaDFS");

		// 获取closeAudio和closeAudioFormat
		String closeAudio = audioInfo.getCloseAudio();
		String closeAudioFormat = audioInfo.getCloseAudioFormat();

		// 调用ToudaDFS API进行存储
		byte[] file_buff = null;
		if ("file".equals(closeAudioFormat)) {
			try {
				file_buff = FileUtils.readFileToByteArray(new File(closeAudio));
			} catch (IOException e) {
				outMessage.set("returnCode",
						ReturnCode.FILE_NOT_FOUND_EXCEPTION);
				outMessage.set("returnMsg", "用户语音文件不存在！");
				logger.error("获取用户语音文件文件操作异常，异常信息:" + e.getMessage(), e);
				return outMessage;
			}
		} else {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				file_buff = decoder.decodeBuffer(closeAudio);
			} catch (IOException e) {
				outMessage.set("returnCode",
						ReturnCode.FILE_OPERATION_EXCEPTION);
				outMessage.set("returnMsg", "文件操作异常！");
				logger.error("文件操作异常，异常信息:" + e.getMessage(), e);
				return outMessage;
			}
		}

		// FileStoreInfo fileStoreInfo = null;
		Map<String, String> metaInfo = new HashMap<String, String>();
		String fileAbsolutePath = "";
		metaInfo.put("operUser", operUser);// 上传用户
		metaInfo.put("busiCode", "J_VPRS_003_0001");// 必填
		// metaInfo.put("fileStatus", "1");// 可选，直接绑定文件至持久状态，值填1
		
		// ToudaDFSClient client = ToudaDFSClient.getInstance();
		String fileName = "";
		if ("file".equals(closeAudioFormat)) {
			try {
				// fileStoreInfo =
				// client.upload(FilenameUtils.getName(closeAudio), file_buff,
				// metaInfo);
				fileName = FilenameUtils.getName(closeAudio);
				fileAbsolutePath = FastDFSClientAlone.upload(fastDfsGroup,
						fileName, file_buff, null, metaInfo);
			} catch (Exception e) {
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "语音文件上传失败！");
				logger.error("上传语音文件操作异常，异常信息:" + e.getMessage(), e);
				return outMessage;
			}
		} else {
			try {
				// fileStoreInfo =
				// client.upload(FilenameUtils.getName("VPRS_Audio_"+System.currentTimeMillis()),
				// file_buff, metaInfo);
				fileName = FilenameUtils.getName("VPRS_Audio_"
						+ System.currentTimeMillis());
				fileAbsolutePath = FastDFSClientAlone.upload(fastDfsGroup,
						fileName, file_buff, null, metaInfo);
			} catch (Exception e) {
				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "语音文件上传失败！");
				logger.error("上传语音文件操作异常，异常信息:" + e.getMessage(), e);
				return outMessage;
			}
		}

		file_buff = null;
		// 删除DFS中语音文件------用于测试
		// try {
		// boolean result = FastDFSClientAlone.deleteFile(fileAbsolutePath);
		// logger.error("语音删除结果--->"+result);
		// } catch (Exception e) {
		// outMessage.set("returnCode",ReturnCode.FILE_SYSTEM_EXCEPTION );
		// outMessage.set("returnMsg","删除语音文件操作异常！" );
		// logger.error("删除语音文件操作异常，异常信息:" + e.getMessage(), e);
		// return outMessage;
		// }

		if (StringUtils.isNotBlank(fileAbsolutePath)) {
			logger.error("上传语音文件成功");
			// 保存语音文件存储位置和ID
			audioInfo.setAudioPath(fileAbsolutePath);
			// audioInfo.setAudioId(fileStoreInfo.getFileId());
			// 生成唯一id存入数据库中
			audioInfo.setAudioId(VPRSUtils.getUUID());
			outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
			outMessage.set("returnMsg", "语音文件上传成功！");
		} else {
			outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
			outMessage.set("returnMsg", "语音文件上传失败！");
			logger.error("上传语音文件操作异常");
			return outMessage;
		}

		return outMessage;
	}

	@Bizlet("插入或更新用户信息")
	public DataObject insertOrUpdateUserInfo(DataObject inMessage,
			AudioInfo audioInfo, DataObject outMessage) {
		logger.error("插入或更新用户信息");
		
		// 检测用户是否存在，
		AudioUserInfo audioUserInfo = new AudioUserInfo();
		audioUserInfo.setCertNo(audioInfo.getCertNo());
		audioUserInfo.setCertType(audioInfo.getCertType());

		AudioUserInfo newAudioUserInfo = null;
		try {
			Object[] objs = DatabaseExt.queryByNamedSql("default",
					QUERY_USER_INFO, audioUserInfo);
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					newAudioUserInfo = (AudioUserInfo) obj;
					break;
				}
				// 存在，检测用户所有语音，获取所有时长，并更新用户信息
				int time = 0;
				// if(StringUtils.isNotBlank(newAudioUserInfo) &&
				// StringUtils.isNotBlank(newAudioUserInfo.getFsAudioTotalDuration())
				// && StringUtils.isNotBlank(audioInfo.getAudioDuration()) ){
				// time =
				// Integer.parseInt(newAudioUserInfo.getFsAudioTotalDuration())+Integer.parseInt(audioInfo.getAudioDuration());
				// }
				// newAudioUserInfo.setFsAudioTotalDuration(time+"");
				
		
				
				if (Constants.LOW_RISK_LEVEL.equals(audioInfo.getRiskLevel())) {
					if (StringUtils.isNotBlank(newAudioUserInfo
							.getLowDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(newAudioUserInfo
								.getLowDuration())
								+ Integer
										.parseInt(audioInfo.getAudioDuration());
					} else if (StringUtils.isBlank(newAudioUserInfo
							.getLowDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(audioInfo.getAudioDuration()); // 不同风险等级的语音时长可能为零
					}
					newAudioUserInfo.setLowDuration(time + "");
				} else if (Constants.MIDDLE_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())) {
					if (StringUtils.isNotBlank(newAudioUserInfo
							.getMiddleDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(newAudioUserInfo
								.getMiddleDuration())
								+ Integer
										.parseInt(audioInfo.getAudioDuration());
					} else if (StringUtils.isBlank(newAudioUserInfo
							.getMiddleDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(audioInfo.getAudioDuration());
					}
					newAudioUserInfo.setMiddleDuration(time + "");
				} else if (Constants.HIGH_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())) {
					if (StringUtils.isNotBlank(newAudioUserInfo
							.getHighDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(newAudioUserInfo
								.getHighDuration())
								+ Integer
										.parseInt(audioInfo.getAudioDuration());
					} else if (StringUtils.isBlank(newAudioUserInfo
							.getHighDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(audioInfo.getAudioDuration());
					}
					newAudioUserInfo.setHighDuration(time + "");
				} else if (Constants.SUPER_HIGH_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())) {
					if (StringUtils.isNotBlank(newAudioUserInfo
							.getSuperHighDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(newAudioUserInfo
								.getSuperHighDuration())
								+ Integer
										.parseInt(audioInfo.getAudioDuration());
					} else if (StringUtils.isBlank(newAudioUserInfo
							.getSuperHighDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(audioInfo.getAudioDuration());
					}
					newAudioUserInfo.setSuperHighDuration(time + "");
				} else if (Constants.SPECIAL_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())) {
					if (StringUtils.isNotBlank(newAudioUserInfo
							.getSpecialDuraion())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(newAudioUserInfo
								.getSpecialDuraion())
								+ Integer
										.parseInt(audioInfo.getAudioDuration());
					} else if (StringUtils.isBlank(newAudioUserInfo
							.getSpecialDuraion())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(audioInfo.getAudioDuration());
					}
					newAudioUserInfo.setSpecialDuraion(time + "");
				} else if (Constants.PDA_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())) {
					if (StringUtils.isNotBlank(newAudioUserInfo
							.getSpecialDuraion())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(newAudioUserInfo
								.getPdaDuration())
								+ Integer
										.parseInt(audioInfo.getAudioDuration());
					} else if (StringUtils.isBlank(newAudioUserInfo
							.getPdaDuration())
							&& StringUtils.isNotBlank(audioInfo
									.getAudioDuration())) {
						time = Integer.parseInt(audioInfo.getAudioDuration());
					}
					newAudioUserInfo.setPdaDuration(time + "");
				}
				
				
				//比较原有的最高风险等级，且当前语音时长大于30
				if(StringUtils.isNotBlank(newAudioUserInfo.getMaxRiskLevel()) && StringUtils.isNotBlank(audioInfo.getRiskLevel())){
					double maxRiskLevel = Double.parseDouble(newAudioUserInfo.getMaxRiskLevel());
					double riskLevel = Double.parseDouble(audioInfo.getRiskLevel());
					if(time>=30 && (riskLevel>maxRiskLevel)){
						//当当前风险等级大于最高风险等级，更新最高风险等级,且语音时长大于30S
						newAudioUserInfo.setMaxRiskLevel(audioInfo.getRiskLevel());
					}
				}else if(StringUtils.isBlank(newAudioUserInfo.getMaxRiskLevel()) && StringUtils.isNotBlank(audioInfo.getRiskLevel())) {
					//表明历史最高风险等级为空
					if(time>=30){
						newAudioUserInfo.setMaxRiskLevel(audioInfo.getRiskLevel());
					}
				}
				
				newAudioUserInfo.setUserType(audioInfo.getUserType());
				newAudioUserInfo.setPhoneNo(audioInfo.getPhoneNo());
				try {
					DatabaseExt.executeNamedSql("default", UPDATE_USER_INFO,
							newAudioUserInfo);
				} catch (Exception e) {
					outMessage.set("returnCode",
							ReturnCode.DATABASE_SYSTEM_ERROR);
					outMessage.set("returnMsg", "更新用户信息失败！");
					logger.error("更新用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
					return outMessage;
				}
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "更新用户信息成功");
				return outMessage;

			} else {
				// 不存在,插入用户信息
				newAudioUserInfo = new AudioUserInfo();
				newAudioUserInfo.setUserName(audioInfo.getUserName());
				newAudioUserInfo.setCertNo(audioInfo.getCertNo());
				newAudioUserInfo.setCertType(audioInfo.getCertType());
				// 设置用户类型和手机号码
				newAudioUserInfo.setUserType(audioInfo.getUserType());
				newAudioUserInfo.setPhoneNo(audioInfo.getPhoneNo());

				// newAudioUserInfo.setFsAudioTotalDuration(audioInfo.getAudioDuration());
				if (Constants.LOW_RISK_LEVEL.equals(audioInfo.getRiskLevel())
						&& StringUtils.isNotBlank(audioInfo.getAudioDuration())) {
					newAudioUserInfo.setLowDuration(audioInfo
							.getAudioDuration());
				} else if (Constants.MIDDLE_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())
						&& StringUtils.isNotBlank(audioInfo.getAudioDuration())) {
					newAudioUserInfo.setMiddleDuration(audioInfo
							.getAudioDuration());
				} else if (Constants.HIGH_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())
						&& StringUtils.isNotBlank(audioInfo.getAudioDuration())) {
					newAudioUserInfo.setHighDuration(audioInfo
							.getAudioDuration());
				} else if (Constants.SUPER_HIGH_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())
						&& StringUtils.isNotBlank(audioInfo.getAudioDuration())) {
					newAudioUserInfo.setSuperHighDuration(audioInfo
							.getAudioDuration());
				} else if (Constants.SPECIAL_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())
						&& StringUtils.isNotBlank(audioInfo.getAudioDuration())) {
					newAudioUserInfo.setSpecialDuraion(audioInfo
							.getAudioDuration());
				}else if (Constants.PDA_RISK_LEVEL.equals(audioInfo
						.getRiskLevel())
						&& StringUtils.isNotBlank(audioInfo.getAudioDuration())) {
					newAudioUserInfo.setPdaDuration(audioInfo
							.getAudioDuration());
				}

				newAudioUserInfo.setCrtChannel(audioInfo.getChannel());
				
				//更新最高风险等级
				if(StringUtils.isNotBlank(audioInfo.getRiskLevel()) && StringUtils.isNotBlank(audioInfo.getAudioDuration()) ) {
					//表明历史最高风险等级为空
					int time = Integer.parseInt(audioInfo.getAudioDuration());
					if(time>=30){
						newAudioUserInfo.setMaxRiskLevel(audioInfo.getRiskLevel());
					}
					
				}
				
				logger.error("newAudioUserInfo---> " + newAudioUserInfo);
				try {
					DatabaseExt.executeNamedSql("default", INSERT_USER_INFO,
							newAudioUserInfo);
				} catch (Exception e) {

					outMessage.set("returnCode",
							ReturnCode.DATABASE_SYSTEM_ERROR);
					outMessage.set("returnMsg", "保存用户信息失败！");
					logger.error("保存用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
					return outMessage;
				}
				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
				outMessage.set("returnMsg", "保存用户信息成功");
				return outMessage;

			}
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", "根据用户证件号和证件类型查询用户信息数据库操作异常！");
			logger.error("根据用户证件号和证件类型查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}
	}

	@Bizlet("查询用户上传的语音文件和文件属性")
	public DataObject queryUserAudioInfo(DataObject inMessage,
			DataObject outMessage, DataObject pageCond) {
		logger.error("查询用户上传的语音文件和文件属性");

		AudioInfoVO audioInfoVO = new AudioInfoVO();

		// 证件号和证件类型必输
		if (StringUtils.isBlank(inMessage.getString("certType"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}
		if (StringUtils.isBlank(inMessage.getString("certNo"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}

		if (StringUtils.isNotBlank(inMessage.getString("userName"))) {
			audioInfoVO.setUserName(inMessage.getString("userName"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certType"))) {
			audioInfoVO.setCertType(inMessage.getString("certType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certNo"))) {
			audioInfoVO.setCertNo(inMessage.getString("certNo").toUpperCase());
		}

		if (StringUtils.isNotBlank(inMessage.getString("channel"))) {
			audioInfoVO.setChannel(inMessage.getString("channel"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("businessType"))) {
			audioInfoVO.setBusinessType(inMessage.getString("businessType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("closeAudioFormat"))) {
			audioInfoVO.setCloseAudioFormat(inMessage
					.getString("closeAudioFormat"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("startDate"))) {
			audioInfoVO.setStartDate(inMessage.getString("startDate"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("endDate"))) {
			audioInfoVO.setEndDate(inMessage.getString("endDate"));
		}

		// 设置需要查询的表
		audioInfoVO.setTableName(getTableNameByCertNo(audioInfoVO.getCertNo(),
				audioInfoVO.getCertType()));

		Object[] objs = null;
		try {
			objs = DatabaseExt.queryByNamedSqlWithPage("default",
					QUERY_USER_AUDIO_INFO, pageCond, audioInfoVO);
			// objs = DatabaseExt.queryByNamedSql("default",
			// QUERY_USER_AUDIO_INFO, audioInfoVO);
			int i = 1;
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					AudioInfo tempAudioInfo = (AudioInfo) obj;
					outMessage.set("info[" + i + "]/userName", tempAudioInfo
							.getUserName());
					outMessage.set("info[" + i + "]/userType", tempAudioInfo
							.getUserType());
					outMessage.set("info[" + i + "]/phoneNo", tempAudioInfo
							.getPhoneNo());
					outMessage.set("info[" + i + "]/certType", tempAudioInfo
							.getCertType());
					outMessage.set("info[" + i + "]/certNo", tempAudioInfo
							.getCertNo());
					outMessage.set("info[" + i + "]/userUniqueId",
							tempAudioInfo.getUserUniqueId());
					outMessage.set("info[" + i + "]/channel", tempAudioInfo
							.getChannel());
					outMessage.set("info[" + i + "]/businessType",
							tempAudioInfo.getBusinessType());
					outMessage.set("info[" + i + "]/riskLevel", tempAudioInfo
							.getRiskLevel());
					// outMessage.set("info[" + i+
					// "]/closeAudio",tempAudioInfo.getCloseAudio());
					outMessage.set("info[" + i + "]/closeAudioFormat",
							tempAudioInfo.getCloseAudioFormat());
					outMessage.set("info[" + i + "]/transDate", tempAudioInfo
							.getCrtDate());
					outMessage.set("info[" + i + "]/transTime", tempAudioInfo
							.getCrtTime());
					outMessage.set("info[" + i + "]/audioDuration",
							tempAudioInfo.getAudioDuration());
					outMessage.set("info[" + i + "]/samplingRate",
							tempAudioInfo.getSamplingRate());
					outMessage.set("info[" + i + "]/bitRate", tempAudioInfo
							.getBitRate());
					outMessage.set("info[" + i + "]/soundTrack", tempAudioInfo
							.getSoundTrack());
					outMessage.set("info[" + i + "]/codeRate", tempAudioInfo
							.getCodeRate());
					outMessage.set("info[" + i + "]/encodingFormat",
							tempAudioInfo.getEncodingFormat());
					outMessage.set("info[" + i + "]/audioPath", tempAudioInfo
							.getAudioPath());
					outMessage.set("info[" + i + "]/audioId", tempAudioInfo
							.getAudioId());

					if (StringUtils.isNotBlank(tempAudioInfo.getExtName())) {
						outMessage.set("info[" + i + "]/extName", tempAudioInfo
								.getExtName());
					} else {
						outMessage.set("info[" + i + "]/extName", "");
					}

					i++;
				}
			} else {
				outMessage.set("returnCode", ReturnCode.QUERY_ISNULL);
				outMessage.set("returnMsg", "查询用户语音文件记录数为零！");
				return outMessage;
			}
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("查询用户语音文件数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "查询用户语音文件成功");
		return outMessage;
	}

	@Bizlet("查询语音导入交易流水")
	public DataObject queryAudioTransDetail(DataObject inMessage,
			DataObject outMessage, DataObject pageCond) {
		logger.error("查询语音导入交易流水");

		TransDetailModelVO transDetailModelVO = new TransDetailModelVO();
		// 证件号和证件类型必输
		// if (StringUtils.isBlank(inMessage.getString("certType"))) {
		// outMessage.set("returnCode",ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
		// outMessage.set("returnMsg", "输入参数必输项丢失");
		// return outMessage;
		// }
		// if (StringUtils.isBlank(inMessage.getString("certNo"))) {
		// outMessage.set("returnCode",Re--------------------------------------turnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
		// outMessage.set("returnMsg", "输入参数必输项丢失");
		// return outMessage;
		// }

		if (StringUtils.isNotBlank(inMessage.getString("userName"))) {
			transDetailModelVO.setUserName(inMessage.getString("userName"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certType"))) {
			transDetailModelVO.setCertType(inMessage.getString("certType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certNo"))) {
			transDetailModelVO.setCertNo(inMessage.getString("certNo")
					.toUpperCase());
		}

		if (StringUtils.isNotBlank(inMessage.getString("channel"))) {
			transDetailModelVO.setChannel(inMessage.getString("channel"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("businessType"))) {
			transDetailModelVO.setBusinessType(inMessage
					.getString("businessType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("closeAudioFormat"))) {
			transDetailModelVO.setCloseAudioFormat(inMessage
					.getString("closeAudioFormat"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("startDate"))) {
			transDetailModelVO.setStartDate(inMessage.getString("startDate"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("endDate"))) {
			transDetailModelVO.setEndDate(inMessage.getString("endDate"));
		}

		Object[] objs = null;
		try {
			objs = DatabaseExt.queryByNamedSqlWithPage("default",
					QUERY_AUDIO_TRANS_DETAIL, pageCond, transDetailModelVO);
			// objs = DatabaseExt.queryByNamedSql("default",
			// QUERY_USER_AUDIO_INFO, audioInfoVO);
			int i = 1;
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					TransDetailModel tempTransDetailModel = (TransDetailModel) obj;
					outMessage.set("info[" + i + "]/userName",
							tempTransDetailModel.getUserName());
					outMessage.set("info[" + i + "]/userType",
							tempTransDetailModel.getUserType());
					outMessage.set("info[" + i + "]/phoneNo",
							tempTransDetailModel.getPhoneNo());
					outMessage.set("info[" + i + "]/certType",
							tempTransDetailModel.getCertType());
					outMessage.set("info[" + i + "]/certNo",
							tempTransDetailModel.getCertNo());
					outMessage.set("info[" + i + "]/userUniqueId",
							tempTransDetailModel.getUserUniqueId());
					outMessage.set("info[" + i + "]/channel",
							tempTransDetailModel.getChannel());
					outMessage.set("info[" + i + "]/businessType",
							tempTransDetailModel.getBusinessType());
					outMessage.set("info[" + i + "]/riskLevel",
							tempTransDetailModel.getRiskLevel());
					outMessage.set("info[" + i + "]/returnCode",
							tempTransDetailModel.getReturnCode());
					outMessage.set("info[" + i + "]/returnMsg",
							tempTransDetailModel.getReturnMsg());
					outMessage.set("info[" + i + "]/transDate",
							tempTransDetailModel.getTransDate());
					outMessage.set("info[" + i + "]/transTime",
							tempTransDetailModel.getTransTime());

					i++;
				}
			} else {
				outMessage.set("returnCode", ReturnCode.QUERY_ISNULL);
				outMessage.set("returnMsg", "查询语音导入交易流水记录数为零！");
				return outMessage;
			}
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("查询语音导入交易流水数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "查询用户语音文件成功");
		return outMessage;
	}

	@Bizlet("查询语音文件")
	public DataObject downloadAudioFile(DataObject inMessage,
			DataObject outMessage) {
		logger.error("查询语音文件");

		if (StringUtils.isBlank(inMessage.getString("audioPath"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}

		String audioPath = inMessage.getString("audioPath");
		try {
			// ToudaDFSClient client=ToudaDFSClient.getInstance();
			// byte[] temp=client.download(audioId);

			byte[] temp = FastDFSClientAlone.download(audioPath);
			if (temp == null || (temp.length == 0)) {
				logger.error("语音文件可能被删除了");
				outMessage.set("returnCode",
						ReturnCode.GENERAL_SYSTEM_EXCEPTION);
				outMessage.set("returnMsg", "语音文件可能被删除了,文件名为=" + audioPath);
				return outMessage;
			}
			logger.error("文件的大小为--->" + temp.length);
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encode(temp);
			// generateAudio(base64, "C:\\Users\\A146901\\Desktop\\1.amr");
			outMessage.set("closeAudio", base64);
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.GENERAL_SYSTEM_EXCEPTION);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("查询用户语音文件数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "查询语音文件成功");
		return outMessage;
	}

	@Bizlet("查看用户语音统计信息")
	public DataObject queryUserAudioDetail(DataObject inMessage,
			DataObject outMessage, DataObject pageCond) {
		logger.error("查看用户语音统计信息");

		AudioUserInfo audioUserInfo = new AudioUserInfo();

		if (StringUtils.isNotBlank(inMessage.getString("userName"))) {
			audioUserInfo.setUserName(inMessage.getString("userName"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certType"))) {
			audioUserInfo.setCertType(inMessage.getString("certType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certNo"))) {
			audioUserInfo
					.setCertNo(inMessage.getString("certNo").toUpperCase());
		}

		Object[] objs = null;
		try {
			objs = DatabaseExt.queryByNamedSqlWithPage("default",
					QUERY_USER_INFO, pageCond, audioUserInfo);
			// objs = DatabaseExt.queryByNamedSql("default",
			// QUERY_USER_AUDIO_INFO, audioInfoVO);
			int i = 1;
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					AudioUserInfo tempAudioUserInfo = (AudioUserInfo) obj;
					outMessage.set("info[" + i + "]/ID", tempAudioUserInfo
							.getId());
					outMessage.set("info[" + i + "]/userName",
							tempAudioUserInfo.getUserName());
					outMessage.set("info[" + i + "]/userType",
							tempAudioUserInfo.getUserType());
					outMessage.set("info[" + i + "]/phoneNo", tempAudioUserInfo
							.getPhoneNo());
					outMessage.set("info[" + i + "]/certType",
							tempAudioUserInfo.getCertType());
					outMessage.set("info[" + i + "]/certNo", tempAudioUserInfo
							.getCertNo());
					outMessage.set("info[" + i + "]/crtChannel",
							tempAudioUserInfo.getCrtChannel());
					outMessage.set("info[" + i + "]/crtTime", tempAudioUserInfo
							.getCrtTime());
					outMessage.set("info[" + i + "]/crtDate", tempAudioUserInfo
							.getCrtDate());
					// outMessage.set("info[" + i+
					// "]/fsAudioTotalDuration",tempAudioUserInfo.getFsAudioTotalDuration());
					outMessage.set("info[" + i + "]/lAudioDuration",
							tempAudioUserInfo.getLowDuration());
					outMessage.set("info[" + i + "]/mAudioDuration",
							tempAudioUserInfo.getMiddleDuration());
					outMessage.set("info[" + i + "]/hAudioDuration",
							tempAudioUserInfo.getHighDuration());
					outMessage.set("info[" + i + "]/shAudioDuration",
							tempAudioUserInfo.getSuperHighDuration());
					outMessage.set("info[" + i + "]/spAudioDuration",
							tempAudioUserInfo.getSpecialDuraion());
					outMessage.set("info[" + i + "]/audioIds",
							tempAudioUserInfo.getAudioIds());
					outMessage.set("info[" + i + "]/isFsRegistered",
							tempAudioUserInfo.getIsFsRegistered());
					outMessage.set("info[" + i + "]/lstUpdTime",
							tempAudioUserInfo.getLstUpdTime());
					outMessage.set("info[" + i + "]/lstUpdDate",
							tempAudioUserInfo.getLstUpdDate());
					i++;
				}
			} else {
				outMessage.set("returnCode", ReturnCode.QUERY_ISNULL);
				outMessage.set("returnMsg", "查看用户语音统计信息记录数为零！");
				return outMessage;
			}
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("查看用户语音统计信息数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "查看用户语音统计信息成功");
		return outMessage;
	}

	private static String getTableNameByCertNo(String certNo, String certType) {
		String tableName = "TBL_VPRS_FS_AUDIO_INFO_";

		if (!"0".equals(certType)) {
			// 表明不是身份证
			tableName += "OTHER";
		} else {
			tableName += certNo.substring(certNo.length() - 1);
		}

		return tableName;
	}

	// 检测语音属性
	private static void detectAudioInfo(AudioInfo audioInfo, String ffmpegPath,
			String filePath) {
		List<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegPath);
		cmd.add("-i");
		cmd.add(filePath);

		logger.error("cmd--->" + cmd);
		List<String> strs = exec(cmd);
		dealString(audioInfo, strs);

	}

	private static List<String> exec(List<String> cmd) {
		logger.error("调用ffmpeg命令检测语音文件属性");
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(cmd);
		builder.redirectErrorStream(true);
		Process proc = null;
		try {
			proc = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader stdout = new BufferedReader(new InputStreamReader(proc
				.getInputStream()));
		List<String> strs = new ArrayList<String>();
		String line;
		try {
			while ((line = stdout.readLine()) != null) {
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

	private static void dealString(AudioInfo audioInfo, List<String> strs) {
		// 封装音频信息
		logger.error("封装语音文件属性到AudioInfo对象");
		final String DURATION = "Duration";
		final String BITRATE = "bitrate:";
		final String AUDIO = "Audio";
		final String SAMPLERATE = "Hz";
		final String CODERATE = "kb/s";
		logger.error("音频信息--->" + strs);

		for (String string : strs) {
			if (string.contains(DURATION)) {
				String bak = string.substring(string.indexOf(DURATION)
						+ DURATION.length());
				String duration = bak.substring(bak.indexOf(":") + 1,
						bak.indexOf(",")).trim();
				if (StringUtils.isNotBlank(duration)) {
					audioInfo.setAudioDuration(getTotalSecond(duration));
				}
			}

			// 设置音频编码格式
			if (string.contains(AUDIO)) {
				String bak = string.substring(string.indexOf(AUDIO)
						+ AUDIO.length());
				String audio = bak.substring(bak.indexOf(":") + 1,
						bak.indexOf(",")).trim();
				audio = audio.split(" ")[0].trim();
				if (StringUtils.isNotBlank(audio)) {
					audioInfo.setEncodingFormat(audio);
				}

			}

			// 设置音频声道
			if (string.contains(AUDIO)) {
				String soundTrack = string.split(",")[2].trim();
				audioInfo.setSoundTrack(soundTrack);
			}

			// 设置采样率
			if (string.contains(SAMPLERATE)) {
				String sampleRate = string.split(",")[1].trim().split(" ")[0]
						.trim();
				if (StringUtils.isNotBlank(sampleRate)) {
					audioInfo.setSamplingRate(sampleRate);
				}
			}

			// 设置码率
			if (string.contains(BITRATE)) {
				logger.error("string--->" + string);
				String bak = string.substring(string.indexOf(BITRATE)
						+ BITRATE.length());
				String codeRate = bak.substring(bak.indexOf(":") + 1,
						bak.indexOf("kb")).trim();
				if (StringUtils.isNotBlank(codeRate)) {
					audioInfo.setCodeRate(codeRate);
				}
			}
		}

		// 设置比特率
		if (StringUtils.isNotBlank(audioInfo.getSamplingRate())
				&& StringUtils.isNotBlank(audioInfo.getCodeRate())) {
			int bitRate = Integer.parseInt(audioInfo.getCodeRate()) * 1000
					/ Integer.parseInt(audioInfo.getSamplingRate());
			audioInfo.setBitRate(bitRate + "");
		}

		// System.out.println("audioInfo--->"+audioInfo);
	}

	// 将时间格式转化为秒
	private static String getTotalSecond(String string) {
		return (Integer.parseInt(string.substring(0, 2)) * 60 * 60
				+ Integer.parseInt(string.substring(3, 5)) * 60 + Integer
				.parseInt(string.substring(6, 8)))
				+ "";
	}

	// 通过uuid值创建临时音频路径字符串
	public static String crtAudioPathByUUID(String uuid) {
		final String audioFileSuffix = ".wav";
		String separator = File.separator;
		// String filePath =
		// BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.TMP_AUDIO_PATH + separator + uuid + audioFileSuffix;
	}

	public static boolean generateAudio(String audioStr, String audioFilePath) {
		if (audioStr == null) {
			return false;
		}
		boolean flag = false;
		OutputStream out = null;
		try {

			byte[] b = Base64.decodeBase64(new String(audioStr).getBytes());

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成JPEG图片
			out = new FileOutputStream(audioFilePath);
			out.write(b);
			flag = true;
		} catch (Exception e) {
			System.out
					.println("根据文件base64字符串在指定路径解码生成对应文件异常：" + e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				System.out.println("根据文件base64字符串在指定路径解码生成对应文件，关闭文件异常："
						+ e.getMessage());
			}
		}
		return flag;
	}
}
