package com.example.dao;

import java.util.List;

import com.example.model.Department;
import com.example.model.User;


public interface IGetSubDepUserDao {
	List<Department> getSubDepUserDao(String depId);
	List<User> getSubUserDao(String depId);
	
}
