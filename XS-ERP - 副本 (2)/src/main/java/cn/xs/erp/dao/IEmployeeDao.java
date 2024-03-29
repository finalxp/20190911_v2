package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.EmployeeItem;

@Mapper
public interface IEmployeeDao extends IBaseDao<EmployeeItem, Integer> {
	List<EmployeeItem> selectAll();
	List<EmployeeItem> selectAllEmpInfo();
	//根据员工No查员工信息
	EmployeeItem getEmployeeByNo(String empNo);
	
	//修改员工信息
	int updateEmployeeById(Map<String, Object> map);
	
	//根据toolbar查询员工信息
	List<EmployeeItem> selectToolbarList(Map<String, String> map);
	List<EmployeeItem> selectEmpByEmpNoAndDepIdAndDoorNo(Map<String, String> map);
	List<EmployeeItem> selectEmpByEmpNoAndDepIdAndmenuName(Map<String, String> map);
	
}