package com.helloblog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService detailsService() {
        return new ForDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/error**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager());

        http
        .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login**","/css**","/images**","/reguser**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index",true)
                .successForwardUrl("/index")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/error")
                .permitAll();
        //cookie名remember-me，记住一天
        //.rememberMe().alwaysRemember(true).tokenValiditySeconds(1 * 60 * 60 * 24);
        //session设置，时效性需在boot设置
        //.sessionManagement().maximumSessions(1);


        http
                .logout()
                .logoutSuccessUrl("/");
    }
}
