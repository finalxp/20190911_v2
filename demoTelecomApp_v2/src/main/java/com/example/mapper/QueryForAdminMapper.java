package com.example.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.User;

public interface QueryForAdminMapper {
	@Results({
		@Result(property = "user_login_name", column = "user_login_name"),
		@Result(property = "name", column = "name"),
		@Result(property = "text_number", column = "text_number"),
		@Result(property = "administrator_privileges", column = "administrator_privileges"),
		@Result(property = "user_dep_id", column = "user_dep_id")
	})
	@Select("SELECT administrator_privileges FROM users_telecom WHERE user_login_name = #{userId}")
	
	public User queryForAdmin(String userId);
}
