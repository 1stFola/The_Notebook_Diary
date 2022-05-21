package com.technophiles.thenotebook.security;

import com.technophiles.thenotebook.security.jwt.ExceptionHandlerFilter;
import com.technophiles.thenotebook.security.jwt.JWTAuthenticationFilter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UnAuthorizedEntryPoint unAuthorizedEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize.antMatchers("/**/users/createUser/**"
                                        , "/**/**/**/auth/login").permitAll().anyRequest()
                                .authenticated().and()
                                .exceptionHandling().authenticationEntryPoint(unAuthorizedEntryPoint)
                                .and().sessionManagement().sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                );
                        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
                        http.addFilterBefore(exceptionHandlerFilterBean(), JWTAuthenticationFilter.class);


                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                });
    }

    @Bean
    public Filter authenticationTokenFilterBean(){
        return new JWTAuthenticationFilter();
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilterBean(){
        return new ExceptionHandlerFilter();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}





/*
* - Auth Controller --> CONTROLLER
- Global(Diary Exception Handler) --> CONTROLLER

- Account Creation Request --> DTOs
- Login Request --> DTOs
*
* USER SERVICE IMPLEMENTATION EXTENDS 2 PARENTS
* - public class UserServiceImpl implements UserService, UserDetailsService {

- API Error  --> EXCEPTION
- UserInExistenceException --> EXCEPTION
- UserNotFoundException --> EXCEPTION

https://www.javatpoint.com/spring-security-features
- ** --> SECURITY
Configuration Points
- Link to the controller endpoints
*
* */