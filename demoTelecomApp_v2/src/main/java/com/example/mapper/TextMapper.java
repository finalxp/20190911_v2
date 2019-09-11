package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.Text;
public interface TextMapper {
	@Select("SELECT * FROM text_telecom")
	@Results({
		@Result(property = "textId",  column = "text_id"),
		@Result(property = "textWord", column = "text_word"),
		@Result(property = "textStatus", column = "text_status")
	})
	List<Text> getAllTextMapper();
	
	
}
