package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.mapper.GetSubDepUserMapper;
import com.example.model.Department;
import com.example.model.TreePoint;
import com.example.model.User;
import com.example.service.IGetSubDepUserService;

@Service
public class GetSubDepUserService implements IGetSubDepUserService{

	@Autowired
	GetSubDepUserMapper getSubDepUserMapper;
	
	@Override
	public List<TreePoint> getSubDepUserService(String depId) {
		//得到所有子部门
		List<Department> getSubDepUser=getSubDepUserMapper.getSubDepMapper(depId);
		//得到所有用户
		List<User> getSubUser = getSubDepUserMapper.getSubUserMapper(depId);
		
		
		List<TreePoint> treePoints = new ArrayList<TreePoint>();
		
		int order =1;
		for (Department department : getSubDepUser) {
			
			TreePoint treePoint = new TreePoint();
			treePoint.setID(department.getDepartment_id());
			treePoint.setNAME(department.getDepartment_name());
			treePoint.setPARENTID(department.getDepartment_sup_id());
			treePoint.setISLEAF("0");
			treePoint.setDISPLAY_ORDER(order);
			order++;
			treePoints.add(treePoint);
		}
		
		for (User user : getSubUser) {
			TreePoint treePoint = new TreePoint();
			treePoint.setID(user.getUser_login_name());
			treePoint.setNAME(user.getName());
			treePoint.setPARENTID(user.getUser_dep_id());
			treePoint.setISLEAF("1");
			treePoint.setDISPLAY_ORDER(order);
			order++;
			treePoints.add(treePoint);
		}
		
		return treePoints;
	}

}
