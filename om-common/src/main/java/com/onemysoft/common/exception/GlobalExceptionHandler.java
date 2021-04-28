package com.onemysoft.common.exception;


import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.onemysoft.common.web.Result;



@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    /**
     * 指定处理什么异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message("后台异常，请联系管理员！");
    }


    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result error(BusinessException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message(e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return Result.error().code(HttpServletResponse.SC_UNAUTHORIZED).message("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Result userNotFound(BadCredentialsException e)
    {
        log.error(e.getMessage());
        return Result.error().code(HttpServletResponse.SC_BAD_REQUEST).message("用户名或者密码错误");
    }

    @ExceptionHandler(LockedException.class)
    public Result userLocked(LockedException e) {
        log.error(e.getMessage());
        return Result.error().code(HttpServletResponse.SC_FORBIDDEN).message(e.getMessage());
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public Result handleAuthenticationServiceException(AuthenticationServiceException e) {
        log.error(e.getMessage());
        return Result.error().message("验证码错误");
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result handleSQLIntegrityConstraintViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().message("违反数据完整性约束");
    }
    
}
