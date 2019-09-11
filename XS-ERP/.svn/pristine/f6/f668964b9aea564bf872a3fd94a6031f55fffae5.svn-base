package cn.xs.erp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDoorService;

@Controller
public class DoorPermissionController extends BaseController{
	
	@Autowired
	private IDoorService doorService;
	
	@RequestMapping("/door_manager")
	public String doorManagerHome(){
		return "/door_manager/index";
	}

	@RequestMapping("/door_manager/edit")
	public String edit(Model model,@RequestParam int id){
		EmployeeItem employeeItem = doorService.selectDoorPermission(id);
		model.addAttribute("empDoorPermission", employeeItem);
		model.addAttribute("doorPermission", toJson(employeeItem.getDoorPermission()));
		return "/door_manager/edit";
	}
	
	@RequestMapping(value="/door_manager/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public RspResultDto updateDoorPermission(EmployeeItem eItem,@RequestParam(value="doorID",required=false) int[] doorID){
		return doorService.updateDoorPermission(eItem,doorID,getUserID());
	}
	
	
	@RequestMapping(value="/door_manager/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<EmployeeItem> list(@RequestParam(value="page",required=false)Integer page,
			@RequestParam(value="rows",required=false)Integer rows,
			@RequestParam(value="depID",required=false)String depID,
			@RequestParam(value="empNo",required=false)String empNo,
			@RequestParam(value="doorNo",required=false)String doorNo
			){
		return doorService.selectDoorPermissionByPage(page,rows,depID,empNo,doorNo,getUserID());
	}
	
	
	
	
	
	
	
}
