package cn.xs.erp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.dto.RspResultMessage;

@Controller
public class TechnologyDepartmentController extends BaseController{

	@RequestMapping("/technology")
	public String technology(Model model){
		
		return "technology_department/index";
	}
	@RequestMapping("/technology_department/addNotification")
	public String tech(){
		return "technology_department/add";
	}
	@RequestMapping(value="/technology_department/addHtml",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto addHtml(@RequestParam String htmlText,HttpServletRequest request, HttpServletResponse response){
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().replace("/target/classes/", "");
		String finalPath=path+"/src/main/resources/public";
		String newFileName = System.currentTimeMillis() + new Random().nextInt(1000) + ".html";
		String htmlTextEnd="<!DOCTYPE html>"+
				"<html xmlns='http://www.w3.org/1999/xhtml'>"+
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<style type='text/css'>"+
				"table { border-top: 1px solid #ccc; border-left: 1px solid #ccc; }"+
				"table td, table th { border-bottom: 1px solid #ccc; border-right: 1px solid #ccc; padding: 3px 5px; }"+
				"table th { border-bottom: 2px solid #ccc; text-align: center; }"+
				"blockquote { display: block; border-left: 8px solid #d0e5f2; padding: 5px 10px; margin: 10px 0; line-height: 1.4; font-size: 100%; background-color: #f1f1f1; }"+
				"code { display: inline-block; *display: inline; *zoom: 1; background-color: #f1f1f1; border-radius: 3px; padding: 3px 5px; margin: 0 3px;}"+
				"pre code { display: block; }"+
				"ul, ol { margin: 10px 0 10px 20px; }"+
				"</style> </head>"+
				"<body>"+ htmlText +"</body></html>";
		try {
			File file = new File(finalPath+"/"+newFileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream outStream = new FileOutputStream(file);
			byte[] bs = htmlTextEnd.getBytes();
			outStream.write(bs);
			outStream.close();
			return new RspResultDto(RspResultMessage.AddHtmlPageSuccess);
		} catch (IOException e) {
			e.printStackTrace();
			return new RspResultDto(RspResultMessage.AddHtmlPageFailed);
		}
	}
	
	
	@RequestMapping("/marketing")
	public String marketing(){
		return "technology_department/index";
	}
	
	
	
}
