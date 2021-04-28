package com.onemysoft.oma.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.onemysoft.common.springmvc.intercept.JsonDataRequestBodyIntercept;

/**
 * @author zongshuo
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	

	@Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(new JsonDataRequestBodyIntercept());
        registration.addPathPatterns("/api/**");
//        registration.excludePathPatterns("/error","/static/**");
 
    }
	
//  @Bean
//  public BCryptPasswordEncoder bCryptPasswordEncoder() {
//      return new BCryptPasswordEncoder();
//  }
}
