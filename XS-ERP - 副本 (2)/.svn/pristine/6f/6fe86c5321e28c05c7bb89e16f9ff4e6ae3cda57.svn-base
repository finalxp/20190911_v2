package cn.xs.erp.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javassist.expr.NewArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.xs.erp.config.Const;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.service.IEmployeeService;
/**
 * 
 * @author kenny create on 2019/1/18
 *
 */
@Controller
public class EmployeeController extends BaseController{

	@Autowired
	private IEmployeeService employeeService;
	
	@RequestMapping("/employee")
	public String selectAllEmpInfo(Model model){
		model.addAttribute("employeeItem", new EmployeeItem());
		model.addAttribute("allEmployees", employeeService.selectAllEmpInfo());
		model.addAttribute("allMenus",getSession().getAttribute("allMenus"));
		
		return "employee/index";
	}
	
	@RequestMapping("/employee/addEmployee")
	public String addEmployee(EmployeeItem employeeItem, Model model,
			final RedirectAttributes reAttributes){
		if (employeeService.getEmployeeByNo(employeeItem.getEmpNo())==null) {
			EmployeeItem employeeItem2 =(EmployeeItem) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
			employeeItem.setCreateBy(employeeItem2.getId());
			employeeItem.setIsEnable((short) 1);
			employeeItem.setCreateTime(new Date());
			employeeItem.setSalt("123456");
			employeeService.addEmployee(employeeItem);
		}else {
			reAttributes.addFlashAttribute("addEmployee", "unsuccess");
		}
		return "redirect:/employee";
	}
	
	@RequestMapping("/employee/editEmployee")
	public String editEmployee(@ModelAttribute("employeeItem") EmployeeItem employeeItem, Model model,
			final RedirectAttributes reAttributes) throws ParseException{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", employeeItem.getId());
			map.put("empNo", employeeItem.getEmpNo());
			map.put("empName", employeeItem.getEmpName());
			map.put("sex", employeeItem.getSex());
			map.put("phoneNo", employeeItem.getPhoneNo());
			map.put("birthday", employeeItem.getBirthday());
			map.put("nickname", employeeItem.getNickname());
			map.put("updateBy", ((EmployeeItem)getSession().getAttribute(Const.LOGIN_SESSION_KEY)).getId());
			
			employeeService.updateEmployeeById(map);
		return "redirect:/employee";
	}
	
	@RequestMapping(value="/employee/deleteEmployee",method=RequestMethod.DELETE)
	public String deleteEmpByID(int id){
		
		employeeService.deleteByPrimaryKey(id);
		
		
		return "redirect:/employee";
	}
	//form表单提交 Date类型数据绑定
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		sFormat.setLenient(true);//指定日期/时间解析是否宽松
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sFormat,true));
	}
	
}
