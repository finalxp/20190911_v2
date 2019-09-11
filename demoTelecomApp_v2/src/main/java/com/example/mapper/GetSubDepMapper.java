package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.Department;

public interface GetSubDepMapper {
	@Results({
		@Result(property = "department_id", column = "department_id"),
		@Result(property = "department_name", column = "department_name"),
		@Result(property = "department_type", column = "department_type"),
		@Result(property = "department_sup_id", column = "department_sup_id"),
		@Result(property = "department_sup_name", column = "department_sup_name")
	})
	@Select("SELECT * FROM department_telecom WHERE department_sup_id=#{depId}")
	List<Department> getSubDepMapper(String depId);
}
