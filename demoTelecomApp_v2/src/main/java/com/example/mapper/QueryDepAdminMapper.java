package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.Department;
import com.example.model.User;



public interface QueryDepAdminMapper {
	@Results({
		@Result(property = "user_login_name", column = "user_login_name"),
		@Result(property = "name", column = "name"),
		@Result(property = "text_number", column = "text_number"),
		@Result(property = "administrator_privileges", column = "administrator_privileges"),
		@Result(property = "user_dep_id", column = "user_dep_id")
	})
	@Select("SELECT * FROM users_telecom WHERE administrator_privileges='1'")
	public List<User> queryDepAdminList();
	
	@Results({
		@Result(property = "department_id", column = "department_id"),
		@Result(property = "department_name", column = "department_name"),
		@Result(property = "department_type", column = "department_type"),
		@Result(property = "department_sup_id", column = "department_sup_id"),
		@Result(property = "department_sup_name", column = "department_sup_name")
	})
	@Select("SELECT * FROM `department_telecom` WHERE department_id= #{depId}")
	public Department getSupDepMapper(String string);
	
	
	@Select("SELECT * FROM `department_telecom` WHERE department_id= #{depId}")
	public Department getDepartmentNameMapper(String depId);
}
