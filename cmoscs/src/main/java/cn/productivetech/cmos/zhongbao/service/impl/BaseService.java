package cn.productivetech.cmos.zhongbao.service.impl;
/**
 * 注册验证通用方法
 * @author   kenny_peng
 * @created  2019年4月29日
 */
public class BaseService {
	
	/**
	 * 操作数据库失败时，抛出异常
	 * @param flag		受影响的行数,< 1 说明操作失败，抛出异常
	 * @param error		抛出错误原因
	 */
	protected void throwException(int flag, String error) {
		if (flag < 1) {
			try {
				throw new Exception(error);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
