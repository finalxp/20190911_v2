package cn.xs.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xs.erp.dao.IEmployeeDao;
import cn.xs.erp.dao.IMenuDao;
import cn.xs.erp.dao.IMenuPermissionDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.MenuItem;
import cn.xs.erp.model.MenuPermissionItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IPermissionService;

import com.github.pagehelper.PageHelper;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	private IMenuPermissionDao menuPermissionDao;
	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private IEmployeeDao employeeDao;
	
	
	@Override
	public MenuPermissionItem getMenuPermissionItemById(int Id) {
		return menuPermissionDao.selectByPrimaryKey(Id);
	}

	@Override
	public PageInfo<EmployeeItem> selectMenuPermissionByPage(int page,int pagesize, String depID, String empNo, String menuName) {
		PageHelper.startPage(page, pagesize);
		if (depID ==null)
			depID="";
		if (empNo==null)
			empNo="";
		if (menuName==null)
			menuName="";
		List<EmployeeItem> empList = new ArrayList<EmployeeItem>();
		if (depID=="" && empNo=="" && menuName=="") {
			empList=employeeDao.selectAll();
			setDoorPermission(empList);
		}else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("depID", depID);
			map.put("empNo", empNo);
			map.put("menuName", menuName);
			empList =employeeDao.selectEmpByEmpNoAndDepIdAndmenuName(map);
			setDoorPermission(empList);
		}
		PageInfo<EmployeeItem> pageInfo = new PageInfo<>(empList);
		return pageInfo;
	}

	private void setDoorPermission(List<EmployeeItem> list) {
		for (EmployeeItem employeeItem : list) {
			List<MenuItem> listMenu = menuDao.selectMenusByEmpID(employeeItem.getId());
			employeeItem.setMenuPermission(listMenu);
		}
	}
	
	@Override
	public int AddMenuPermission(MenuPermissionItem menuPermissionItem) {
		return menuPermissionDao.insert(menuPermissionItem);
	}

	@Override
	public List<MenuPermissionItem> getPermissionsByEmpID(int empId) {
		return menuPermissionDao.getPermissionsByEmpID(empId);
	}

	@Override
	public boolean hasPermission(String urlPath,Object loginUserID) {
		List<MenuItem> listMenu = menuDao.selectByMenuPath(urlPath);
		if (listMenu.isEmpty()) {
			return true;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("urlPath", urlPath);
		map.put("loginUserID", (Integer) loginUserID+"");
		List<MenuPermissionItem> mItem = menuPermissionDao.hasPermission(map);
		if (mItem.size()<1) {
			return false;
		}
		return true;
	}

	@Override
	public EmployeeItem getEmpMenuByID(int id) {
		EmployeeItem eItem=employeeDao.selectByPrimaryKey(id);
		List<MenuItem> list=menuDao.selectMenusByEmpID(id);
		eItem.setMenuPermission(list);
		return eItem;
	}

	@Override
	public RspResultDto updateEmpMenuPermission(EmployeeItem eItem, int[] menuID, int userID) {
		if (menuID==null) {
			menuID=new int[0];
		}
		List<MenuPermissionItem> list = menuPermissionDao.selectByEmpID(eItem.getId());
		if (menuID.length > 0) {
			int addEmpMenu = 0;
			List<String> menuList = new ArrayList<String>();
			for (int i = 0; i < menuID.length; i++) {
				menuList.add(menuID[i]+"");
			}
			//menuID中不包含以前就有的menu id，删除以前存储的员工和menu对应记录
			for (MenuPermissionItem menuPermissionItem : list) {
				if (!menuList.contains(menuPermissionItem.getMenuId()+"")) {
					if (menuPermissionDao.deleteByPrimaryKey(menuPermissionItem.getId())<1) {
						return new RspResultDto(RspResultMessage.EditMenuForEmployeeFailed);
					}
				}	
			}
			//menuID中包含以前就有的菜单ID，则不再添加
			for (int j = 0; j < menuID.length; j++) {
				for (MenuPermissionItem menuPermissionItem : list) {
					if (menuPermissionItem.getMenuId()==menuID[j]) {
						menuList.remove(menuID[j]+"");
					}
				}
			}
			for (String integer : menuList) {
				MenuPermissionItem menuPermissionItem = new MenuPermissionItem();
				menuPermissionItem.setMenuId(Integer.parseInt(integer));
				menuPermissionItem.setEmpId(eItem.getId());
				menuPermissionItem.setCreateBy(userID);
				menuPermissionItem.setCreateTime(new Date());
				addEmpMenu=menuPermissionDao.insertSelective(menuPermissionItem);
				if (addEmpMenu<1) {
					return new RspResultDto(RspResultMessage.EditMenuForEmployeeFailed);
				}
			}
			return new RspResultDto(RspResultMessage.EditMenuForEmployeeSuccess);
		} else {
			if (list.size() > 0) {
				for (MenuPermissionItem menuPermissionItem : list) {
					if (menuPermissionDao.deleteByPrimaryKey(menuPermissionItem.getId())<1) {
						return new RspResultDto(RspResultMessage.EditMenuForEmployeeFailed);
					}
				}
				return new RspResultDto(RspResultMessage.EditMenuForEmployeeSuccess);
			} else {
				return new RspResultDto(RspResultMessage.EditMenuForEmployeeSuccess);
			}
		}
	}
}
