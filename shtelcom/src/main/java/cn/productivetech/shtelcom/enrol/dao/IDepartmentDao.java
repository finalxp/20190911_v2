package cn.productivetech.shtelcom.enrol.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.productivetech.shtelcom.enrol.model.DepartmentBean;

@Mapper
public interface IDepartmentDao extends IBaseDao<DepartmentBean, Short> {
}