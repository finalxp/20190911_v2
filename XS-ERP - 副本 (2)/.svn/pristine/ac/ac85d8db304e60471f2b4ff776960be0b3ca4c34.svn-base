package cn.xs.erp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.xs.erp.util.JsonUtil;

@Controller
public class BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler
	public String exp(HttpServletRequest request, Exception ex) {

		request.setAttribute("ex", ex);
		logger.error("ERROR", ex);
		return "error";
	}
	
	protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    protected HttpSession getSession() {
        return getRequest().getSession();
    }
    
    protected String toJson(Object obj){
    	
		return JsonUtil.toJson(obj);
    }
}
