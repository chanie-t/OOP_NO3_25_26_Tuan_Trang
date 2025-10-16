package com.phenikaa.hospital_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean này dùng để mã hóa mật khẩu
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép tất cả mọi người truy cập vào các tài nguyên tĩnh (css, js, images...)
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // truy cập vào trang login và trang chủ
                .requestMatchers("/", "/login").permitAll()
                // Tất cả các request khác đều cần phải xác thực (đăng nhập)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Chỉ định trang login của chúng ta
                .loginPage("/login")
                // URL mà form login sẽ gửi dữ liệu tới để Spring Security xử lý
                .loginProcessingUrl("/login")
                // URL sẽ chuyển hướng đến sau khi đăng nhập thành công
                .defaultSuccessUrl("/patients", true)
                // truy cập vào trang login
                .permitAll()
            )
            .logout(logout -> logout
                // Cho phép mọi người truy cập vào URL logout
                .permitAll()
            );
        return http.build();
    }
}