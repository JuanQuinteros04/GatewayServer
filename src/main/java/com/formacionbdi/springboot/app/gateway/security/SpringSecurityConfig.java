package com.formacionbdi.springboot.app.gateway.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/users/users").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/users/users/username/{username}", "/api/students/students").hasAnyRole("ADMIN", "USER")
                .pathMatchers( "/api/students/**", "/api/usuarios/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }

}