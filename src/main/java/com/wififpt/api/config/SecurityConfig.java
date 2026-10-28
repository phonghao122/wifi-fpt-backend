
package com.wififpt.api.config;

import com.wififpt.api.security.JwtAuthenticationFilter;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtFilter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .httpBasic(basic -> basic.disable())

                .formLogin(form -> form.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        )
                        )
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) ->
                                        response.sendError(
                                                HttpServletResponse.SC_FORBIDDEN
                                        )
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Cho phép xử lý ERROR dispatch
                        .dispatcherTypeMatchers(
                                DispatcherType.ERROR
                        ).permitAll()

                        // API công khai: danh sách gói cước
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/packages"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/content/*").permitAll()

                        // API công khai: đăng nhập Admin
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login"
                        ).permitAll()

                        // API công khai: khách đăng ký WiFi
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/leads"
                        ).permitAll()

                        // Các API quản trị yêu cầu ROLE_ADMIN
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // Các API khác yêu cầu xác thực
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
