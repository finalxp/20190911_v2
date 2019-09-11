package cn.xs.erp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public static String toJson(Object obj){
    	ObjectMapper mapper = new ObjectMapper();
		String retResult = null;
		try {
			retResult = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("ERROR", e);
			retResult = "{\"retCode\":1010,\"retMsg\":\"数据异常\"}";
		}
		return retResult;
    }
}
