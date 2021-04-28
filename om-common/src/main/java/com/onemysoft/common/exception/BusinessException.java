package com.onemysoft.common.exception;

/**
 * 
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -578607331764730641L;

    private final String code;
    private final String msg;

    public BusinessException(String defaultMessage){
        this(null, defaultMessage,null);
    }
    
    public BusinessException(String code,String msg){
        this(code, msg, null);
    }

    public BusinessException(String code,String msg,Throwable cause) {

        super(msg, cause);
        this.msg = msg;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
