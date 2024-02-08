package com.vinovibes.vinoapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuration class for security.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Profile("local")
public class LocalSecurityConfig {

    private final UserAuthProvider userAuthProvider;

    /**
     * Bean for security filter chain. Configures security for the application.
     * @param http http security
     * @return security filter chain
     * @throws Exception exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(new JWTAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(requests ->
                requests
                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/login",
                        "/api/register/**",
                        "/api/forgot-password",
                        "/api/reset-password"
                    )
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api-docs/**", "/swagger-ui/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            );
        return http.build();
    }
}
