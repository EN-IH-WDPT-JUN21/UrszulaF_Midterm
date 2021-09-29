package com.ironhack.midterm.Security;

import com.ironhack.midterm.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true) //override the protected methods to provide custom implementation
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("123456")).roles("ADMIN", "USER")
                .and()
                .withUser("user").password(passwordEncoder.encode("123456")).roles("USER");

    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/third-party/receive-money/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/third-party/send-money/**").hasRole("ACCOUNT_HOLDER")
                .antMatchers(HttpMethod.GET, "/my-accounts/**").hasRole("ACCOUNT_HOLDER")
                .antMatchers(HttpMethod.POST, "/transfer").hasAnyRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .anyRequest().permitAll();
        http.csrf().disable();

    }

}
