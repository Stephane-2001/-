package com.leeyen.crowd.mvc.config;

import com.leeyen.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 当前类为配置类
@Configuration
// 开启springsecurity
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置prePostEnabled = true。保证@PreAuthority、@PostAuthority、@PreFilter、@PostFilter生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                // 临时使用内存的登录测试
                /*.inMemoryAuthentication()
                .withUser("lyx")
                .password("123123")
                .roles("ADMIN")*/

                // 正是功能中使用基于数据库的功能
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                // 对请求进行授权
                .authorizeRequests()
                // 针对登陆页面进行设置
                .antMatchers("/admin/to/login/page.html")
                // 允许所有许可
                .permitAll()

                // 对静态资源进行放行
			    .antMatchers("/bootstrap/**")
                .permitAll()
                .antMatchers("/crowd/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/fonts/**")
                .permitAll()
                .antMatchers("/img/**")
                .permitAll()
                .antMatchers("/jquery/**")
                .permitAll()
                .antMatchers("/layer/**")
                .permitAll()
                .antMatchers("/script/**")
                .permitAll()
                .antMatchers("/ztree/**")
                .permitAll()
                // 针对分页显示admin信息分页
                .antMatchers("/admin/get/page.html")
                //.hasRole("经理")
                .access("hasRole('经理') OR hasAuthority('user:get')")

                // 其他任意请求
                .anyRequest()
                // 认证后访问
                .authenticated()

                // 异常处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse,
                                       AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("exception",
                                new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp")
                                .forward(httpServletRequest, httpServletResponse);
                    }
                })

                .and()
                // 防跨站请求伪造功能
                .csrf()
                // 禁用csrf
                .disable()
                // 开启表单登录功能
                .formLogin()
                // 登陆页面
                .loginPage("/admin/to/login/page.html")
                // 处理登录请求的地址
                .loginProcessingUrl("/security/do/login.html")
                // 指定登录成功后前往的地址
                .defaultSuccessUrl("/admin/to/main/page.html")
                // 账号的请求参数名称
                .usernameParameter("loginAcct")
                // 密码的请求参数名称
                .passwordParameter("userPswd")

                .and()
                // 开启退出登录
                .logout()
                // 指定退出登录的地址
                .logoutUrl("/security/do/logout.html")
                // 成功退出后进入的页面
                .logoutSuccessUrl("/admin/to/login/page.html")
        ;


    }
}
