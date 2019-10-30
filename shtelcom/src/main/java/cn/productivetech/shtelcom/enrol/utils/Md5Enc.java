package cn.productivetech.shtelcom.enrol.utils;

import java.security.MessageDigest;

public class Md5Enc {
	
	public static String enc(String str) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		byte b[] = md.digest();

		int i;

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}

		return buf.toString();
	}

	public static String enc16(String str) throws Exception {
		String val = enc(str);
		return val.substring(8, 24);
	}
}
