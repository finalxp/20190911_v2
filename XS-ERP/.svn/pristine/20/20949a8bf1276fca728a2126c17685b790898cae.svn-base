package cn.xs.erp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.model.MenuItem;
import cn.xs.erp.service.IMenuService;

@Controller
public class MenuController extends BaseController{

	@Autowired
	private IMenuService menuService;
	
	@RequestMapping(value="/allMenus",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getFirstLevelMenus(){
		Map<String, Object> map =menuService.getFirstLevelMenus(getUserID());
		String result =toJson(map);
		return result;
	}
	
	@RequestMapping(value="/treeMenus",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getTreeMenus(){
		List<MenuItem> list =menuService.getTreeMenus();
		String result =toJson(list);
		return result;
	}
	
	@RequestMapping(value="/menu_permission/comboxList",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public List<MenuItem> comboxList(){
		return menuService.selectAll();
	}
	
}
