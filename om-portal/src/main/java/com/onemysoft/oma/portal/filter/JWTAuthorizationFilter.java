package com.onemysoft.oma.portal.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.oma.portal.exception.TokenIsExpiredException;
import com.onemysoft.oma.portal.service.impl.UserDetailsServiceImpl;
import com.onemysoft.oma.portal.utils.JwtTokenUtils;

/**
 * @author zongshuo
 * 
 */

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
	@Autowired
    private JwtTokenUtils jwtTokenUtils;
	
	private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/test/**");
    

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

    	
        String tokenHeader = request.getHeader(jwtTokenUtils.TOKEN_HEADER);
        
        //如果header没有token，查看cookies是否有
        if (tokenHeader == null || !tokenHeader.startsWith(jwtTokenUtils.TOKEN_PREFIX)) {
            Cookie[] cookies=request.getCookies();
            if( cookies != null && cookies.length>0 ) {
                for( Cookie c:cookies ) {
    	            if( c.getName().equals("token") ) {
    	            	tokenHeader= URLDecoder.decode(c.getValue());
    	            }
                }
            }
        }

        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(jwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        try {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        } catch (TokenIsExpiredException e) {
            //返回json形式的错误信息
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String reason = e.getMessage() +" <a href=# onclick='javascript:top.location.href=\"/logout\"'>重新登录</a>";
            response.getWriter().write(reason);
            response.getWriter().flush();
            return;
        }
//        super.doFilterInternal(request, response, chain);
        chain.doFilter(request, response);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws TokenIsExpiredException {
        String token = tokenHeader.replace(jwtTokenUtils.TOKEN_PREFIX, "");
        boolean expiration = jwtTokenUtils.isExpiration(token);
        if (expiration) {
            throw new TokenIsExpiredException("token超时了");
        } else {
            String username = jwtTokenUtils.getUsername(token);
            String role = jwtTokenUtils.getUserRole(token);
            
            Map<String,Object> loginMap=new HashMap<String,Object>();
            loginMap.put("username",username);
            loginMap.put("role",role);
            SystemContext.setUserMap(loginMap);
            
            
            List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
//            UserDetails user=userDetailsService.loadUserByUsername(username);
            Set<String> permissions=StringUtils.commaDelimitedListToSet(role);
            
            for(String item:permissions) {
            	authorities.add(new SimpleGrantedAuthority(item));
            }
            
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }
        return null;
    }
    

}
