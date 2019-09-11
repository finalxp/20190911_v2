package cn.xs.erp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xs.erp.dao.IEmployeeDao;
import cn.xs.erp.dao.IMenuDao;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.MenuItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IMenuService;

import com.github.pagehelper.PageHelper;

@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private IEmployeeDao empDao;
	@Override
	public MenuItem getMenuItemById(int Id) {
		return menuDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<MenuItem> selectMenuByPage(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<MenuItem> docs = menuDao.selectAll();
		PageInfo<MenuItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}

	@Override
	public int AddMenu(MenuItem menuItem) {
		return menuDao.insert(menuItem);
	}

	@Override
	public Map<String,Object> getFirstLevelMenus(int userID) {
		List<MenuItem> listMenuItems = menuDao.getFirstLevelMenus();
		EmployeeItem employeeItem = empDao.selectByPrimaryKey(userID);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("menuItem", listMenuItems);
		map.put("employeeItem", employeeItem);
		return map;
	}

	@Override
	public List<MenuItem> getTreeMenus() {
		return menuDao.getTreeMenus();
	}

	@Override
	public List<MenuItem> selectAll() {
		return menuDao.selectAll();
	}

}
