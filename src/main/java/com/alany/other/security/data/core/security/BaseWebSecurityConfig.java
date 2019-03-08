package com.alany.other.security.data.core.security;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RmsUserDetailsService userDetailsService;

    @Value("${remember.key:remember_me}")
    protected String rememberMeKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**", "/openapi/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .rememberMe().key(rememberMeKey).rememberMeServices(rememberMeServices())
                .and()
                .logout().permitAll();

        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/404", "/401", "/403", "/error");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/openapi/**", "/api/**");
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(rememberMeKey, userDetailsService);
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

//    @Bean
//    public RememberMeServices rememberMeServices() {
//        InMemoryTokenRepositoryImpl rememberMeTokenRepository = new InMemoryTokenRepositoryImpl();
//        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(rememberMeKey, userDetailsService, rememberMeTokenRepository);
//        rememberMeServices.setAlwaysRemember(true);
//        return rememberMeServices;
//    }

    protected boolean isAjax(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
    }

    protected boolean isApi(HttpServletRequest request) {
        String[] paths = StringUtils.split(request.getServletPath(), "/");
        if (paths != null && paths.length > 0) {
            return paths[0].equalsIgnoreCase("api")
                    || paths[0].equalsIgnoreCase("openapi")
                    || paths[0].equalsIgnoreCase("webapi");
        }
        return false;
    }

    private class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication)
                throws ServletException, IOException {

            clearAuthenticationAttributes(request);
            if (!isAjax(request) && !isApi(request)) {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }

    private class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request,
                                    HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
            if (!isAjax(request) && !isApi(request)) {
                super.onLogoutSuccess(request, response, authentication);
            }
        }
    }

    private class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            response.setCharacterEncoding("utf-8");
            if (isApi(request)) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "请登录");
                response.getWriter().println(json);
            } else if (isAjax(request)) {
                response.getWriter().println("请登录");
            } else {
                response.sendRedirect("/login");
            }
        }
    }

    private class MyAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            response.setCharacterEncoding("utf-8");
            if (isApi(request)) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "您无权访问");
                response.getWriter().println(json);
            } else if (isAjax(request)) {
                response.getWriter().println("您无权访问");
            } else {
                response.sendRedirect("/403");
            }
        }
    }

}
