package com.leeyen.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // 添加一个view-controller
        registry.addViewController("/auth/member/to/reg/page.html").setViewName("member-reg");
        registry.addViewController("/auth/member/to/login/page.html").setViewName("member-login");
        registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
    }
}
