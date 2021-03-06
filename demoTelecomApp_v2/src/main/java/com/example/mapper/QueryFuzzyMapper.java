package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.Department;
import com.example.model.User;

public interface QueryFuzzyMapper {
	@Results({
		@Result(property = "user_login_name", column = "user_login_name"),
		@Result(property = "name", column = "name"),
		@Result(property = "text_number", column = "text_number"),
		@Result(property = "administrator_privileges", column = "administrator_privileges"),
		@Result(property = "user_dep_id", column = "user_dep_id")
	})
	@Select("SELECT * FROM users_telecom WHERE NAME LIKE #{namePart}")
	public List<User> queryFuzzy(String namePart);
	
	//单位管理员调用接口时，查询上级部门的下级部门，当用户的depId在查询集合中，则返回用户信息
	@Results({
		@Result(property = "department_id", column = "department_id"),
		@Result(property = "department_name", column = "department_name"),
		@Result(property = "department_type", column = "department_type"),
		@Result(property = "department_sup_id", column = "department_sup_id"),
		@Result(property = "department_sup_name", column = "department_sup_name")
	})
	@Select("SELECT * FROM department_telecom WHERE department_sup_id=#{depId}")
	public List<Department> queryFuzzyByUnitAdmin_getSupDepId(String depId);
}
