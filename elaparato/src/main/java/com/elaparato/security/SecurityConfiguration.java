package com.elaparato.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final JwtAuthenticationConverter jwtAuthenticationConverter;


    public static final String ADMINISTRADOR = "administrador-cliente";

    public static final String REPOSITOR = "repositor-cliente";

    public static final String VENDEDOR = "vendedor-cliente";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()


                .requestMatchers(HttpMethod.POST, "/productos/create", "/productos/**").hasAnyRole(REPOSITOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/productos/getall", "/productos/**").hasAnyRole(REPOSITOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/productos/**", "/productos/**").hasAnyRole(REPOSITOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.PUT, "/productos/edit/**", "/productos/**").hasAnyRole(REPOSITOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAnyRole(REPOSITOR, ADMINISTRADOR)


                .requestMatchers(HttpMethod.POST, "/ventas/create", "/ventas/**").hasAnyRole(VENDEDOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/ventas/getall", "/ventas/**").hasAnyRole(VENDEDOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/ventas/**", "/ventas/**").hasAnyRole(VENDEDOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.PUT, "/ventas/edit/**", "/ventas/**").hasAnyRole(VENDEDOR, ADMINISTRADOR)
                .requestMatchers(HttpMethod.DELETE, "/ventas/**").hasAnyRole(VENDEDOR, ADMINISTRADOR)


                .requestMatchers(HttpMethod.GET, "/users/all","/users/**").hasAnyRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/users/username/**","/users/**").hasAnyRole(ADMINISTRADOR)


                .anyRequest().authenticated();

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}