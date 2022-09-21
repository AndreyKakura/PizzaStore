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

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/users/login", "/users/registration", "/error").permitAll()
//                .anyRequest().hasAnyRole("USER", "ADMIN")
//                .and()
//                .formLogin().loginPage("/users/login")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/hello", true)
//                .failureUrl("/users/login?error")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/users/login");
//
//        return http.build();
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/users/login", "/users/registration", "/error").permitAll()
//                .anyRequest().hasAnyRole("USER", "ADMIN")
//                .and()
//                .formLogin().loginPage("/users/login")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/hello", true)
//                .failureUrl("/users/login?error")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/users/login");
//    }

//    @Bean
//    public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
//        EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
//                EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
//        contextSourceFactoryBean.setPort(0);
//        return contextSourceFactoryBean;
//    }
//
//    @Bean
//    AuthenticationManager ldapAuthenticationManager(
//            BaseLdapPathContextSource contextSource) {
//        LdapBindAuthenticationManagerFactory factory =
//                new LdapBindAuthenticationManagerFactory(contextSource);
//        factory.setUserDnPatterns("uid={0},ou=people");
//        factory.setUserDetailsContextMapper(new PersonContextMapper());
//        return factory.createAuthenticationManager();
//    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();


        return http
                .authorizeRequests()
//                .anyRequest().permitAll()
                .antMatchers( "/users", "/users/{id}/edit").hasRole("ADMIN")
                .antMatchers("/pizza/new", "/pizza/{id}", "/pizza/{id}/edit").hasRole("ADMIN")
                .antMatchers("/cart/index").hasAnyRole("ADMIN", "USER")
                .antMatchers( "/users/login", "/users/registration", "/pizza", "/images/{id}", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/users/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/pizza", true)
                .failureUrl("/users/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/users/login")
                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authenticationManager(authenticationManager)
                .build();
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(getPasswordEncoder());
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
