package cn.xs.erp.service;

import java.util.List;
import java.util.Map;

import cn.xs.erp.model.MenuItem;
import cn.xs.erp.model.PageInfo;

public interface IMenuService {

	MenuItem getMenuItemById(int id);

	PageInfo<MenuItem> selectMenuByPage(int page,int pagesize);
	
	int AddMenu(MenuItem menuItem);
	
	Map<String,Object> getFirstLevelMenus(int i);

	List<MenuItem> getTreeMenus();

	List<MenuItem> selectAll();
	
}
