package org.example.ecomercestore.config;

import org.example.ecomercestore.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/api/products/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/categories/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/orders/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST,"/orders/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT,"/orders/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE,"/orders/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/orderItems/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST,"/orderItems/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT,"/orderItems/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE,"/orderItems/**")
                        .hasRole("ADMIN")


                        .anyRequest().authenticated()

                )
                .httpBasic(httpBasic->{});

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}