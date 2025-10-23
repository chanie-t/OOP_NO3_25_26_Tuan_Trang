package com.phenikaa.hospital_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.Authentication; // Giữ lại import này
import org.springframework.security.core.GrantedAuthority; // Giữ lại import này

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/error/**").permitAll()
                // Phân quyền chỉ còn PATIENT và DOCTOR
                .requestMatchers("/patient/**").hasRole("PATIENT")
                .requestMatchers("/doctor/**").hasRole("DOCTOR")
                // XÓA DÒNG .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/records/create-for-appointment/**").hasRole("DOCTOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // === TRẢ LẠI SUCCESS HANDLER CŨ ===
                .successHandler((request, response, authentication) -> {
                    // Chỉ kiểm tra có phải DOCTOR hay không
                    boolean isDoctor = authentication.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DOCTOR"));
                    if (isDoctor) {
                        response.sendRedirect("/doctor/dashboard");
                    } else { // Mặc định là PATIENT
                        response.sendRedirect("/patient/dashboard");
                    }
                })
                .permitAll()
            )
            // (Phần logout, rememberMe, exceptionHandling giữ nguyên)
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
                .key("a-very-secret-key-for-remember-me")
                .tokenValiditySeconds(86400 * 7)
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error/403")
            );

        return http.build();
    }
}