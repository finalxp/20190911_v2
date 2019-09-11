package com.pccc.vprs.servicecustom.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.safehaus.uuid.UUIDGenerator;

import com.pccc.vprs.servicecustom.constants.VprsConstant;

public class VPRSUtils {
	/**
	 * 判断输入的Map是否为空
	 * @param map
	 * @return
	 */
	public static boolean checkMapEmpty(Map<String, Object> map){
		if (map == null || map.isEmpty()) {
			return false;
		} else {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				Object value = map.get(key);
				if (value != null && StringUtils.isNotBlank(value.toString())) return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否都不为空
	 * @param map
	 * @return
	 */
	public static boolean checkRequiredParam(Map<String, Object> map){
		if (map == null || map.isEmpty()) {
			return false;
		}else{
			Set<String> keys = map.keySet();
			for (String key : keys) {
				Object value = map.get(key);
				if(value==null||StringUtils.isBlank(value.toString())){
					return false;
				}
			}
			return true;
		}
	}
	/**
	 * 自动生成UUID
	 * @return
	 */
	public static String getUUID(){
		UUIDGenerator uuid = UUIDGenerator.getInstance();
		return  uuid.generateRandomBasedUUID().toString();
	}
	/**
	 * 生成UUID对应的HashCode
	 * @param args
	 */
	public static String getHashCode(String UUID){
		return String.valueOf(Integer.toHexString(UUID.hashCode()));
	}
	/**
	 * 将hashCode值处理成路径字符串
	 * @param hashCode
	 */
	public static String getPathByHashCode(String hashCode){
//   	 根据系统的实际情况选择目录分隔符（windows下是\，linux下是/）
		String separator = File.separator;
		StringBuilder builder= new StringBuilder();
		for(int i =0;i<hashCode.length();i++){
			builder.append(separator);
			builder.append(hashCode.charAt(i));
		}
		return builder.toString();
	}
	
	/**
	 * 获取当天日期字符串
	 * @param hashCode
	 */
	public static String getTime(String format) {
		String fileDate = DateFormatUtils.format(new Date(), format);   
		return fileDate;
	}
	
	
	/**
	 * 获BigDecimal数组最大值的下标
	 * @param hashCode
	 */
    public static int getMaxIndex(BigDecimal[] arr){  
        int maxIndex = 0;   //获取到的最大值的角标  
        for(int i=0; i<arr.length; i++){  
            if(arr[i].compareTo(arr[maxIndex])>=0){  
                maxIndex = i;  
            }  
        }  
        return maxIndex;  
    }        
		
    
    /**
	 * 将Object类型转为BigDecimal
	 * @param value
	 */
    public static BigDecimal convertObject2BigDecimal(Object value){
		 BigDecimal ret = null;
        if( value != null ) {
              if( value instanceof BigDecimal ) {
                     ret = (BigDecimal) value;
                } else if( value instanceof String ) {
                     ret = new BigDecimal( (String) value );
                } else if( value instanceof BigInteger ) {
                     ret = new BigDecimal( (BigInteger) value );
                } else if( value instanceof Number ) {
               	  DecimalFormat df = new DecimalFormat("######0.00");   
               	  String s = df.format(((Number)value).doubleValue());
                     ret = new BigDecimal(s);
                } else {
                     throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
                }
        }
       return ret;
	}
    
    
    
    /**
	 * 通过uuid值创建音频路径字符串
	 */
	public static String crtAudioPathByUUID(String uuid) {
		final String audioFileSuffix = ".wav";
		
		String separator = File.separator;
		//String filePath = BAMSUtils.getPathByHashCode(BAMSUtils.getHashCode(uuid));
		return VprsConstant.TMP_AUDIO_PATH + separator + uuid + audioFileSuffix;
	}
}
