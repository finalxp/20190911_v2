/**
 * 
 */
package com.pccc.vprs.servicedisplay.vprs.audio.fs;

import com.eos.foundation.database.DatabaseExt;
import com.eos.system.annotation.Bizlet;
import com.pccc.vprs.servicedisplay.vprs.common.ReturnCode;
import com.pccc.vprs.servicedisplay.vprs.model.fs.VprsFsVerifyInfo;
import com.primeton.btp.api.core.exception.BTPRuntimeException;
import com.primeton.btp.api.core.logger.ILogger;
import com.primeton.btp.api.core.logger.LoggerFactory;

import commonj.sdo.DataObject;

/**
 * @author pccc
 * @date 2018-10-12 15:33:43
 *
 */
@Bizlet("随意说验证明细流水表")
public class VprsFsVerify {
	private static ILogger logger = LoggerFactory.getLogger(VprsFsVerify.class);

	@Bizlet("分页查询验证明细流水表")
	public DataObject queryPageVprsFsVerifyInfo(DataObject inMessage,DataObject outMessage,DataObject page){	
		logger.info("进入查询验证明细流水表信息");

		try {
		VprsFsVerifyInfo vprsFsVerifyInfo = new VprsFsVerifyInfo();

		vprsFsVerifyInfo.setTransDate(inMessage.getString("transDate"));
		
		vprsFsVerifyInfo.setStartDate(inMessage.getString("startDate"));
		
		vprsFsVerifyInfo.setEndDate(inMessage.getString("endDate"));
		
		vprsFsVerifyInfo.setIsDesc(inMessage.getString("isDesc"));
		
		if (inMessage.get("userType") != null && !"".equals(inMessage.get("userType"))) {
			vprsFsVerifyInfo.setUserType(inMessage.getString("userType"));
		}
		if (inMessage.get("userName") != null && !"".equals(inMessage.get("userName"))) {
			vprsFsVerifyInfo.setUserName(inMessage.getString("userName"));
		}
		if (inMessage.get("certType") != null && !"".equals(inMessage.get("certType"))) {
			vprsFsVerifyInfo.setCertType(inMessage.getString("certType"));
		}
		if (inMessage.get("certNo") != null && !"".equals(inMessage.get("certNo"))) {
			vprsFsVerifyInfo.setCertNo(inMessage.getString("certNo"));
		}
		if (inMessage.get("userUniqueId") != null && !"".equals(inMessage.get("userUniqueId"))) {
			if(inMessage.get("callId") != null && !"".equals(inMessage.get("callId"))) {
			vprsFsVerifyInfo.setUserUniqueId(inMessage.getString("userUniqueId")+"|"+inMessage.get("callId"));	//callId和userUniqueId拼接存储在数据库USER_UNIQUE_ID字段中
			}else {
			vprsFsVerifyInfo.setUserUniqueId(inMessage.getString("userUniqueId"));
			}
		}
		if (inMessage.get("channel") != null && !"".equals(inMessage.get("channel"))) {
			vprsFsVerifyInfo.setChannel(inMessage.getString("channel"));
		}
		if (inMessage.get("businessType") != null && !"".equals(inMessage.get("businessType"))) {
			vprsFsVerifyInfo.setBusinessType(inMessage.getString("businessType"));
		}
			
		String querySqlId = "com.pccc.vprs.servicedisplay.vprs.model.fs.VprsFsVerifyInfo.queryPageVprsFsVerifyInfo";
		String queryCount = "com.pccc.vprs.servicedisplay.vprs.model.fs.VprsFsVerifyInfo.queryPageVprsFsVerifyCount";
		
		Object objs[] = null;
		int total = 0;
		
		try {
			objs = DatabaseExt.queryByNamedSqlWithPage("default", querySqlId, page, vprsFsVerifyInfo);
			total = (((VprsFsVerifyInfo) DatabaseExt.queryByNamedSql("default", queryCount, vprsFsVerifyInfo)[0])).getCount();
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("查询组信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
			throw new BTPRuntimeException("查询组信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);	
		}
		
		for (int i = 0; i < objs.length; i++) {
			VprsFsVerifyInfo sdo = (VprsFsVerifyInfo) objs[i];
//			outMessage.set("info[" + (i + 1) + "]/jnlSeqNo", sdo.getSeqNo()!=null?sdo.getSeqNo():"");  
//			outMessage.set("info[" + (i + 1) + "]/channelSeqNo", sdo.getChannelSeqNo()!=null?sdo.getChannelSeqNo():"");
			outMessage.set("info[" + (i + 1) + "]/channel", sdo.getChannel()!=null?sdo.getChannel():"");
			outMessage.set("info[" + (i + 1) + "]/userType", sdo.getUserType()!=null?sdo.getUserType():"");
//			outMessage.set("info[" + (i + 1) + "]/riskLevel", sdo.getRiskLevel()!=null?sdo.getRiskLevel():"");
			outMessage.set("info[" + (i + 1) + "]/businessType", sdo.getBusinessType()!=null?sdo.getBusinessType():"");
			outMessage.set("info[" + (i + 1) + "]/transDate", sdo.getTransDate()!=null?sdo.getTransDate():"");
			outMessage.set("info[" + (i + 1) + "]/transTime", sdo.getTransTime()!=null?sdo.getTransTime():"");
			outMessage.set("info[" + (i + 1) + "]/transCode", sdo.getTransCode()!=null?sdo.getTransCode():"");
//			outMessage.set("info[" + (i + 1) + "]/transDesc", sdo.getTransDesc()!=null?sdo.getTransDesc():"");
			outMessage.set("info[" + (i + 1) + "]/userUniqueId", sdo.getUserUniqueId()!=null?sdo.getUserUniqueId():"");
			outMessage.set("info[" + (i + 1) + "]/certType", sdo.getCertType()!=null?sdo.getCertType():"");
			outMessage.set("info[" + (i + 1) + "]/userName", sdo.getUserName()!=null?sdo.getUserName():"");
			outMessage.set("info[" + (i + 1) + "]/certNo", sdo.getCertNo()!=null?sdo.getCertNo():"");
//			outMessage.set("info[" + (i + 1) + "]/closeAudio", sdo.getCloseAudio()!=null?sdo.getCloseAudio():"");
//			outMessage.set("info[" + (i + 1) + "]/transRequestMsg", sdo.getTransRequestMsg()!=null?sdo.getTransRequestMsg():"");
//			outMessage.set("info[" + (i + 1) + "]/transResponseMsg", sdo.getTransResponseMsg()!=null?sdo.getTransResponseMsg():"");
			outMessage.set("info[" + (i + 1) + "]/compareResult", sdo.getCompareResult()!=null?sdo.getCompareResult():"");
			outMessage.set("info[" + (i + 1) + "]/returnCode", sdo.getReturnCode()!=null?sdo.getReturnCode():"");
			outMessage.set("info[" + (i + 1) + "]/score", sdo.getScore()!=null?sdo.getScore():"");
			outMessage.set("info[" + (i + 1) + "]/returnMsg", sdo.getReturnMsg()!=null?sdo.getReturnMsg():"");
//			outMessage.set("info[" + (i + 1) + "]/serverNode", sdo.getServerNode()!=null?sdo.getServerNode():"");
//			outMessage.set("info[" + (i + 1) + "]/crtTime", sdo.getCrtTime()!=null?sdo.getCrtTime():"");
//			outMessage.set("info[" + (i + 1) + "]/lstUpdTime", sdo.getLstUpdTime()!=null?sdo.getLstUpdTime():"");
//			outMessage.set("info[" + (i + 1) + "]/lstUpdDate", sdo.getLstUpdDate()!=null?sdo.getLstUpdDate():"");
//			outMessage.set("info[" + (i + 1) + "]/lstUpdUser", sdo.getLstUpdUser()!=null?sdo.getLstUpdUser():"");
//			outMessage.set("info[" + (i + 1) + "]/scrLevel", sdo.getScrLevel()!=null?sdo.getScrLevel():"");
		}
		
		outMessage.set("returnCode",ReturnCode.TOUDA_SUCCESS );
		outMessage.set("returnMsg", "分页查询验证明细流水表成功！");
		outMessage.set("totalNum", total);
		}catch(Exception e) {
			logger.error("queryPageVprsFsVerifyInfo分页查询验证明细流水表异常："+e);
			outMessage.set("returnCode",ReturnCode.TOUDA_FAIL );
			outMessage.set("returnMsg", "分页查询验证明细流水表异常！");
		}
		return outMessage;
	}
	
	
}
