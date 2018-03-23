package com.twitter.TwitterEduApp.security;

import com.twitter.TwitterEduApp.social.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@Order(0)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*
       @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(new MyUserDetailsService());
    }

     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile")

                .and()
                .logout().logoutSuccessUrl("/login")

                .and()
                .authorizeRequests()
                .antMatchers("/webjars/**", "/login", "/signin/**", "/signup", "/h2-console/**").permitAll()
                .anyRequest().authenticated();

                http.csrf().disable();
                http.headers().frameOptions().disable();

    }
}
