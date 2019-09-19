package com.example.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Update;

public interface UpdateUserPrivilegesMapper {
	@Results({
		@Result(property = "user_login_name", column = "user_login_name"),
		@Result(property = "name", column = "name"),
		@Result(property = "text_number", column = "text_number"),
		@Result(property = "administrator_privileges", column = "administrator_privileges"),
		@Result(property = "user_dep_id", column = "user_dep_id")
	})
	@Update("UPDATE users_telecom SET administrator_privileges = '0' WHERE user_login_name =#{userId}")
	int updateUserPrivilegesAs0(String userId);
	
	@Update("UPDATE users_telecom SET administrator_privileges = '1' WHERE user_login_name =#{userId}")
	int updateUserPrivilegesAs1(String userId);
	
	@Update("UPDATE users_telecom SET administrator_privileges = '2' WHERE user_login_name =#{userId}")
	int updateUserPrivilegesAs2(String userId);
}
