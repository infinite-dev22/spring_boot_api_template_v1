package io.nomard.spring_boot_api_template_v1.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nomard.spring_boot_api_template_v1.entities.*;
import io.nomard.spring_boot_api_template_v1.filters.SpotyRequestFilter;
import io.nomard.spring_boot_api_template_v1.security.SpotyAuthEntryPoint;
import io.nomard.spring_boot_api_template_v1.services.auth.SpotyTokenService;
import io.nomard.spring_boot_api_template_v1.services.auth.SpotyUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Value("jwt.secret")
    private String secret;

    @Bean
    public Product product() {
        return new Product();
    }

    @Bean
    public User user() {
        return new User();
    }

    @Bean
    public UserProfile userProfile() {
        return new UserProfile();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return new SpotyAuthEntryPoint();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SpotyUserDetailsService();
    }

    @Bean
    public SpotyTokenService spotyTokenService() {
        return new SpotyTokenService(secret);
    }

    @Bean
    public SpotyRequestFilter spotyRequestFilter() {
        return new SpotyRequestFilter();
    }
}
