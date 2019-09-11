/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio;

import org.apache.commons.lang.StringUtils;

import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;
import com.pccc.vprs.servicedisplay.bams.model.AudioInfo;
import com.pccc.vprs.servicedisplay.bams.model.AudioInfoVO;
import com.pccc.vprs.servicedisplay.bams.model.AudioUserInfo;
import com.pccc.vprs.servicedisplay.vprs.common.ReturnCode;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

import commonj.sdo.DataObject;

/**
 * @author pccc
 * @date 2018-10-09 14:39:07
 *
 */
@Bizlet("声纹跑批注册")
public class AudioFsRegisterBatch {
	private static ILogger logger = LoggerFactory.getLogger(AudioFsRegisterBatch.class);

	private static String QUERY_USER_INFO_BATCH = "com.pccc.vprs.servicecustom.sql.AudioCollect.queryUserInfoBatch"; // 批量查询用户信息
	
	private static String QUERY_USER_AUDIO_INFO_BATCH = "com.pccc.vprs.servicecustom.sql.AudioCollect.queryUserAudioInfoBatch"; // 查询用户语音文件信息

	private static String UPDATE_USER_INFO_BATCH = "com.pccc.vprs.servicecustom.sql.AudioCollect.updateUserInfoBatch"; // 更新用户信息

