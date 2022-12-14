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
        //????????????UserDetailsService????????????
        authenticationProvider.setUserDetailsService(myCustomUserService);
        authenticationProvider.setPasswordEncoder(myPasswordEncoder);
        return authenticationProvider;
    }

    //??????????????????UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        //?????????????????????json
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                Map<String, Object> map = new HashMap<>();
                PrintWriter out = null;

                map.put("code", 200);
                map.put("msg", "????????????");
//            map.put("data", authentication);
                out = response.getWriter();
                assert out != null;
                out.write(objectMapper.writeValueAsString(map));
                out.flush();
                out.close();
            }
        });

        //??????????????????????????????Json???
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map<String, Object> map = new HashMap<>();
                map.put("code", 401);
                if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                    map.put("msg", "????????????????????????");
                } else if (ex instanceof DisabledException) {
                    map.put("msg", "???????????????");
                } else {
                    map.put("msg", "????????????!");
                }

                out.write(objectMapper.writeValueAsString(map));
                out.flush();
                out.close();
            }
        });
        //??????WebSecurityConfigurerAdapter?????????AuthenticationManager????????????????????????AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                // ????????????OPTIONS??????
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .and().httpBasic()
                //?????????????????????json???????????????
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
//                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        PrintWriter out = response.getWriter();
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", 403);
                        map.put("msg", "?????????");
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .authorizeRequests()
                //????????????????????????Preflight??????
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/Api/User/register", "/Api/test/**", "/Api/User/loginTest","/file/**").permitAll()
//                .antMatchers("/docs.html", "/swagger-ui/index.html").hasAnyAuthority("admin")//???????????????????????????????????????
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() //????????????????????????
                .and()
                .formLogin() //?????????????????????
                .loginProcessingUrl("/Api/USer/login")
                .permitAll()
                .and()
                .exceptionHandling()
                //?????????????????????json
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
//                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        PrintWriter out = response.getWriter();
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", 403);
                        map.put("msg", "????????????");
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout()
                .logoutUrl("/Api/User/logout")
                //?????????????????????json
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", 200);
                        map.put("msg", "????????????");
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
     * ??????: ???????????????????????????????????????????????? Spring Security ????????????
     **/
    @Override
    public void configure(WebSecurity web) {
        // ?????????????????????????????????
        web.ignoring().antMatchers("/file/**");

    }

}