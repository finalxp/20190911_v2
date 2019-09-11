package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.GetAllByNameMapper;
import com.example.model.User;
import com.example.service.IGetAllByName;

@Service
public class GetAllByNameService implements IGetAllByName{

	@Autowired
	GetAllByNameMapper getAllByNameMapper;
	
	@Override
	public List<User> getAllService(String name) {
		
		List<User> all = getAllByNameMapper.getUser(name);
		return all;
	}
	
}
