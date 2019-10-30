package cn.productivetech.shtelcom.enrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.productivetech.shtelcom.enrol.model.TextBean;

@Mapper
public interface ITextDao extends IBaseDao<TextBean, Short> {
	List<TextBean> selectByStatus(Integer textStatus);
}
