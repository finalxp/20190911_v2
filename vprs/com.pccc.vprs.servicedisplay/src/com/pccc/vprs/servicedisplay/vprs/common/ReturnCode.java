package com.pccc.vprs.servicedisplay.vprs.common;

public class ReturnCode {
	/** 成功 */
	public static final String TOUDA_SUCCESS = "000000";
	/** 失败 */
	public static final String TOUDA_FAIL = "999999";
	/** 系统连接调用成功，获取信息失败 */
	public static final String SERVER_FAIL = "010000";
	/** 调用成功，返回报文为空 */
	public static final String MESSAGE_ISNULL_FAIL = "010001";
	/** 调用成功，查询数据不存在 */
	public static final String QUERY_ISNULL = "010002";
	/** 更新或新增操作时数据已存在，返回等幂结果 */
	public static final String SQL_DATE_IS_EXIST = "020000";
	//校验异常
	/** 输入参数必输项丢失 */
	public static final String REQUIRED_FIELD_ISEMPTY_ERROR = "110000";
	/** 输入参数异常 */
	public static final String REQUIRED_FIELD_ERROR = "110001";
	/** 输入参数必输项冗余 */
	public static final String REQUIRED_FIELD_ISFULL_ERROR = "110002";
	/** 通用数据转换异常 */
	public static final String GENERAL_DATA_CONVERSION_ERROR = "120000";
	/** 图片质量分析不通过 */
	public static final String QUALITY_ANALY_CHECK_ERROR = "120001";
	/** 获取阈值异常 */
	public static final String THRESHOLD_GET_ERROR  = "120002";
	/** 渠道不存在 */
	public static final String CHANNEL_NOT_EXIT  = "120003";
	
	//用户类
	/** 通用用户类操作异常 */
	public static final String GENERAL_USER_EXCEPTION = "220000";
	/** 用户已被临时冻结 */
	public static final String USER_TEMPORARILY_FROZEN = "220001";
	/** 用户已被管理员冻结 */
	public static final String USER_FROZEN_ADMINISTRATOR = "220002";
	/** 用户已注销 */
	public static final String USER_LOGOUT = "220003";
	/** 用户未激活*/
	public static final String USER_NOT_ACTIVE = "220004";
	/** 证件号已存在其他用户*/
	public static final String CERT_USER_EXIT= "220005";
	/** 用户不存在*/
	public static final String USER_NOT_EXIT= "220006";
	/** 用户已存在，不能重复注册*/
	public static final String USER_EXIT= "220007";
	/** 用户已存在，状态异常*/
	public static final String USER_STATUS_ERROR= "220008";
	/** 用户近照非本人*/
	public static final String USER_CLOSE_IMG_ERROR= "220009";
	
	
	//系统类异常
	/** 通用非预期程序运行时异常 */
	public static final String GENERIC_UNEXPECTED_RUNTIME_EXCEPTION = "310000";
	/** 线程池满 */
	public static final String FULL_THREAD_POOL_EXCEPTION = "310002";
	/** 应用程序锁获取失败 */
	public static final String APPLICATION_LOCK_ACCESS_FAILED = "310003";
	/** 通用系统异常 */
	public static final String GENERAL_SYSTEM_EXCEPTION = "320000";
	/** 文件系统异常 */
	public static final String FILE_SYSTEM_EXCEPTION = "320001";
	/** 网络通讯异常 */
	public static final String NETWORK_COMMUNICATION_EXCEPTION = "320002";
	/** 时间格式转换异常 */
	public static final String TIME_FORMAT_CONVERSION_EXCEPTION = "320003";
	/** 通用外部系统调用异常*/
	public static final String GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION = "330000";
	/** 数据库系统错误或操作异常 */
	public static final String DATABASE_SYSTEM_ERROR = "330001";
	/** 消息队列服务调用异常 */
	public static final String MESSAGE_QUEUING_CALL_EXCEPTION = "330002";
	/** 缓存操作异常 */
	public static final String CACHE_OPERATION_EXCEPTION = "330003";
	/** ESB服务调用异常 */
	public static final String ESB_SERVICE_CALL_EXCEPTION = "330004";
	/** 图片找不到异常 */
	public static final String FILE_NOT_FOUND_EXCEPTION = "330005";
	/** 图片找不到异常 */
	public static final String FILE_OPERATION_EXCEPTION = "330006";
	
	
	
	
	/** 文件已经存在，请勿重复上传 */
	public static final String FILE_ALREADY_EXIST = "010003";
	
	
	/** 语音时长过短 */
	public static final String AUDIO_DURATION_SHORT = "010004";
}
	