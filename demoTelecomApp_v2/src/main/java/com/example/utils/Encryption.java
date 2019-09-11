package com.example.utils;


import java.security.SecureRandom;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 建议使用DESencode（String res）加密，如对密钥有更改请告知效生。
 * 
 * @author leo
 *
 */

public class Encryption {

	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA1";
	public static final String HmacMD5 = "HmacMD5";
	public static final String HmacSHA1 = "HmacSHA1";
	public static final String DES = "DES";
	public static final String AES = "AES";
	public static final String KEY_VS = "654289";
	

	/** 编码格式；默认使用uft-8 */
	public static String charset = "utf-8";
	/** DES */
	public static int keysizeDES = 0;
	/** AES */
	public int keysizeAES = 128;

	/**
	 * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
	 * 
	 * @param res
	 *            加密的原文
	 * @param algorithm
	 *            加密使用的算法名称
	 * @param key
	 *            加密的秘钥
	 * @param keysize
	 * @param isEncode
	 * @return
	 */
	private static String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) {
		try {
			KeyGenerator kg = KeyGenerator.getInstance(algorithm);
			if (keysize == 0) {
				byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
				kg.init(new SecureRandom(keyBytes));
			} else if (key == null) {
				kg.init(keysize);
			} else {
				byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
				kg.init(keysize, new SecureRandom(keyBytes));
			}
			SecretKey sk = kg.generateKey();
			SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			if (isEncode) {
				cipher.init(Cipher.ENCRYPT_MODE, sks);
				byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
				return parseByte2HexStr(cipher.doFinal(resBytes));
			} else {
				cipher.init(Cipher.DECRYPT_MODE, sks);
				return new String(cipher.doFinal(parseHexStr2Byte(res)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 将二进制转换成16进制 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/** 将16进制转换为二进制 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 使用DES加密算法进行加密（可逆）
	 * 
	 * @param res
	 *            需要加密的原文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String DESencode(String res, String key) {
		return keyGeneratorES(res, DES, key, keysizeDES, true);
	}

	/**
	 * 对使用DES加密算法的密文进行解密（可逆）
	 * 
	 * @param res
	 *            需要解密的密文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String DESdecode(String res, String key) {
		return keyGeneratorES(res, DES, key, keysizeDES, false);
	}
	/**
	 * 效生科技公司默认使用KEY_VS为密钥加密
	 * @param res
	 * @return
	 */
	public static String DESencode(String res){
		return DESencode(res,KEY_VS);
		
	}
	/**
	 * 效生科技公司默认使用KEY_VS为密钥解密
	 * @param res
	 * @return
	 */
	public static String DESdecode(String res) {
		return DESdecode(res,KEY_VS);
		
	}
}
