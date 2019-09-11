package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.model.JobItem;
import cn.xs.erp.model.PageInfo;

public interface IDepartmentService {

	DepartmentItem getDepartmentById(int Id);

	PageInfo<DepartmentItem> selectDepartmentByPage(Integer page,Integer pagesize, String depInfo);

	List<DepartmentItem> selectAllDepInfo();
	
	PageInfo<DepartmentItem> selectAllDepInfo(Integer page,Integer rows);

	List<DepartmentItem> selectAllDepInfoByEmpID(int id);


	RspResultDto addDepartment(DepartmentItem department, int userID);


	RspResultDto updateDepartment(DepartmentItem departmentItem, int userID);

	RspResultDto deleteDepartment(int id);

	





	
}
