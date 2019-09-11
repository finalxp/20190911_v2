package com.example.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.IQueryFuzzyService;

/**
 * 模糊查询
 * 
 * @author leo
 * 
 * @date 2019年8月26日 下午1:05:36
 */

@RestController
@RequestMapping("/stdBiometricLite")
public class QueryFuzzy {
	@Autowired
	IQueryFuzzyService queryFuzzyService;
	
	@RequestMapping(value = "queryFuzzy", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<User> queryFuzzy(HttpServletRequest request) throws Exception{
		
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
		Object object = map.get("namePart");
		String namePart = object.toString();
		
		return queryFuzzyService.queryFuzzyService(namePart);
		
	}
	
}
