package datn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/gio-hang/**",
                                "/upload",             // Cho phép upload ảnh
                                "/images/**",          // Cho phép truy cập ảnh tĩnh
                                "/sanpham/**",         // Nếu có dùng redirect hoặc form
                                "/",                   // Trang chủ nếu cần
                                "/**" ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
