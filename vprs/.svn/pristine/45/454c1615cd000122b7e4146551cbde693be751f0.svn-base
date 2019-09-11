package com.pccc.vprs.servicedisplay.bams.common;

import java.util.Map;
import com.eos.system.annotation.Bizlet;
import com.pccc.touda.proxy.Invoker;
import com.pccc.touda.proxy.ProxyContext;
import com.pccc.touda.common.Version;
import com.pccc.touda.proxy.api.Result;
/**
 * @author A146714
 * @date 2017-01-19 10:27:05
 *
 */
@Bizlet("公共业务抽象类")
public abstract class AbstractService {
    public static Result invoke(String param,Map<String,Object> requestMap){
    	ProxyContext context = Invoker.initContext(param, Version.DefaultVersion, null, null, requestMap, false);
    	return Invoker.invoke(context);
    	
    }

    public static Result invoke1(String param,Object request){
    	ProxyContext context = Invoker.initProxyContext(param, Version.DefaultVersion, null, null, request, false);
    	return Invoker.invoke(context);
    	
    }
}
