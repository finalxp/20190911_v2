package cn.xs.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xs.erp.dao.IDoorDao;
import cn.xs.erp.dao.IDoorPermissionDao;
import cn.xs.erp.dao.IEmployeeDao;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;
import cn.xs.erp.model.DoorItem;
import cn.xs.erp.model.DoorPermissionItem;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDoorService;

import com.github.pagehelper.PageHelper;

@Service
public class DoorServiceImpl implements IDoorService {

	@Autowired
	private IDoorDao doorDao;
	@Autowired
	private IEmployeeDao empDao;
	@Autowired
	private IDoorPermissionDao doorPermissionDao;

	@Override
	public DoorItem getDoorItemById(int Id) {
		return doorDao.selectByPrimaryKey(Id);
	}


	@Override
	public PageInfo<DoorItem> selectDoorByPage(Integer page, Integer rows,String doorNo, String doorName) {
		PageHelper.startPage(page,rows);
		Map<String, String> map = new HashMap<String, String>();
		map.put("doorNo", doorNo);
		map.put("doorName", doorName);
		List<DoorItem> list = doorDao.selectByDoorNoAndDoorName(map);
		PageInfo<DoorItem> pageInfo = new PageInfo<DoorItem>(list);
		return pageInfo;
	}
	
	@Override
	public RspResultDto addDoor(DoorItem door,int userID) {
		door.setCreateBy(userID);
		door.setCreateTime(new Date());
		if (doorDao.insertSelective(door)<1) {
			return new RspResultDto(RspResultMessage.AddDoorFailed);
		}
		return new RspResultDto(RspResultMessage.AddDoorSuccess);
	}

	@Override
	public RspResultDto editDoor(DoorItem doorItem,int userID) {
		
		doorItem.setUpdateBy(userID);
		doorItem.setUpdateTime(new Date());
		int i = doorDao.updateByPrimaryKeySelective(doorItem);
		if (i<1) {
			return new RspResultDto(RspResultMessage.EditDoorFailed);
		}
		return new RspResultDto(RspResultMessage.EditDoorSuccess);
	}

	@Override
	public RspResultDto deleteDoor(int id) {
		int i = doorDao.deleteByPrimaryKey(id);
		if (i<1) {
			return new RspResultDto(RspResultMessage.DeleteDoorFailed);
		}
		return new RspResultDto(RspResultMessage.DeleteDoorSuccess);
	}

	@Override
	public PageInfo<EmployeeItem> selectDoorPermissionByPage(int page, int rows, String depID,
			String empNo, String doorNo,int userID) {
		if (empNo==null) {
			empNo="";
		}
		if (depID==null) {
			depID="";
		}
		if (doorNo==null) {
			doorNo="";
		}
		PageHelper.startPage(page, rows);
		List<EmployeeItem> listEmp=new ArrayList<EmployeeItem>();
		if (empNo=="" && depID=="" && doorNo=="") {
			listEmp= empDao.selectAll();
			setDoorPermission(listEmp);
		}else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("empNo", empNo);
			map.put("depID", depID);
			map.put("doorNo", doorNo);
			listEmp=empDao.selectEmpByEmpNoAndDepIdAndDoorNo(map);
			setDoorPermission(listEmp);
		}
		PageInfo<EmployeeItem> pagInfo = new PageInfo<EmployeeItem>(listEmp);
		return pagInfo;
	}

	private void setDoorPermission(List<EmployeeItem> list) {
		for (EmployeeItem employeeItem : list) {
			List<DoorItem> listDoor = doorDao.selectByEmpID(employeeItem.getId());
			employeeItem.setDoorPermission(listDoor);
		}
	}
	@Override
	public EmployeeItem selectDoorPermission(int id) {
		EmployeeItem empItem = empDao.selectByPrimaryKey(id);
		List<DoorItem> listDoor = doorDao.selectByEmpID(empItem.getId());
		empItem.setDoorPermission(listDoor);
		return empItem;
	}

	@Override
	public List<DoorItem> selectAll() {
		return doorDao.selectAll();
	}

	@Override
	public RspResultDto updateDoorPermission(EmployeeItem eItem, int[] doorID,int userID) {
		if (doorID==null) {
			doorID=new int[0];
		}
		List<DoorPermissionItem> list = doorPermissionDao.selectByEmpID(eItem.getId());
		if (doorID.length > 0) {
			int addEmpDoor = 0;
			List<String> doorList = new ArrayList<String>();
			for (int i = 0; i < doorID.length; i++) {
				doorList.add(doorID[i]+"");
			}
			//doorID中不包含以前就有的door id，删除以前存储的员工和部门对应记录
			for (DoorPermissionItem doorPermissionItem : list) {
				if (!doorList.contains(doorPermissionItem.getDoorId()+"")) {
					if (doorPermissionDao.deleteByPrimaryKey(doorPermissionItem.getId())<1) {
						return new RspResultDto(RspResultMessage.EditDoorForEmployeeFailed);
					}
				}	
			}
			//depID中包含以前就有的部门ID，则不再添加
			for (int j = 0; j < doorID.length; j++) {
				for (DoorPermissionItem doorPermissionItem : list) {
					if (doorPermissionItem.getDoorId()==doorID[j]) {
						doorList.remove(doorID[j]+"");
					}
				}
			}
			for (String integer : doorList) {
				DoorPermissionItem doorPermissionItem = new DoorPermissionItem();
				doorPermissionItem.setDoorId(Integer.parseInt(integer));
				doorPermissionItem.setEmpId(eItem.getId());
				doorPermissionItem.setCreateBy(userID);
				doorPermissionItem.setCreateTime(new Date());
				addEmpDoor=doorPermissionDao.insertSelective(doorPermissionItem);
				if (addEmpDoor<1) {
					return new RspResultDto(RspResultMessage.EditDoorForEmployeeFailed);
				}
			}
			return new RspResultDto(RspResultMessage.EditDoorForEmployeeSuccess);
		} else {
			if (list.size() > 0) {
				for (DoorPermissionItem doorPermissionItem : list) {
					if (doorPermissionDao.deleteByPrimaryKey(doorPermissionItem.getId())<1) {
						return new RspResultDto(RspResultMessage.EditDoorForEmployeeFailed);
					}
				}
				return new RspResultDto(RspResultMessage.EditDoorForEmployeeSuccess);
			} else {
				return new RspResultDto(RspResultMessage.EditDoorForEmployeeSuccess);
			}
		}
	}

}
