package cn.productivetech.shtelcom.enrol.utils;

import java.util.List;

import org.apache.commons.codec.binary.Base64;

/**
 * 通用辅助类
 * 
 * @author brain
 *
 */
public class Utils {

	/**
	 * 将二进制数组转换成Base64 使用 BASE64Encoder
	 * 
	 * @param data
	 *            二进制数据
	 * @return 转后的Base64字符串
	 */
	public static String byteArrayToBase64(byte[] data) {
		return Base64.encodeBase64String(data);
	}

	/**
	 * 将Base64字符串转换成二进制数据
	 * 
	 * @param base64
	 *            Base64字符串
	 * @return 转换后二进制数据
	 */
	public static byte[] base64ToByteArray(String base64) {
		return Base64.decodeBase64(base64);
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().length() == 0)
			return true;
		return false;
	}

	public static boolean isNullOrEmpty(List<?> list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}
}
