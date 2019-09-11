package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






import com.example.mapper.QueryForAdminMapper;
import com.example.model.User;
import com.example.service.IQueryForAdminService;
import com.example.utils.ResponseTemplate;
import com.example.utils.ResponseTemplate_toQueryAdmin;

@Service
public class QueryForAdminService implements IQueryForAdminService{

	@Autowired
	QueryForAdminMapper queryForAdminMapper;
	
	ResponseTemplate_toQueryAdmin responseTemplate = new ResponseTemplate_toQueryAdmin();
	
	@Override
	public ResponseTemplate_toQueryAdmin queryForAdmin(String userId) {
		int queryForAdmin = 0;
		try {
			queryForAdmin = queryForAdminMapper.queryForAdmin(userId).getAdministrator_privileges();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseTemplate.error();
		}
		
		
		return responseTemplate.ok(queryForAdmin);
	}

}
