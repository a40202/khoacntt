package com.tlu.khoacntt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.tlu.khoacntt.security.FlexiblePasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new FlexiblePasswordEncoder();
    }

    // Xác thực: AdminUserDetailsService đọc user từ bảng admins (HTTP Basic).

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // 👈 THÊM DÒNG NÀY
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Cho phép preflight request
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 👈 THÊM

                // Public GET
                .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()

                // Swagger
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // Admin
                .requestMatchers("/api/admin/**", "/api/v1/admin/**").hasRole("ADMIN")

                // CRUD
                .requestMatchers(HttpMethod.POST, "/api/**", "/api/v1/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/**", "/api/v1/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**", "/api/v1/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    
}