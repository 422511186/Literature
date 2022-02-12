package com.hzy.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import com.hzy.security.CustomAuthenticationFilter;
import com.hzy.security.myUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/9/30 18:39
 * @Description:
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder myPasswordEncoder;

//    @Autowired
    private myUserDetailsService myCustomUserService;

//    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private modeshapeServiceImpl modeshapeService;

    public SecurityConfig(myUserDetailsService myCustomUserService, ObjectMapper objectMapper) {
        this.myPasswordEncoder = new BCryptPasswordEncoder();
        this.myCustomUserService = myCustomUserService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(myCustomUserService);
        authenticationProvider.setPasswordEncoder(myPasswordEncoder);
        return authenticationProvider;
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        //登录成功，返回json
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                Map<String, Object> map = new HashMap<>();
                PrintWriter out = null;

                map.put("code", 200);
                map.put("msg", "登录成功");
//            map.put("data", authentication);
                out = response.getWriter();
                assert out != null;
                out.write(objectMapper.writeValueAsString(map));
                out.flush();
                out.close();
            }
        });

        //登录失败，返回原因（Json）
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map<String, Object> map = new HashMap<>();
                map.put("code", 401);
                if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                    map.put("msg", "用户名或密码错误");
                } else if (ex instanceof DisabledException) {
                    map.put("msg", "账户被禁用");
                } else {
                    map.put("msg", "登录失败!");
                }

                out.write(objectMapper.writeValueAsString(map));
                out.flush();
                out.close();
            }
        });
        //重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                // 放行所有OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .and().httpBasic()
                //未登录时，进行json格式的提示
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
//                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        PrintWriter out = response.getWriter();
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", 403);
                        map.put("msg", "未登录");
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/Api/User/register", "/Api/test/**", "/Api/User/loginTest","/file/**").permitAll()
//                .antMatchers("/docs.html", "/swagger-ui/index.html").hasAnyAuthority("admin")//文档接口仅限管理员权限可见
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() //必须授权才能范围
                .and()
                .formLogin() //使用自带的登录
                .loginProcessingUrl("/Api/USer/login")
                .permitAll()
                .and()
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
//                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        PrintWriter out = response.getWriter();
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", 403);
                        map.put("msg", "权限不足");
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout()
                .logoutUrl("/Api/User/logout")
                //退出成功，返回json
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", 200);
                        map.put("msg", "退出成功");
                        if (authentication != null)
                            map.put("data", authentication.getPrincipal());
                        SecurityContextHolder.clearContext();
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .cors().and()
                .csrf().disable()
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    /**
     * 描述: 静态资源放行，这里的放行，是不走 Spring Security 过滤器链
     **/
    @Override
    public void configure(WebSecurity web) {
        // 可以直接访问的静态数据
        web.ignoring().antMatchers("/file/**");

    }

}