package com.example.utils;

import java.util.HashMap;

/**
 * @author kenny_peng
 * @created 2019/7/25 10:44
 */
public class ResponseTemplate_toQueryAdmin extends HashMap<String, Object> {

   

   

    public static ResponseTemplate_toQueryAdmin ok(Object o){
        ResponseTemplate_toQueryAdmin responseTemplate = new ResponseTemplate_toQueryAdmin();
        responseTemplate.put("status", o);
        return responseTemplate;
    }

    public static ResponseTemplate_toQueryAdmin error(){
    	 ResponseTemplate_toQueryAdmin rst = new ResponseTemplate_toQueryAdmin();
         return rst.put("status", "error");
    }

    public static ResponseTemplate_toQueryAdmin error(Object o){
        ResponseTemplate_toQueryAdmin rst = new ResponseTemplate_toQueryAdmin();
        rst.put("status", "error");
       
        return rst;
    }

    @Override
    public ResponseTemplate_toQueryAdmin put(String key, Object value){
        super.put(key, value);
        return this;
    }

}
