package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.model.PageInfo;

public interface IDepartmentService {

	DepartmentItem getDepartmentById(int Id);

	PageInfo<DepartmentItem> selectDepartmentByPage(int page,int pagesize);
	
	RspResultDto AddDepartment(DepartmentItem department, int userID);

	List<DepartmentItem> selectAllDepInfo();
	
	PageInfo<DepartmentItem> selectAllDepInfo(int page,int rows);

	List<DepartmentItem> selectAllDepInfoByEmpID(int id);
	
}
