package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.EmployeeItem;

@Mapper
public interface IEmployeeDao extends IBaseDao<EmployeeItem, Integer> {
	List<EmployeeItem> selectAll();
	
	//根据员工No查员工信息
	EmployeeItem getEmployeeByNo(String empNo);
	
	//查询所有员工信息，包括是否注册过声纹
	List<EmployeeItem> selectAllEmpInfo();
	
	//修改员工信息
	int updateEmployeeById(Map<String, Object> map);
	
	
}