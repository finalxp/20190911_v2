package cn.xs.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.xs.erp.config.Const;
import cn.xs.erp.dto.RspResultDto;
import cn.xs.erp.model.EmployeeItem;
import cn.xs.erp.service.IEmployeeService;
import cn.xs.erp.util.MD5Util;

@Controller
@ComponentScan
public class LoginController extends BaseController {

	@Autowired
	private IEmployeeService employeeService;

	@RequestMapping("/login")
	public String login(Model model) {

		return "login/index";
	}

	@RequestMapping("/login/login")
	public String getEmployee(@RequestParam(value="empNo",required=false) String empNo, @RequestParam(value="pwd",required=false) String pwd,Model model) {

		RspResultDto result = new RspResultDto();
		EmployeeItem empItem = employeeService.getEmployeeByNo(empNo);
		if (empItem == null) {
//			result.setRetCode("1001");
//			result.setRetMsg("账户不存在");
			model.addAttribute("errorEmployee", "账户不存在！");
			return "login/index";
		} else {
			String validPwd = MD5Util.getMD5(pwd + empItem.getSalt());
			if (validPwd.equals(empItem.getEmpPassword())) {
				result.setRetCode("0");
				getSession().setAttribute(Const.LOGIN_SESSION_KEY, empItem.getId());
			} else {
//				result.setRetCode("1002");
//				result.setRetMsg("密码错误");
				model.addAttribute("errorPassWord", "密码错误");
				return "login/index";
			}
		}
		return "/index";
	}

}
