package cn.xs.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.model.PageInfo;
import cn.xs.erp.model.SystemLogItem;
import cn.xs.erp.service.ISystemLogService;

@Controller
public class SystemLogController extends BaseController{
	@Autowired
	private ISystemLogService systemLogService;
	
	@RequestMapping("/conlog")
	public String home(){
		return "system_log/index";
	}
	
	@RequestMapping(value="/conlog/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<SystemLogItem> list(
			@RequestParam(value = "page", required = false) int page, 
			@RequestParam(value = "rows", required = false) int rows,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime
			){
		
		return systemLogService.selectAllLogByPage(page,rows,startTime,endTime);
	}
	
}
