package cn.xs.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.model.SystemParameterItem;
import cn.xs.erp.service.ISystemParameterService;

@Controller
public class SystemParameterController extends BaseController{

	@Autowired
	private ISystemParameterService systemParameterService;
	
	@RequestMapping("/system_parameter")
	public String home(){
		return "system_parameter/index";
	}
	
	@RequestMapping("/system_parameter/add")
	public String add(Model model){
		model.addAttribute("systemParameterItem", new SystemParameterItem());
		return "system_parameter/edit";
	}
	
	@RequestMapping("/system_parameter/edit")
	public String edit(Model model,@RequestParam int id){
		model.addAttribute("systemParameterItem", systemParameterService.selectSystemParameterByID(id));
		return "system_parameter/edit";
	}
	
	@RequestMapping(value="/system_parameter/insert",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto Insert(SystemParameterItem systemParameterItem){
		return systemParameterService.addSystemParameter(systemParameterItem,getUserID());
	}
	
	@RequestMapping(value="/system_parameter/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto update(SystemParameterItem systemParameterItem){
		return systemParameterService.updateSystemParameter(systemParameterItem,getUserID());
	}
	
	@RequestMapping(value="/system_parameter/delete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto delete(@RequestParam int id){
		return systemParameterService.deleteSystemParameter(id);
	}
	
	
	@RequestMapping(value="/system_parameter/list", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<SystemParameterItem> list(@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="rows",required=false) Integer rows){
		return systemParameterService.listSystemParameterByPage(page,rows);
	}
	
	
}
