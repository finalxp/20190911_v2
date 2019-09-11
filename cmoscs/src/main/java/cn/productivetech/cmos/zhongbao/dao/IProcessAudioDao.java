package cn.productivetech.cmos.zhongbao.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.productivetech.cmos.zhongbao.model.ProcessAudioItem;
/**
 * 验证语音面向对象数据访问接口
 * @author Administrator
 * @created 2019-04-16
 */
@Mapper
public interface IProcessAudioDao extends IBaseDao<Integer, ProcessAudioItem> {


}
