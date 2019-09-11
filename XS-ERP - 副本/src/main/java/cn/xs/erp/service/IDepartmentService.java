package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.model.PageInfo;

public interface IDepartmentService {

	DepartmentItem getDepartmentById(int Id);

	PageInfo<DepartmentItem> selectDepartmentByPage(int page,int pagesize);
	
	int AddDepartment(DepartmentItem department);

	List<DepartmentItem> selectAllDepInfo();
	
	
}
