package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.GetUserMapper;
import com.example.model.User;
import com.example.service.IGetUserService;

@Service
public class GetUserService implements IGetUserService{

	@Autowired
	GetUserMapper getUserMapper;
	
	@Override
	public List<User> getUserService(String depId) {
		List<User> user = getUserMapper.getUser(depId);
		
		return user;
	}

}
