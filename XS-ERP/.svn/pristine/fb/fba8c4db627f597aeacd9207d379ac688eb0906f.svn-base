package cn.xs.erp.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.DoorItem;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;

public interface IDoorService {

	DoorItem getDoorItemById(int Id);

	RspResultDto addDoor(DoorItem door,int userID);
	
	RspResultDto editDoor(DoorItem doorItem,int userID);
	
	RspResultDto deleteDoor(int id);

	PageInfo<DoorItem> selectDoorByPage(Integer page, Integer rows,String doorNo, String doorName);

	EmployeeItem selectDoorPermission(int id);

	List<DoorItem> selectAll();
	@Transactional
	RspResultDto updateDoorPermission(EmployeeItem eItem, int[] id,int userID);

	PageInfo<EmployeeItem> selectDoorPermissionByPage(int page,int rows, String depID, String empNo, String doorNo, int userID);
}
