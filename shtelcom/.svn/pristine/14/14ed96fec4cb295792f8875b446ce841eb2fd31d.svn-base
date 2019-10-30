package cn.productivetech.shtelcom.enrol.utils;

import cn.productivetech.shtelcom.enrol.model.UserBean;
import cn.productivetech.shtelcom.enrol.utils.AesEnc;
import cn.productivetech.shtelcom.enrol.utils.Md5Enc;

public class TokenUtils {

	private static final int TOKEN_EXPIRED_TIME = 7 * 24 * 3600 * 1000;

	public static String createToken(UserBean ub) throws Exception {
		return AesEnc.getIns().encrypt(
				ub.getUserLoginName() + ":" + Md5Enc.enc(ub.getUserLoginName()) + ":"
						+ genExpireTime());
	}

	private static long genExpireTime() {
		return System.currentTimeMillis() + TOKEN_EXPIRED_TIME;
	}

}