	private static String UPDATE_AUDIO_INFO_BATCH = "com.pccc.vprs.servicecustom.sql.AudioCollect.updateAudioInfoBatch"; // 更新用户信息

	
	@Bizlet("批量查询1000条用户记录")
	public DataObject queryUserInfo(DataObject inMessage,
			DataObject outMessage, AudioUserInfo audioUserInfo) {
		logger.info("批量查询1000条用户记录");

		Object[] objs = null;
		try {
			objs = DatabaseExt.queryByNamedSql("default", QUERY_USER_INFO_BATCH,audioUserInfo);
			System.out.println(objs.length);
			// objs = DatabaseExt.queryByNamedSql("default",
			// QUERY_USER_AUDIO_INFO, audioInfoVO);
			int i = 1;
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					AudioUserInfo tempTransDetailModel = (AudioUserInfo) obj;
					outMessage.set("info[" + i + "]/userName",
					tempTransDetailModel.getUserName());
					outMessage.set("info[" + i + "]/certNo",
							tempTransDetailModel.getCertNo());
					outMessage.set("info[" + i + "]/userType",
							tempTransDetailModel.getUserType());
					outMessage.set("info[" + i + "]/certType",
							tempTransDetailModel.getCertType());
					
					outMessage.set("info[" + i + "]/lAudioDuration",
							tempTransDetailModel.getLowDuration());
					outMessage.set("info[" + i + "]/mAudioDuration",
							tempTransDetailModel.getMiddleDuration());
					outMessage.set("info[" + i + "]/hAudioDuration",
							tempTransDetailModel.getHighDuration());
					outMessage.set("info[" + i + "]/shAudioDuration",
							tempTransDetailModel.getSuperHighDuration());
					outMessage.set("info[" + i + "]/spAudioDuration",
							tempTransDetailModel.getSpecialDuraion());
//					outMessage.set("info[" + i + "]/phoneNo",
//					tempTransDetailModel.getPhoneNo());
//					outMessage.set("info[" + i + "]/userUniqueId",
//							tempTransDetailModel.getUserUniqueId());
//					outMessage.set("info[" + i + "]/channel",
//							tempTransDetailModel.getChannel());
//					outMessage.set("info[" + i + "]/businessType",
//							tempTransDetailModel.getBusinessType());
//					outMessage.set("info[" + i + "]/riskLevel",
//							tempTransDetailModel.getRiskLevel());
//					outMessage.set("info[" + i + "]/returnCode",
//							tempTransDetailModel.getReturnCode());
//					outMessage.set("info[" + i + "]/returnMsg",
//							tempTransDetailModel.getReturnMsg());
//					outMessage.set("info[" + i + "]/transDate",
//							tempTransDetailModel.getTransDate());
//					outMessage.set("info[" + i + "]/transTime",
//							tempTransDetailModel.getTransTime());

					i++;
				}
			} else {
				outMessage.set("returnCode", ReturnCode.QUERY_ISNULL);
				outMessage.set("returnMsg", "查询用户信息记录数为零！");
				return outMessage;
			}
		} catch (Exception e) {
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "批量查询用户信息成功");
		return outMessage;
	}
	
	
	@Bizlet("根据证件号查询用户上传的语音文件和文件属性")
	public DataObject queryUserAudioInfo(DataObject inMessage,
			DataObject outMessage) {
		logger.error("根据证件号查询用户上传的语音文件和文件属性");

		AudioInfoVO audioInfoVO = new AudioInfoVO();

		// 证件号和证件类型和风险等级必输
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
		if (StringUtils.isBlank(inMessage.getString("certNo"))) {
			outMessage.set("returnCode",
					ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
			outMessage.set("returnMsg", "输入参数必输项丢失");
			return outMessage;
		}

		if (StringUtils.isNotBlank(inMessage.getString("userName"))) {
			audioInfoVO.setUserName(inMessage.getString("userName"));
		}
		
		if (StringUtils.isNotBlank(inMessage.getString("riskLevel"))) {
			audioInfoVO.setRiskLevel(inMessage.getString("riskLevel"));
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
			objs = DatabaseExt.queryByNamedSql("default",
					QUERY_USER_AUDIO_INFO_BATCH, audioInfoVO);
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
		outMessage.set("returnMsg", "根据证件号查询用户语音文件成功");
		return outMessage;
	}

	
	@Bizlet("更新用户信息表")
	public DataObject updateUserInfo(DataObject inMessage,
			DataObject outMessage, AudioUserInfo audioUserInfo) {
		logger.info("更新用户信息表");

		
		// 证件号和证件类型和风险等级必输
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

		
		if (StringUtils.isNotBlank(inMessage.getString("isFsRegistered"))) {
			audioUserInfo.setIsFsRegistered(inMessage.getString("isFsRegistered"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("regRiskLevel"))) {
			audioUserInfo.setRegRiskLevel(inMessage.getString("regRiskLevel"));
		}
		
	
		audioUserInfo.setCertNo(inMessage.getString("certNo"));
		audioUserInfo.setCertType(inMessage.getString("certType"));
		
		try {
			DatabaseExt.executeNamedSql("default", UPDATE_USER_INFO_BATCH, audioUserInfo);
		} catch (Exception e) {
//			e1.printStackTrace();
//			logger.error("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
//			throw new BTPRuntimeException("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("更新用户信息表数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
			
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "更新用户信息表成功");
		return outMessage;
	}
	
	@Bizlet("更新音频信息表")
	public DataObject updateAudioInfo(DataObject inMessage,
			DataObject outMessage) {
		logger.info("更新音频信息表");

		
		AudioInfoVO audioInfoVO = new AudioInfoVO();

		// 证件号和证件类型和风险等级必输
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


		if (StringUtils.isNotBlank(inMessage.getString("certType"))) {
			audioInfoVO.setCertType(inMessage.getString("certType"));
		}

		if (StringUtils.isNotBlank(inMessage.getString("certNo"))) {
			audioInfoVO.setCertNo(inMessage.getString("certNo"));
		}
		
		if (StringUtils.isNotBlank(inMessage.getString("audioId"))) {
			audioInfoVO.setAudioId(inMessage.getString("audioId"));
		}
		
		if (StringUtils.isNotBlank(inMessage.getString("isValid"))) {
			audioInfoVO.setIsValid(inMessage.getString("isValid"));
		}

		// 设置需要查询的表
		audioInfoVO.setTableName(getTableNameByCertNo(audioInfoVO.getCertNo(),
				audioInfoVO.getCertType()));
		
		
//		if (StringUtils.isNotBlank(inMessage.getString("isFsRegistered"))) {
//			audioUserInfo.setIsFsRegistered(inMessage.getString("isFsRegistered"));
//		}
//
//		if (StringUtils.isNotBlank(inMessage.getString("regRiskLevel"))) {
//			audioUserInfo.setRegRiskLevel(inMessage.getString("regRiskLevel"));
//		}
//		
//		audioUserInfo.setCertNo(inMessage.getString("certNo"));
//		audioUserInfo.setCertType(inMessage.getString("certType"));
		
		try {
			DatabaseExt.executeNamedSql("default", UPDATE_AUDIO_INFO_BATCH, audioInfoVO);
		} catch (Exception e) {
//			e1.printStackTrace();
//			logger.error("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
//			throw new BTPRuntimeException("更新用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			
			outMessage.set("returnCode", ReturnCode.DATABASE_SYSTEM_ERROR);
			outMessage.set("returnMsg", e.getMessage());
			logger.error("更新用户信息表数据库操作异常，异常信息:" + e.getMessage(), e);
			return outMessage;
			
		}

		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
		outMessage.set("returnMsg", "更新用户信息表成功");
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
	
	
}
