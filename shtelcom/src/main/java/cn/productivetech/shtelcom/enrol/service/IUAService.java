package cn.productivetech.shtelcom.enrol.service;

import cn.productivetech.shtelcom.enrol.uarest.core.UAException;

public interface IUAService {

	/**
	 * 声纹注册
	 * 
	 * @param userId
	 *            注册用户ID
	 * @param data
	 *            音频数据去头
	 * @param times
	 *            第几次注册,从0开始
	 * @throws UAException
	 *             异常
	 */
	void enrolSpeaker(String userId, byte[] data, int times) throws UAException;

}
