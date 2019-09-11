package com.example.service;

import com.example.utils.ResponseTemplate;
import com.example.utils.ResponseTemplate_toQueryAdmin;

public interface IQueryForAdminService {
	public ResponseTemplate_toQueryAdmin queryForAdmin(String userId);
}
