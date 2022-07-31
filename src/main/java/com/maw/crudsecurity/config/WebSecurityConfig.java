package com.maw.crudsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
            .authorizeRequests()
            .antMatchers("/*").permitAll()
            .antMatchers("/user/userPage/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/user/adminPage/**").hasAnyAuthority("ADMIN")
            .antMatchers("/book/deleteBook/**").hasAuthority("ADMIN")
            .antMatchers("/book/updateBook/**").hasAnyAuthority("ADMIN", "EDITOR")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .successHandler(customLoginSuccessHandler)
            // .defaultSuccessUrl("/book/bookList")
            .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .rememberMe()
            // .tokenValiditySeconds(2592000) for remember me time limit
            .and()
            .exceptionHandling().accessDeniedPage("/403")
            ;
        return http.build();
    }

}
