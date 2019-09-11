package cn.xs.erp.service;

import java.util.List;

import cn.xs.erp.model.MenuItem;
import cn.xs.erp.model.PageInfo;

public interface IMenuService {

	MenuItem getMenuItemById(int id);

	PageInfo<MenuItem> selectMenuByPage(int page,int pagesize);
	
	int AddMenu(MenuItem menuItem);
	
	List<MenuItem> getFirstLevelMenus();

	List<MenuItem> getTreeMenus();

	List<MenuItem> selectAll();
	
}
