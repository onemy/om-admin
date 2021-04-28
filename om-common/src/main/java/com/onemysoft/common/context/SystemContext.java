package com.onemysoft.common.context;

import java.util.Map;

import com.onemysoft.common.web.RequestTransferData;;

/**
 * 
 */
public class SystemContext {

    private static ThreadLocal<RequestTransferData> requestTransferData = new ThreadLocal<RequestTransferData>();
    private static ThreadLocal<Map<String,Object>> userMap = new ThreadLocal<Map<String,Object>>();

    public static void setRequestTransferData(RequestTransferData _jsonData){
        requestTransferData.set(_jsonData);
    }

    public static RequestTransferData getRequestTransferData(){
        return requestTransferData.get();
    }

    public static void removeRequestTransferData(){
        requestTransferData.remove();
    }
    
    public static void setUserMap(Map<String,Object> _userMap){
    	userMap.set(_userMap);
    }

    public static Map<String,Object> getUserMap(){
        return userMap.get();
    }

    public static void removeUserMap(){
    	userMap.remove();
    }

    public final static String SYS_ERROR_CODE = "999";
    public final static String SYS_ERROR_MSG = "系统错误,请联系管理员!";
}
