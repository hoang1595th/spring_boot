package hoang.learn.spring_boot.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SwaggerSecurityConfig {

    // Bean này CHỈ được khởi tạo khi profile "dev" hoạt động
    @Bean
    @Profile("dev")
    @Order(1) // Ưu tiên chạy trước SecurityFilterChain mặc định
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
