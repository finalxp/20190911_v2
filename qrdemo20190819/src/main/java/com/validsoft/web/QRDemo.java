package com.validsoft.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import com.validsoft.service.impl.QRServiceImpl;


@Controller

public class QRDemo {
	
	@Autowired
	QRServiceImpl qrServiceImpl;
	
	
	/*@ResponseBody
	@RequestMapping("/qrcode")*/
	@RequestMapping(value = "/qrcode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String qrcode(HttpServletRequest request) throws IOException{
		// 读取请求内容
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				// 将资料解码
				String reqBody = sb.toString();
			
		
				String qrCodeService = qrServiceImpl.getQRCodeService(reqBody);
		
		return qrCodeService;
		
    }
}
