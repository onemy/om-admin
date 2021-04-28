package com.onemysoft.common.utils;

import java.util.Collection;
import java.util.Map;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.exception.BusinessException;

/**
 * 断言类，在Spring断言的基础上进行的扩展
 * 
 */
@SuppressWarnings("rawtypes")
public class OMAssert {

    private OMAssert() {
    }

    public static void notNull(Object obj){

        try{
            org.springframework.util.Assert.notNull(obj,SystemContext.SYS_ERROR_MSG);

        }catch(IllegalArgumentException e){

            throw new BusinessException(SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG,e);
        }
    }
    
    public static boolean isNotNull(Object obj){
    	
    	try {
    		
			notNull(obj);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(Object obj,String code,String msg){

        try{
            org.springframework.util.Assert.notNull(obj,msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }
    
    public static void notNull(String str){
        notNull(str, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(String str){
    	
    	try {
    		
			notNull(str);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(String str,String code,String msg){

        try{
            org.springframework.util.Assert.notNull(str,msg);
            if("".equals(str))throw new IllegalArgumentException(msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notNull(Integer value){

        notNull(value, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(Integer value){
    	
    	try {
    		
			notNull(value);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(Integer value,String code,String msg){

        try{
            org.springframework.util.Assert.notNull(value,msg);
            if(value <= 0)throw new IllegalArgumentException(msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notNull(Double value){

        notNull(value, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(Double value){
    	
    	try {
    		
			notNull(value);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(Double value,String code,String msg){

        try{
            org.springframework.util.Assert.notNull(value,msg);
            if(value <= 0)throw new IllegalArgumentException(msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notNull(Float value){

        notNull(value, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(Float value){
    	
    	try {
    		
			notNull(value);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(Float value,String code,String msg){

        try{
            org.springframework.util.Assert.notNull(value,msg);
            if(value <= 0)throw new IllegalArgumentException(msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notNull(int value){

        notNull(value, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(int value){
    	
    	try {
    		
			notNull(value);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(int value,String code,String msg){

        try{
            if(value <= 0)throw new IllegalArgumentException(msg);
        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notNull(double value){

        notNull(value, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(double value){
    	
    	try {
    		
			notNull(value);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(double value,String code,String msg){

        try{
            if(value <= 0)throw new IllegalArgumentException(msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notNull(float value){

        notNull(value, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotNull(float value){
    	
    	try {
    		
			notNull(value);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notNull(float value,String code,String msg){

        try{
            if(value <= 0)throw new IllegalArgumentException(msg);
        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notEmpty(Collection collection){

        notEmpty(collection, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotEmpty(Collection collection){
    	
    	try {
    		
    		notEmpty(collection);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notEmpty(Collection collection,String code,String msg){

        try{
            org.springframework.util.Assert.notEmpty(collection,msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notEmpty(Object[] objArray){

        notEmpty(objArray, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotEmpty(Object[] objArray){
    	
    	try {
    		
    		notEmpty(objArray);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notEmpty(Object[] objArray,String code,String msg){

        try{
            org.springframework.util.Assert.notEmpty(objArray,msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }

    public static void notEmpty(Map map){

        notEmpty(map, SystemContext.SYS_ERROR_CODE, SystemContext.SYS_ERROR_MSG);
    }
    
    public static boolean isNotEmpty(Map map){
    	
    	try {
    		
    		notEmpty(map);
			return true;
		} catch (BusinessException e) {
			
			return false;
		}
    }

    public static void notEmpty(Map map,String code,String msg){

        try{
            org.springframework.util.Assert.notEmpty(map,msg);

        }catch(IllegalArgumentException e){

            throw new BusinessException(code, msg,e);
        }
    }
}
