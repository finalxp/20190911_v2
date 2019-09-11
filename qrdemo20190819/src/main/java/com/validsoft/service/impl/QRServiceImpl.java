package com.validsoft.service.impl;

import java.util.Date;





import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.validsoft.service.QRService;
import com.validsoft.utils.TwoDimensionCode;

@Service
public class QRServiceImpl implements QRService{

	//保存路径
	@Value("${spring.application.imgPath}")
	private String imgPath;
	
	@Override
	public String getQRCodeService(String info) {
		
		//生成唯一ID
		int uuid = (int) (Math.random() * 100000);
		//二维码内容
		String content = info;
		//生成二维码
		String imgName =  uuid + "_" + (int) (new Date().getTime() / 1000) + ".png";
		
		//保存路径
		String imgPath2 = imgPath + imgName;
		
		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCode(content, imgPath2, "png");
		
		
		return imgPath2;
	}

}
