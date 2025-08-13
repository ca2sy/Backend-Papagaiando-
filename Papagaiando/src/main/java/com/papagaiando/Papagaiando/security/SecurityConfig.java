package com.papagaiando.Papagaiando.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // desabilita CSRF
            .authorizeHttpRequests((authz) -> authz.anyRequest().permitAll()); // permite todas as requisições
        return http.build();
    }
}
