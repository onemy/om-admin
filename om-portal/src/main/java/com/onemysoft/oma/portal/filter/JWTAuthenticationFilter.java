package com.onemysoft.oma.portal.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.model.LoginUser;
import com.onemysoft.oma.portal.utils.JwtTokenUtils;


/**
 * 认证过滤器，用于登录，产生token
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
    private JwtTokenUtils jwtTokenUtils;
	
//    private ThreadLocal<Integer> rememberMe = new ThreadLocal<>();
	@Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
        	InputStream stream=request.getInputStream();

	        LoginUser loginUser = new ObjectMapper().readValue(stream, LoginUser.class);
	//            rememberMe.set(loginUser.getRememberMe() == null ? 0 : loginUser.getRememberMe());
	            return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
	            );

        } catch (IOException e) {
        	logger.error("Authentication Error: "+e.getMessage());
        	throw new AuthenticationServiceException("Authentication Error");
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        LoginUser jwtUser = (LoginUser) authResult.getPrincipal();
        logger.info("jwtUser:" + jwtUser.toString());
//        boolean isRemember = rememberMe.get() == 1;

        String role = "";
        Collection<GrantedAuthority> authorities = jwtUser.getAuthorities();
        if(authorities!=null) {
        	role=StringUtils.collectionToCommaDelimitedString(authorities);
        }

        String token = jwtTokenUtils.createToken(jwtUser.getUsername(), role, false);

        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setHeader("token", jwtTokenUtils.TOKEN_PREFIX + token);

        Result result = Result.ok().message("Authentication Success! 验证成功！");
        
        Map<String,String> map=new HashMap<String,String>();
        map.put("token",jwtTokenUtils.TOKEN_PREFIX+token);
        map.put("username", jwtUser.getUsername());
        result.setData(map);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        
    	Result result = Result.error().message("Authentication failure! 验证失败！ "+ failed.getMessage());
    	
    	response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
