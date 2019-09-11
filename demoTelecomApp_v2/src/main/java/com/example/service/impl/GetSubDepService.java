package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.GetSubDepMapper;
import com.example.model.Department;
import com.example.service.IGetSubDepService;

@Service
public class GetSubDepService implements IGetSubDepService{

	@Autowired
	GetSubDepMapper getSubDepMapper;
	
	@Override
	public List<Department> getSubDepService(String depId) {
		List<Department> subDepMapper = getSubDepMapper.getSubDepMapper(depId);
		
		return subDepMapper;
	}
	
}
