package com.example.web;

import java.io.BufferedReader;

import java.io.InputStreamReader;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.IVerifyService;

import com.example.utils.EncryptionValidsoft;
import com.example.utils.StringGetJsonValue;
import com.example.utils.VerifyApi;

@Controller
public class VerifyController {
	@Autowired
	IVerifyService verifyService;

	@RequestMapping(value = "/stdBiometricLite/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String receivePost(HttpServletRequest request) throws Exception {

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
		List<String> stringGetJsonValue = StringGetJsonValue.stringGetJsonValue(reqBody);
		
		//加密测试  加密
		/*String deSencode_id = EncryptionValidsoft.encrypt(stringGetJsonValue.get(1));
		System.out.println("deSencode_id"+deSencode_id);
		String deSencode_audio = EncryptionValidsoft.encrypt(stringGetJsonValue.get(2));*/
		//System.out.println("deSencode_audio--->"+deSencode_audio);
		//加密测试  解密
		/*String name_id_decrypt = EncryptionValidsoft.decrypt(deSencode_id);
		//System.out.println("name_id_decrypt"+name_id_decrypt);
		String audio_base64_decrypt = EncryptionValidsoft.decrypt(deSencode_audio);*/
		//System.out.println("audio_base64_decrypt-->"+audio_base64_decrypt);
		/**
		 * 正式发布	只解密
		 */
		// 解密
		String name_id_decrypt = EncryptionValidsoft.decrypt(stringGetJsonValue.get(1));
		System.out.println("name_id_decrypt"+name_id_decrypt);
		String audio_base64_decrypt = EncryptionValidsoft.decrypt(stringGetJsonValue.get(2));
		//System.out.println("audio_base64_decrypt-->"+audio_base64_decrypt); 
		
		
		//加密后解密发送
		String verifyPost = VerifyApi.verifyPost(stringGetJsonValue.get(0), name_id_decrypt, audio_base64_decrypt);
		
		// 发送请求
		String sendPostToVerify = verifyService.sendPostToVerify(verifyPost);
		return sendPostToVerify.toString();
	}

}
