package cn.productivetech.shtelcom.enrol.api;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import cn.productivetech.shtelcom.enrol.api.response.RspApi;
import cn.productivetech.shtelcom.enrol.uarest.core.UAException;
import cn.productivetech.shtelcom.enrol.uarest.payload.ErrorCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class BaseApiController {

	private Logger logger = LoggerFactory.getLogger("Api");

	protected <T> List<T> selectByPageNumSize(int page, int pageSize, Supplier<List<T>> s) {
		PageHelper.startPage(page, pageSize);
		PageInfo<T> pageInfo = new PageInfo<>(s.get());
		PageHelper.clearPage();
		return pageInfo.getList();
	}

	@ExceptionHandler
	public RspApi exp(HttpServletRequest request, Exception ex) {

		RspApi result = new RspApi();
		if (ex instanceof HttpMessageNotReadableException) {
			logger.error("ERROR", ex);
			result.setRetCode(ErrorCode.INVALID_REQUEST);
			result.setRetMsg("INVALID_REQUEST");

		} else if (ex instanceof SQLException || ex instanceof DataAccessException) {
			logger.error("ERROR", ex);
			if (isConnectionException(ex)) {
				result.setRetCode(ErrorCode.DB_CONNECTION_ERROR);
				result.setRetMsg("DB_CONNECTION_ERROR");
			} else {
				result.setRetCode(ErrorCode.DATABASE_ERROR);
				result.setRetMsg("DATABASE_ERROR");
			}
		} else if (ex instanceof UAException) {
			UAException e = (UAException) ex;
			result.setRetCode(e.getCode());
			if ("105".equals(e.getCode())) {
				result.setRetMsg("请先注册声纹");
			} else if ("107".equals(e.getCode())) {
				result.setRetMsg("语音质量不足，请保持环境安静，再说一次！");
			} else if ("113".equals(e.getCode())) {
				// 验证未通过
				result.setRetMsg(e.getMessage());
			} else {
				logger.error("ERROR", ex);
				result.setRetMsg(e.getMessage());
			}
		} else {
			logger.error("ERROR", ex);
			result.setRetCode(101);
			result.setRetMsg("generl error");
		}
		return result;
	}

	private boolean isConnectionException(Throwable e) {
		Throwable next = e;
		do {
			if ((next instanceof SocketException) || (next instanceof SocketTimeoutException)
					|| (next instanceof UnknownHostException))
				return true;
		} while ((next = next.getCause()) != null);
		return false;
	}
}