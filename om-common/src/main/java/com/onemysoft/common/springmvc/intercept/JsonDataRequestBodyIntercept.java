package com.onemysoft.common.springmvc.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.springmvc.intercept.annotation.JsonDataRequestBody;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.common.web.RequestTransferData;


/**
 * 请求中加入分页参数
 * @author onemysoft
 * 
 */
public class JsonDataRequestBodyIntercept implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
        	
        	JsonDataRequestBody jsonDataRequestBody = ((HandlerMethod) handler).getMethodAnnotation(JsonDataRequestBody.class);
        	
        	if(OMAssert.isNotNull(jsonDataRequestBody)){
        		
        		String pageParam = jsonDataRequestBody.page();
            	String startParam = jsonDataRequestBody.start();
            	String rowsParam = jsonDataRequestBody.rows();
            	
            	String page = request.getParameter(pageParam);
            	String start = request.getParameter(startParam);
            	String rows = request.getParameter(rowsParam);
            	
            	String draw = request.getParameter("draw");
                
            	RequestTransferData requestTransferData = new RequestTransferData();
            	
            	if(page != null && !"".equals(page))
            		requestTransferData.setPage(Integer.parseInt(page));
            	if(start != null && !"".equals(start))
            		requestTransferData.setStart(Integer.parseInt(start));
            	if(rows != null && !"".equals(rows))
            		requestTransferData.setRows(Integer.parseInt(rows));
            	if (draw != null && !"".equals(draw))
            		requestTransferData.setDraw(Integer.parseInt(draw));
            	
            	SystemContext.setRequestTransferData(requestTransferData);
        	}
        }
       
        return true;   
     }
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView) throws Exception{
		
	}
	@Override
	public void afterCompletion(  
            HttpServletRequest request, HttpServletResponse response,   
            Object handler, Exception ex)  
            throws Exception{
		SystemContext.removeRequestTransferData();
	}
}
