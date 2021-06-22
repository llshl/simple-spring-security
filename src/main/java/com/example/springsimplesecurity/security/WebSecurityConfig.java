package com.example.springsimplesecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/", "/home", "/create").permitAll()   //모든 접근이 허가된 요청
                    .antMatchers("admin/**").hasRole("ADMIN")           //admin/ 이하의 모든 요청에는 ADMIN 권한만 가능하다 라는 의미
                    .anyRequest().authenticated()   //이외의 것들은 인증이 필요하다는 뜻
                    .and()
                .formLogin()
                    .loginPage("/login")            //로그인페이지로 가는 요청은 이것이다
                    .permitAll()                    //이 페이지는 모두에게 접근 가능한 권한이다.
                    .and()
                .logout()
                    .logoutSuccessUrl("/home")      //로그아웃 성공시 여기로 이동
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
/*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

 */
}