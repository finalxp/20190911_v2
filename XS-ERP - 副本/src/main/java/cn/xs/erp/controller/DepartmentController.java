package cn.xs.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.service.IDepartmentService;

@Controller
@ComponentScan
public class DepartmentController extends BaseController {

	@Autowired
	private IDepartmentService departmentService;

	@RequestMapping("/department")

/*	public String selectAllDepInfo(Model model) {
		List<DepartmentItem> allDepartments = departmentService
				.selectAllDepInfo();

		System.out.println(allDepartments.isEmpty()
				+ "----------isEmpty----------");*/

	public String selectAllDepInfo(Model model){
		List<DepartmentItem> allDepartments=departmentService.selectAllDepInfo();
		
		model.addAttribute("departmentItem", new DepartmentItem());
		model.addAttribute("allDepartments", allDepartments);
		model.addAttribute("allMenus", getSession().getAttribute("allMenus"));

		return "department/index";
	}
}
