package com.example.dao;

import com.example.model.User;


public interface IQueryForAdminDao {
	public User queryForAdmin(String userId);
}
