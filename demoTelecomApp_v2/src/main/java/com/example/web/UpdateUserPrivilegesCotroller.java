package com.example.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.service.IUpdateUserPrivilegesService;
import com.example.utils.ResponseTemplate;
/**
 * 
 * 应该传用户名和所修改的类型，设置为0，或1
 * 
 * @author leo
 * 
 * @date 2019年8月28日 上午10:37:29
 */
@RestController
@RequestMapping("/stdBiometricLite")
public class UpdateUserPrivilegesCotroller {

	@Autowired
	IUpdateUserPrivilegesService updateUserPrivilegesService;

	@RequestMapping(value = "updateUserPrivilegesAs1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseTemplate updateUserPrivilegesAs1Cotroller(HttpServletRequest request) throws IOException {

		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		// 将资料解码
		String reqBody = sb.toString();
		// 获取参数
		Map<String, Object> map = JSONObject.fromObject(reqBody);
		Object object = map.get("userId");
		String userId = object.toString();

		return updateUserPrivilegesService.updateUserPrivilegesAs1(userId);
	}

	
	
	@RequestMapping(value = "updateUserPrivilegesAs0", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseTemplate updateUserPrivilegesAs0Cotroller(HttpServletRequest request) throws IOException {

		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		// 将资料解码
		String reqBody = sb.toString();
		// 获取参数
		Map<String, Object> map = JSONObject.fromObject(reqBody);
		Object object = map.get("userId");
		String userId = object.toString();

		return updateUserPrivilegesService.updateUserPrivilegesAs0(userId);
	}
	
	
	@RequestMapping(value = "updateUserPrivilegesAs2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseTemplate updateUserPrivilegesAs2Cotroller(HttpServletRequest request) throws IOException {

		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		// 将资料解码
		String reqBody = sb.toString();
		// 获取参数
		Map<String, Object> map = JSONObject.fromObject(reqBody);
		Object object = map.get("userId");
		String userId = object.toString();

		return updateUserPrivilegesService.updateUserPrivilegesAs2(userId);
	}
}
