package com.bootx.config;

import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.interceptor.CorsInterceptor;
import com.bootx.interceptor.MemberOptLogInterceptor;
import com.bootx.interceptor.OptLogInterceptor;
import com.bootx.security.CurrentUserHandlerInterceptor;
import com.bootx.security.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
    public MemberOptLogInterceptor memberOptLogInterceptor() {
        return new MemberOptLogInterceptor();
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
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
        registry.addInterceptor(currentMemberHandlerInterceptor())
                .addPathPatterns("/api/member/**","/api/soft/**").excludePathPatterns("/api/member/login");
        registry.addInterceptor(optLogInterceptor())
                .addPathPatterns("/api/admin/**");
        registry.addInterceptor(memberOptLogInterceptor())
                .addPathPatterns("/api/member/**","/api/soft/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver());
    }
}
