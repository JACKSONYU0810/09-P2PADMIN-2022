package com.jackson.p2padmin2022.user.config;

import com.jackson.p2padmin2022.user.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: InterceptorConfiguration
 * Package: com.jackson.p2padmin2022.user.config
 * Description:
 *
 * @Date: 8/31/2022 6:23 PM
 * @Author: JacksonYu
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/admin/**");
    }
}
