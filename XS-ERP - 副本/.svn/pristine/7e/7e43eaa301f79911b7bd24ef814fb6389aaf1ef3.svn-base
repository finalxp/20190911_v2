package cn.xs.erp.service;

import java.util.List;
import java.util.Map;

import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;

public interface IEmployeeService {

	EmployeeItem getEmployeeById(int Id);

	PageInfo<EmployeeItem> selectEmployeeByPage(int page,int pagesize);
	
	int addEmployee(EmployeeItem employee);
	//根据员工No查询员工信息
	EmployeeItem getEmployeeByNo(String empNo);
	//查询所有员工信息，包括是否注册过声纹
	List<EmployeeItem> selectAllEmpInfo();
	//修改员工信息
	int updateEmployeeById(Map<String, Object> map);
	
	int deleteByPrimaryKey(int id);
	
}
