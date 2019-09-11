package cn.productivetech.cmos.zhongbao.service;

import org.springframework.transaction.annotation.Transactional;

import cn.productivetech.cmos.zhongbao.model.EnrollModelItem;

/**
 * 声纹模型服务类接口
 * @author Administrator
 * @created 2019-04-17
 */
public interface IVoicePrintService {

	/**
	 * 添加声纹模型
	 * @param eModelItem	样本模型信息
	 */
	@Transactional
	void addVoicePrint(EnrollModelItem eModelItem);
	
	/**
	 * 更新声纹模型
	 * @param eModelItem	样本模型信息
	 */
	@Transactional
	void updateVoicePrint(EnrollModelItem eModelItem);
	
	/**
	 * 根据id删除样本模型
	 * @param id	主键
	 */
	@Transactional
	void deleteVoicePrint(long id);
	
	/**
	 * 根据主键查询声纹模型信息
	 * @param id	主键
	 * @return
	 */
	EnrollModelItem queryByPrimaryKey(long id);
	
	/**
	 * 根据坐席id查询声纹模型
	 * @param agentId
	 * @return
	 */
	EnrollModelItem queryByaAgentId(String agentId);
}
