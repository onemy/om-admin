package com.onemysoft.oma.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.onemysoft.oma.portal.filter.JWTAuthenticationFilter;
import com.onemysoft.oma.portal.filter.JWTAuthorizationFilter;
import com.onemysoft.oma.portal.handler.JWTAccessDeniedHandler;
import com.onemysoft.oma.portal.handler.JWTAuthenticationEntryPoint;

/**
 * @author onemysoft
 * 
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	/**
	 * 认证失败处理类注入
	 */
	@Autowired
	private JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint;
    /**
     * 权限不足处理类注入
     */
    @Autowired
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;	
    /**
     * 手动注入鉴权过滤器（自动注入静态文件请求都会经过此类）
     * @return
     */
	@Bean
	public FilterRegistrationBean customApiAuthenticationFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(customApiAuthenticationFilter());
		registration.setEnabled(false);
		return registration;
	}
	/**
	 * 同上
	 * @return
	 */
	@Bean
	public JWTAuthorizationFilter customApiAuthenticationFilter() {
		return new JWTAuthorizationFilter();
	}
    /**
     * 注入Bean，setAuthenticationManager（无法自动注入authenticationManager）
     * @return
     * @throws Exception
     */
	@Bean
	public JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
		JWTAuthenticationFilter jwtFilter=new JWTAuthenticationFilter();
		jwtFilter.setAuthenticationManager(this.authenticationManager());
		jwtFilter.setFilterProcessesUrl("/login");
		return jwtFilter;
	}

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	//放行静态资源
        web.ignoring().antMatchers(
        						"/",
        						"/dist/**",
        						"/plugins/**",
        						"/pages/**",
        						"/**.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        	http
        		//增加跨域过滤器
        		.cors()
        		.and()
        			//关闭跨域
        			.csrf().disable()
	                .authorizeRequests()
	                //api路径都需认证
	                .antMatchers("/api/**").authenticated()
	                //其它请求不需认证
	                .anyRequest().permitAll()
                .and()
	                // 基于token，所以不需要session
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                	//错误处理
	                .exceptionHandling().authenticationEntryPoint(jWTAuthenticationEntryPoint)
	                //无权处理
	                .accessDeniedHandler(jwtAccessDeniedHandler) 
	                //防止iframe 造成跨域
                .and()
	                .headers()
	                .frameOptions()
	                .disable()
	            .and()
	                //注销
	                .logout()
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login.html")
	                .deleteCookies("token")
                ;
        	//添加认证过滤器（登录认证成功后增加JWT Token）注：也可以自定义controller实现登录认证
        	http.addFilterBefore(getJWTAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        	//添加鉴权过滤器（每个/api/**请求都需检查JWT Token）
        	http.addFilterBefore(customApiAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 跨域配置（也可以自定义过滤器，或直接在controller加注解等方式）
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    /**
     * 为注入authenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
