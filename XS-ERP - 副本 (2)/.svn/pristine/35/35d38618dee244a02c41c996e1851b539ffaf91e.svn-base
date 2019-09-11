package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.model.MenuPermissionItem;
import cn.xs.erp.model.PageInfo;

public interface IMenuPermissionService {

	MenuPermissionItem getMenuPermissionItemById(int id);

	PageInfo<MenuPermissionItem> selectMenuPermissionByPage(int page,int pagesize);
	
	int AddMenuPermission(MenuPermissionItem menuPermissionItem);
	
	List<MenuPermissionItem> getPermissionsByEmpID(int empID);
}
