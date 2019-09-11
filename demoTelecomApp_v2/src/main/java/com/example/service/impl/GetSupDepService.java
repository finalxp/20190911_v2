package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.IGetSupDepDao;
import com.example.model.TreePoint;
import com.example.model.Department;
import com.example.service.IGetSupDepService;

@Service
public class GetSupDepService implements IGetSupDepService{
	@Autowired
	IGetSupDepDao getSupDepDao;
	
	@Override
	public List<TreePoint> getSupDep(){
		
		List<Department> supDep = getSupDepDao.getSupDep();
		
		List<TreePoint> treePoints = new ArrayList<TreePoint>();
		int order =1;
		for (Department department : supDep) {
			
			TreePoint treePoint = new TreePoint();
			treePoint.setID(department.getDepartment_id());
			treePoint.setNAME(department.getDepartment_name());
			treePoint.setPARENTID(department.getDepartment_sup_id());
			treePoint.setISLEAF("0");
			treePoint.setDISPLAY_ORDER(order);
			order++;
			treePoints.add(treePoint);
		}
		
		return treePoints;
		
	}
	
}
