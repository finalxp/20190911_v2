package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.IGetAllTextService;

@RestController
public class TextController {
	@Autowired
	private IGetAllTextService getAllText;
	
	@RequestMapping("/stdBiometricLite/getText")
	public String getAllText() {
		String allText = getAllText.getAllText();
		return allText;
		
	}
	
}
