package com.greenvn.starlightelectronicsstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.greenvn.starlightelectronicsstore.service.EmployeeDetailsServiceImp;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Bean
    public UserDetailsService userDetailsService() {
        return new EmployeeDetailsServiceImp();
    };
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
        // permitall không cần kiểm tra
        .antMatchers("/", "/contact", "/about","/css/**","/images/**","/api/**").permitAll()
        // .hasrole phaỉ có quyền ADMIN mới được phép truy cập và đăng nhập
        
//      .antMatchers("/users/**").access("hasRole('ADMIN')")
				/*
				 * .antMatchers("/admin").hasRole("ADMIN")
				 * .antMatchers("/admin").hasRole("EMPLOYEE")
				 * .antMatchers("/admin").hasRole("MANAGEMENT")
				 */
        .antMatchers("/admin/**").hasAnyRole("ADMIN","MANAGEMENT","EMPLOYEE")
//        .antMatchers("/users/**").hasRole("Admin")
//        .antMatchers("/admin/**").hasRole("Admin")
//        .anyRequest().authenticated()//Những cái URL còn lại đều phải đăng nhập mới được xài
        .and()
        .formLogin()
        .loginPage("/login").permitAll()
        .defaultSuccessUrl("/admin", true)
        .failureUrl("/login?error")
        .and()
        .logout().permitAll()
        .logoutSuccessUrl("/login")
        .and().csrf()
        .disable();
    }
}
