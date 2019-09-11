package cn.xs.erp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.DepartmentItem;

@Mapper
public interface IDepartmentDao extends IBaseDao<DepartmentItem, Integer> {
	List<DepartmentItem> selectAll();
	
	//根据员工No查员工信息
	DepartmentItem getDepartmentByNo(String depId);
	
	List<DepartmentItem> selectAllDepInfo();

	List<DepartmentItem> selectAllDepInfoByEmpID(int empID);

	List<DepartmentItem> selectAllDepInfoByDepInfo(String depInfo);
}