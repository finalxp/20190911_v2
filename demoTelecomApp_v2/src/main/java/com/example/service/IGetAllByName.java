package com.example.service;

import java.util.List;

import com.example.model.User;

public interface IGetAllByName {
	List<User> getAllService(String name);
}
