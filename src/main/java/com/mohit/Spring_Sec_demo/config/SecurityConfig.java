package com.mohit.Spring_Sec_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

//        provider.setUserDetailsPasswordService((UserDetailsPasswordService) userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<HttpSecurity>() {
//
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> configurer) {
//                configurer.disable();
//
//            }
//        };
//        http.csrf(custCsrf);



        http
                .csrf(customizer -> customizer.disable())
             .authorizeHttpRequests(request -> request
                     .requestMatchers("/register")
                     .permitAll()
                     .anyRequest().authenticated())
        //http.formLogin(Customizer.withDefaults()); // we dont need of form login you make it stateless
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("mohit")
//                .password("0000")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User
//                .withDefaultPasswordEncoder()
//                .username("admin")
//                .password("0000")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);

}
