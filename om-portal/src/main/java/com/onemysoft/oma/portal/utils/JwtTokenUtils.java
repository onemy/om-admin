package com.onemysoft.oma.portal.utils;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author zongshuo
 * 
 */
@Component
public class JwtTokenUtils {

	@Value("${jwt.tokenHeader}")
    public String TOKEN_HEADER;
	
	@Value("${jwt.tokenHead}")
    public String TOKEN_PREFIX;

    @Value("${jwt.secret}")
    private String SECRET;
    
    private static String ISS = "onemysoft";

    // 角色的key
    private static final String ROLE_CLAIMS = "rol";

    // 过期时间是36000秒，既是10个小时
    private static final long EXPIRATION = 36000L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 创建token
    public String createToken(String username,String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public String getUserRole(String token){
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    // 是否已过期
    public boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

	public String getTOKEN_HEADER() {
		return TOKEN_HEADER;
	}

	public void setTOKEN_HEADER(String tOKEN_HEADER) {
		TOKEN_HEADER = tOKEN_HEADER;
	}

	public String getTOKEN_PREFIX() {
		return TOKEN_PREFIX;
	}

	public void setTOKEN_PREFIX(String tOKEN_PREFIX) {
		TOKEN_PREFIX = tOKEN_PREFIX;
	}

	public String getSECRET() {
		return SECRET;
	}

	public void setSECRET(String sECRET) {
		SECRET = sECRET;
	}
    

}
