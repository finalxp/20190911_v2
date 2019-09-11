package cn.xs.erp.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xs.erp.config.Const;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.DepartmentItem;
import cn.xs.erp.model.PageInfo;
import cn.xs.erp.service.IDepartmentService;

@Controller
public class DepartmentController extends BaseController {

	@Autowired
	private IDepartmentService departmentService;

	@RequestMapping("/department")
	public String selectAllDepInfo(Model model) {
		return "department/index";
	}

	@RequestMapping("/department/add")
	public String add(Model model) {
		model.addAttribute("dItem", new DepartmentItem());
		return "department/edit";
	}

	@RequestMapping("/department/edit")
	public String edit(Model model, @RequestParam int id) {
		DepartmentItem dItem = departmentService.getDepartmentById(id);
		model.addAttribute("dItem", dItem);
		return "department/edit";
	}

	// 员工管理页面，加载部门下拉框
	@RequestMapping(value = "/department/comboboxList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<DepartmentItem> selectDepartementcomboboxList() {
		return departmentService.selectAllDepInfo();
	}

	@RequestMapping(value = "/department/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<DepartmentItem> selectDepartementList(
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "rows", required = false) int rows) {
		return departmentService.selectDepartmentByPage(page, rows);
	}

	@RequestMapping("/department/insert")
	@ResponseBody
	public RspResultDto insert(DepartmentItem departmentItem) {
		return departmentService.AddDepartment(departmentItem, getUserID());
	}

	@RequestMapping("/department/editDepartment")
	@ResponseBody
	public String editDepartment(DepartmentItem departmentItem)
			throws ParseException {
		departmentItem.setUpdateBy((Integer) getSession().getAttribute(
				Const.LOGIN_SESSION_KEY));
		departmentItem.setUpdateTime(new Date());
		return "success";
	}

	@RequestMapping(value = "/department/deleteDepartment", method = RequestMethod.DELETE)
	public String deleteDepByID(int id) {
		DepartmentItem dItem = departmentService.getDepartmentById(id);
		dItem.setUpdateBy((Integer) getSession().getAttribute(
				Const.LOGIN_SESSION_KEY));
		dItem.setUpdateTime(new Date());
		return "redirect:/department";
	}
}
