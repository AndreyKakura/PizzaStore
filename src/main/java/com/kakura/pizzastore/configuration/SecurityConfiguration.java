package com.kakura.pizzastore.configuration;

import com.kakura.pizzastore.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();


        return http
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/orders").hasAnyRole("ADMIN", "USER")
                .antMatchers("/pizza/new", "/pizza/{id}", "/pizza/{id}/edit").hasRole("ADMIN")
                .antMatchers("/cart/index").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users/profile").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users/editCurrent").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users/login", "/users/registration", "/pizza", "/images/{id}", "/error").permitAll()
                .and()
                .formLogin().loginPage("/users/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/pizza", true)
                .failureUrl("/users/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/users/login")
                .and()
                .authenticationManager(authenticationManager)
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
