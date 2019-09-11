package com.pccc.vprs.servicedisplay.bams.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.eos.foundation.database.DatabaseExt;
import com.pccc.vprs.servicedisplay.vprs.model.ChannelThreshold;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

public class VPRSDaoUtils {
	private static ILogger logger = LoggerFactory.getLogger(VPRSDaoUtils.class);
	/** 
	 * 根据渠道号、业务场景、生物识别类型查询声纹得分
	 * @date 2017年1月120日下午1:43:00 
	 * @param channel      渠道号
	 * @param businessType 业务场景
	 * @param biometricType生物识别类型 
	 * @return BigDecimal 
	 * 
	 */  
	public static BigDecimal queryThreshold(String channel,
			String businessType, String biometricType,String audioType) {
		BigDecimal threshold = BigDecimal.valueOf(0.0);		
		String qrySqlId = "com.pccc.vprs.servicecustom.sql.QueryThreshold.QueryChannelThreshold";	
		if (StringUtils.isBlank(channel) && StringUtils.isBlank(biometricType)) {
			return null;
		}
		try {
			ChannelThreshold Channelthreshold = new ChannelThreshold();
			Channelthreshold.setChannel(channel);
			Channelthreshold.setBusinessType(businessType);
			Channelthreshold.setBiometricType(biometricType);
			Object[] objs = DatabaseExt.queryByNamedSql("default", qrySqlId, Channelthreshold);
			logger.info("阈值信息："+objs.length+"---------"+objs.toString());
			if (objs != null && objs.length > 0) {
				for (Object obj : objs) {
					Channelthreshold = (ChannelThreshold) obj;
				}
			}else{
				return null;
			}
			logger.info(Channelthreshold.toString());
			//根据对比类型拿不同的阈值,固定文本阈值/随意说阈值
			if(audioType.equals("TD")){
				threshold = Channelthreshold.getThresholdTD();
			}
			else if(audioType.equals("FS")){
				threshold = Channelthreshold.getThresholdFS();
			}
		} catch (Exception e) {
			logger.info("查询阈值信息表数据库操作异常，异常信息:" + e.getMessage());
			throw new BTPRuntimeException("查询阈值信息表数据库操作异常，异常信息:" + e.getMessage(), e);
		}
		return threshold;
	}
	
//	public static BigDecimal queryThreshold(String channel,
//			String businessType, String biometricType,String compareType) {
//		BigDecimal threshold = BigDecimal.valueOf(0.0);
//		String qrySqlId = "com.pccc.touda.bams.servicecustom.sql.QueryThreshold.QueryChannelThreshold";	
//		if (StringUtils.isBlank(channel) && StringUtils.isBlank(biometricType)) {
//			return null;
//		}
//		try {
//			ChannelThreshold Channelthreshold = new ChannelThreshold();
//			Channelthreshold.setChannel(channel);
//			Channelthreshold.setBusinessType(businessType);
//			Channelthreshold.setBiometricType(biometricType);
//			Object[] objs = DatabaseExt.queryByNamedSql("default", qrySqlId, Channelthreshold);
//			logger.info("阈值信息："+objs.length+"---------"+objs.toString());
//			if (objs != null && objs.length > 0) {
//				for (Object obj : objs) {
//					Channelthreshold = (ChannelThreshold) obj;
//				}
//			}else{
//				return null;
//			}
//			logger.info(Channelthreshold.toString());
//			//根据对比类型拿不同的阈值
//			if(compareType.equals("1")){
//				threshold = Channelthreshold.getThreshold();
//			}else{
//				threshold = Channelthreshold.getThresholdBak();
//			}
//		} catch (Exception e) {
//			logger.info("查询阈值信息表数据库操作异常，异常信息:" + e.getMessage());
//			throw new BTPRuntimeException("查询阈值信息表数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		return threshold;
//	}
	
}
