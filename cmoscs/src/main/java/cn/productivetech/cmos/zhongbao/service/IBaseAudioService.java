package cn.productivetech.cmos.zhongbao.service;

import java.util.List;

/**
 * 验证音频和注册音频通用方法类
 * @author Administrator
 * @created 2019-04-17
 * @param <T>
 */
public interface IBaseAudioService<T> {

	/**
	 * 下载音频文件方法
	 * @param date
	 */
	void downloadAudio(List<T> date);

	/**
	 * 处理声纹类(语音处理模块)
	 * @param pAudioItem
	 */
	void processAudio(T audioEntity);
	
	/**
	 * 批量保存声纹基本信息,保存失败时，全部抛出异常
	 * @param dataAudioItems
	 */
	void addAudioEntitys(List<T> data);
	
	/**
	 * 批量更新声纹下载状态
	 * @param date
	 */
	void updateDownloadedStatus(List<T> date);
	
	/**
	 * 批量更新声纹下载状态为下载中
	 * @param data
	 */
	void updateDownloadingStatus(List<T> data);
	
	/**
	 * 批量更新处理状态
	 * @param date
	 */
	void updateProcessStatus(List<T> date);
	
	/**
	 * 解析txt接续文件，得到样本语音信息
	 * @param path	接续TXT文件路径
	 * @return 
	 */
	List<T> parseAudio(String path);
	
	/**
	 * 查询所有待处理的音频信息集合
	 * @return
	 */
	List<T> queryAudioItemsByUnHandle();


}
