package com.bootx.config;

import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.interceptor.CorsInterceptor;
import com.bootx.interceptor.OptLogInterceptor;
import com.bootx.security.CurrentUserHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author black
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }
    @Bean
    public OptLogInterceptor optLogInterceptor() {
        return new OptLogInterceptor();
    }

    @Bean
    public CurrentUserHandlerInterceptor currentMemberHandlerInterceptor() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Member.class);
        return currentUserHandlerInterceptor;
    }

    @Bean
    public CurrentUserHandlerInterceptor currentAdminHandlerInterceptor() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Admin.class);
        return currentUserHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(currentAdminHandlerInterceptor())
                .addPathPatterns("/api/admin/**").excludePathPatterns("/api/admin/login");
        registry.addInterceptor(currentAdminHandlerInterceptor())
                .addPathPatterns("/api/member/**").excludePathPatterns("/api/member/login");
        registry.addInterceptor(optLogInterceptor())
                .addPathPatterns("/api/**");
    }
}
