package com.pccc.vprs.servicecustom.exception;

/**
 * core模块的异常码.<br>
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */
public class ExceptionCodes {
	/**
	 * 空指针异常.<br>
	 */
	public static final String NULL_POINTER = R_EX_CODE("0001");

	/**
	 * 必输字段为空异常<br>
	 */
	public static final String REQUIRE_NULL = R_EX_CODE("0002");
	/**
	 * 主机服务号为空<br>
	 */
	public static final String HOST_NULL = R_EX_CODE("0003");

	/**
	 * ESB接口调用异常前缀
	 */
	private static final String R_EX_PREFIX = "6001";

	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + code;
	}
}
