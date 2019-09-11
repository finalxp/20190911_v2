package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.QueryFuzzyMapper;
import com.example.model.User;
import com.example.service.IQueryFuzzyService;

/**
 * 模糊查询实现类
 * @author leo
 * 
 * @date 2019年8月26日 下午1:28:20
 */
@Service
public class QueryFuzzyService implements IQueryFuzzyService{

	@Autowired
	QueryFuzzyMapper queryFuzzyMapper;
	
	@Override
	public List<User> queryFuzzyService(String namePart) {
		String addName = "%"+namePart+"%";
		System.out.println(addName);
		List<User> queryFuzzy = queryFuzzyMapper.queryFuzzy(addName);
		
		return queryFuzzy;
	}
	
	
}
