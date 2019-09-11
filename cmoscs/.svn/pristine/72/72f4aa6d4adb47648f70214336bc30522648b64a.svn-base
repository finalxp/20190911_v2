package cn.productivetech.cmos.zhongbao.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 接受业务请求url基础控制器
 * @author kenny_peng
 * @created 2019-04-16
 */
@Controller
public class BaseController {

	//日志
	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	//系统异常捕获
	@ExceptionHandler
	public String errorPage(HttpServletRequest request, Exception exception) {
		request.setAttribute("error", exception);
		logger.error("Error", exception);
		return null;
	}
}
