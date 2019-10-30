package cn.productivetech.shtelcom.enrol.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEnc {

	private static String password;

	private static String ivParameter;

	private static final AesEnc _ins = new AesEnc();

	private AesEnc() {
		init("shtelcom9922333!@#");
	}

	public static AesEnc getIns() {
		return _ins;
	}

	public void init(String key) {
		try {
			password = Md5Enc.enc16(key);
			ivParameter = Md5Enc.enc16(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String content) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = password.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
			String encoded = Utils.byteArrayToBase64(encrypted);// 此处使用BASE64做转码。
			return encoded;
		} catch (Exception e) {
			throw e;
		}
	}

	// 解密
	public String decrypt(String sSrc) throws Exception {
		try {
			byte[] raw = password.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Utils.base64ToByteArray(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "UTF-8");
			return originalString;
		} catch (Exception ex) {
			throw ex;
		}
	}

	// 解密
	public byte[] decrypt(byte[] content) throws Exception {
		try {
			byte[] raw = password.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(content);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
