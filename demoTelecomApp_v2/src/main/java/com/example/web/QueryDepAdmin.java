package com.example.web;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.EmpDepList;
import com.example.service.IQueryDepAdminService;


/**
 * 
 * @author leo
 * 
 * @date 2019年9月23日 下午2:48:15
 * 
 * 查询所有内部组织下的部门管理员
 */

@RestController
@RequestMapping("/stdBiometricLite")
public class QueryDepAdmin {
	
	@Autowired
	private IQueryDepAdminService queryDepAdminService;
	
	@RequestMapping(value = "queryDepAdmin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")	
	@ResponseBody
	public List<EmpDepList> queryDepAdmin(HttpServletRequest request) {
		
		List<EmpDepList> queryDepAdmin = queryDepAdminService.queryDepAdmin();
		
		return queryDepAdmin;
		
	}
}
