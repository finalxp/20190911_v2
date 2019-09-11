package com.pccc.vprs.servicedisplay.bams.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/*
	 *	@Description 用于生成时间戳 
	 */
	public static String generateTimestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());		
	}
}
