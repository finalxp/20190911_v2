package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.UpdateTextNumberMapper;

import com.example.service.IUpdateTextNumberService;
import com.example.utils.ResponseTemplate;

@Service
public class UpdateTextNumberService implements IUpdateTextNumberService{

	@Autowired
	UpdateTextNumberMapper updateTextNumberMapper;
	
	ResponseTemplate responseTemplate = new ResponseTemplate();
	
	@Override
	public ResponseTemplate updateTextNumber(String userId) {
		
		int updateTextNumber = updateTextNumberMapper.updateTextNumber(userId);
		if (updateTextNumber == 0) {
			return responseTemplate.error();
		}else {
			System.out.println("updateTextNumber------"+updateTextNumber);
			
			return responseTemplate.ok();
		}
		
	}

}
