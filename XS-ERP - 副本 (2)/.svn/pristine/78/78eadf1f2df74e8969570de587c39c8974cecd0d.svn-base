package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.MenuPermissionItem;
import cn.xs.erp.model.PageInfo;

public interface IPermissionService {

	MenuPermissionItem getMenuPermissionItemById(int id);

	PageInfo<EmployeeItem> selectMenuPermissionByPage(int page,int pagesize, String depID, String empNo, String menuName);
	
	int AddMenuPermission(MenuPermissionItem menuPermissionItem);
	
	List<MenuPermissionItem> getPermissionsByEmpID(int empID);
	
	boolean hasPermission(String urlPath, Object objEmployeeId);

	EmployeeItem getEmpMenuByID(int id);

	RspResultDto updateEmpMenuPermission(EmployeeItem eItem, int[] menuID, int userID);
}
