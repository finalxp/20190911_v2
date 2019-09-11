package cn.xs.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.xs.erp.model.MenuPermissionItem;


@Mapper
public interface IMenuPermissionDao extends IBaseDao<MenuPermissionItem, Integer> {
	List<MenuPermissionItem> selectAll();
	
	//根据员工id查询该员工拥有的menu权限的id
	List<MenuPermissionItem> getPermissionsByEmpID(int empID);

	List<MenuPermissionItem> hasPermission(Map<String, String> map);

	List<MenuPermissionItem> selectByEmpID(Integer id);
	
}