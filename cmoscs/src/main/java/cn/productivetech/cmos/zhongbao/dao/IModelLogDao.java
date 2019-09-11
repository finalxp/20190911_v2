package cn.productivetech.cmos.zhongbao.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.productivetech.cmos.zhongbao.model.ModelLogItem;

/**
 * 注册音频模型创建日志面向对象数据访问基础接口
 * @author Administrator
 * @created 2019-04-17
 */
@Mapper
public interface IModelLogDao extends IBaseDao<Long, ModelLogItem>{

}
