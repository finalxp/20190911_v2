package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TreePoint;

import com.example.service.impl.GetSupDepService;

@RestController
@RequestMapping("/stdBiometricLite")
public class GetSupDepController {
	@Autowired
	private GetSupDepService getSupDepService;
	
	@RequestMapping("getSupDep")
	public List<TreePoint> GetSupDep(){
		return getSupDepService.getSupDep();
	}
}
