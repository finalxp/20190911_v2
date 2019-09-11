package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.UpdateTextNumberMapper;
import com.example.mapper.UpdateUserPrivilegesMapper;
import com.example.service.IUpdateUserPrivilegesService;
import com.example.utils.ResponseTemplate;


@Service
public class UpdateUserPrivilegesService implements IUpdateUserPrivilegesService{

	@Autowired
	UpdateUserPrivilegesMapper updateUserPrivilegesMapper;
	
	ResponseTemplate responseTemplate = new ResponseTemplate();

	@Override
	public ResponseTemplate updateUserPrivilegesAs0(String userId) {
		int updateUserPrivilegesAs0 = updateUserPrivilegesMapper.updateUserPrivilegesAs0(userId);
		if (updateUserPrivilegesAs0 == 0) {
			return responseTemplate.error();
		}else {			
			return responseTemplate.ok();
		}
	
	}

	@Override
	public ResponseTemplate updateUserPrivilegesAs1(String userId) {
		int updateUserPrivilegesAs1 = updateUserPrivilegesMapper.updateUserPrivilegesAs1(userId);
		if (updateUserPrivilegesAs1 == 0) {
			return responseTemplate.error();
		}else {			
			return responseTemplate.ok();
		}

	}

	

}
