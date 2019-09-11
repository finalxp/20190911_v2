package cn.productivetech.cmos.zhongbao.service;

/**
 * 声纹注册、验证、更新接口
 * @author Administrator
 * @created 2019-04-17
 */
public interface IVoiceBiometricService {

	/**
	 * 注册音频
	 * @param path			要注册音频的路径
	 * @param format		要注册音频格式
	 * @param sampleRate	要注册音频采样率
	 * @return				注册结果
	 */
	float[] enrollSpeaker(String path, String format, int sampleRate);
	
	/**
	 * 更新音频模型
	 * @param path			更新音频的文件路径
	 * @param formate		更新音频的格式
	 * @param sampleRate	更新音频的采样率
	 * @param voicePrint	以前的声纹模型
	 * @return				更新结果
	 */
	float[] updateSpeaker(String path, String formate, int sampleRate, float[] voicePrint);
	
	/**
	 * 验证音频
	 * @param path			验证音频的文件路径
	 * @param formate		验证音频的格式
	 * @param sampleRate	验证音频采样率
	 * @param voicePrint	样本声纹模型
	 * @return				要验证的音频的得分
	 */
	float verifySpeaker(String path, String formate, int sampleRate, float[] voicePrint);
	
}
