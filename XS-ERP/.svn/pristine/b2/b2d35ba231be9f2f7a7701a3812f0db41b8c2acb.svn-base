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
import cn.xs.erp.service.IPermissionService;

@Controller
public class PermissionController extends BaseController{

	@Autowired
	private IPermissionService permissionServie;
	
	@RequestMapping("/menu_permission")
	public String home(){
		return "/menu_permission/index";
	}
	@RequestMapping("/menu_permission/edit")
	public String edit(Model model,@RequestParam int id){
		EmployeeItem employeeItem = permissionServie.getEmpMenuByID(id);
		model.addAttribute("employeeMenuPermission", permissionServie.getEmpMenuByID(id));
		model.addAttribute("menuPermission", toJson(employeeItem.getMenuPermission()));
		return "menu_permission/edit";
	}
	
	@RequestMapping(value="/menu_permission/list",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public PageInfo<EmployeeItem> list(@RequestParam(value = "page", required = false) int page, 
			@RequestParam(value = "rows", required = false) int rows,
			@RequestParam(value = "depID", required = false) String depID,
			@RequestParam(value = "empNo", required = false) String empNo,
			@RequestParam(value = "menuName", required = false) String menuName
			
			){
		return permissionServie.selectMenuPermissionByPage(page, rows,depID,empNo,menuName);
	}
	
	
	@RequestMapping(value="/menu_permission/update",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public RspResultDto update(EmployeeItem eItem,@RequestParam(value="menuID",required=false) int[] menuID){
		return permissionServie.updateEmpMenuPermission(eItem,menuID,getUserID());
	}
	
}
