package com.example.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Department;
import com.example.service.IGetSubDepService;


@RestController
@RequestMapping("/stdBiometricLite")
public class GetSubDepCotroller {
	
	@Autowired
	IGetSubDepService getSubDepService;
	
	@RequestMapping(value = "getSubDep", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Department> GetSubDepUser(HttpServletRequest request) throws Exception {

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
		Object object = map.get("depId");
		String depId = object.toString();
		System.out.println("---------------"+depId);
		
		return getSubDepService.getSubDepService(depId);
	}
}
