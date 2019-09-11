/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.service;


import java.math.BigDecimal;

import com.eos.system.annotation.Bizlet;
import commonj.sdo.DataObject;

/**
 * @author A174669
 * @date 2017-12-15 15:21:14
 *
 */
public interface audioService {
	/**
	 * 声纹注册接口（固定文本）
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	public DataObject enrolSpeaker(DataObject inMessage,DataObject outMessage);
	
	/**
	 * 声纹注册接口（文本无关）
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	public DataObject enrolSpeakerIndependent(DataObject inMessage,DataObject outMessage);
	
	
	
	
	/**
	 * 声纹(注册)验证接口--更新模型 固定文本模型
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	
	public DataObject verifySpeaker(DataObject inMessage,DataObject outMessage,BigDecimal threshold);
	
	/**
	 * 声纹(注册)验证接口--更新模型 文本无关模型
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	
	public DataObject verifySpeakerIndependent(DataObject inMessage,DataObject outMessage,BigDecimal threshold);
	
	/**
	 * 声纹(注册)验证接口--更新模型 文本无关模型
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	
	public DataObject verifySpeakerIndependentCQVoice(DataObject inMessage,DataObject outMessage);
	
	/**
	 * 声纹更新接口--固定文本
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	 
	public DataObject updateSpeaker(DataObject inMessage,DataObject outMessage);
	
	/**
	 * 声纹更新接口--文本无关
	 * @param inMessage
	 * @param outMessage
	 * @return
	 */
	public DataObject updateSpeakerIndependent(DataObject inMessage,DataObject outMessage);
	
	/**
	 * 声纹删除--文本无关
	 * @param inMessage
	 * @param outMessage
	 * @return
	 */
	public DataObject deleteSpeakerIndependent(DataObject inMessage,DataObject outMessage);
	
	
	/**
	 * 声纹比较接口
	 * @param inMessage 输入报文
	 * @param inMessage 其中包含如下参数：1.userName用户名 2.certType证件类型 3.certNo证件号码 4.userUniqueId用户唯一标识
	 *                   5.channel渠道 6.businessType商业类型 7.closeAudio输入声纹
	 * @param outMessage 输出报文
	 * @return outMessage 其中包含如下参数：1.	returnCode返回码 2.	returnMsg 返回描述 3.	similarity相似度 4.	threshold 阈值
	 * @throws Exception
	 */
	public DataObject compareSpeaker(DataObject inMessage,DataObject outMessage);
	
	/**
	 * 声纹注册查询
	 */
	public DataObject isExistSpeaker(DataObject inMessage,DataObject outMessage);
}
