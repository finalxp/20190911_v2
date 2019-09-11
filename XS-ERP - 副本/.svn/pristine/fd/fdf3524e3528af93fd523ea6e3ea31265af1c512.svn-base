package cn.xs.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xs.erp.dao.IMenuPermissionDao;
import cn.xs.erp.model.MenuPermissionItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IMenuPermissionService;

import com.github.pagehelper.PageHelper;

@Service
public class MenuPermissionServiceImpl implements IMenuPermissionService {

	@Autowired
	private IMenuPermissionDao menuPermissionDao;

	@Override
	public MenuPermissionItem getMenuPermissionItemById(int Id) {
		return menuPermissionDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<MenuPermissionItem> selectMenuPermissionByPage(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<MenuPermissionItem> docs = menuPermissionDao.selectAll();
		PageInfo<MenuPermissionItem> pageInfo = new PageInfo<>(docs);
		return pageInfo;
	}

	@Override
	public int AddMenuPermission(MenuPermissionItem menuPermissionItem) {
		return menuPermissionDao.insert(menuPermissionItem);
	}

	@Override
	public List<MenuPermissionItem> getPermissionsByEmpID(int empId) {
		return menuPermissionDao.getPermissionsByEmpID(empId);
	}
}
