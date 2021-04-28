package com.onemysoft.common.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;



/**
 * controller返回的总数据对象
 * @author onemysoft
 * 
 */
public class Result implements Serializable {


    private Boolean success;

    private Integer code;

    private String msg;

    private Object data;



    /**
     * 把构造方法私有
     */
    private Result() {}




    /**
     * 成功静态方法
     * @return
     */
    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(HttpServletResponse.SC_OK);
        r.setMsg("成功");
        return r;
    }


    /**
     * 失败静态方法
     * @return
     */
    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        r.setMsg("失败");
        return r;
    }

    public static Result judge(int n,String msg){
        return n > 0 ? ok().message(msg + "成功") : error().message(msg +"失败");
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMsg(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }


    public Result data(Object obj){
        this.setData(obj);
        return this;
    }

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

    
}

