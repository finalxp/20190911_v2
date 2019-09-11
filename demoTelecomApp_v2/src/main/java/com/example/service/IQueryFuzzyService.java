package com.example.service;

import java.util.List;

import com.example.model.User;

/**
 * 模糊查询接口
 * 
 * @author leo
 * 
 * @date 2019年8月26日 下午1:26:41
 */
public interface IQueryFuzzyService {
	public List<User> queryFuzzyService(String namePart);
}
