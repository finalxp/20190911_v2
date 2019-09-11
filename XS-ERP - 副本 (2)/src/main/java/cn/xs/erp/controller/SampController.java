package cn.xs.erp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.xs.erp.service.IEmployeeService;
import cn.xs.erp.service.IPermissionService;
import cn.xs.erp.service.IMenuService;


//@Controller
@RestController
public class SampController extends BaseController {
	
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IPermissionService menuPermissionService;
	
/*	@RequestMapping("/employee")
	public String employee(Model model) {
		model.addAttribute("title", "welcome to emp page");
		return "employee/index";
	}*/
	
/*	@RequestMapping("/employee/list")
	public String employeeList(Model model) {
		model.addAttribute("title", "layout demo");
		return "employee/list";
	}*/
	
	/*@RequestMapping(value="/allMenus",method=RequestMethod.POST)
	public List<Object> findAllMenus(HttpServletRequest request, HttpServletResponse response){
		List<Object> listZTree = new ArrayList<Object>(); 
		EmployeeItem item =employeeService.getEmployeeByNo(((EmployeeItem)getSession().getAttribute(Const.LOGIN_SESSION_KEY)).getEmpNo());
		List<MenuPermissionItem> list= menuPermissionService.getPermissionsByEmpID(item.getId());
		List<MenuItem> listMenuItems = new ArrayList<MenuItem>();
		for (MenuPermissionItem mpItem : list) {
			if (menuService.getMenuItemById(mpItem.getMenuId())!=null) {
				listMenuItems.add(menuService.getMenuItemById(mpItem.getMenuId()));
			}
		}
		String str="";
		for(int i = 0; i < listMenuItems.size(); i++){
			MenuItem menuItem = listMenuItems.get(i);
			if(i==0){
				str="[{\"id\":" +menuItem.getId() + ", \"parentId\":"+menuItem.getParentId()+", \"name\":\""+menuItem.getMenuName()+"\"}";//封装ztree需要格式的字符串
			}else if (i==(listMenuItems.size()-1)) {
				str="{\"id\":" +menuItem.getId() + ", \"parentId\":"+menuItem.getParentId()+", \"name\":\""+menuItem.getMenuName()+"\"}]";//封装ztree需要格式的字符串
			}else {
				str="{\"id\":" +menuItem.getId() + ", \"parentId\":"+menuItem.getParentId()+", \"name\":\""+menuItem.getMenuName()+"\"}";//封装ztree需要格式的字符串
			}
			listZTree.add(str);
		}
		return listZTree;
	}*/
	
	
}
