package com.senifit.was.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;
import com.senifit.was.service.auth.exception.SigninAuthenticationFailureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

@Configuration
@Slf4j
public class SecurityConfig {

    @Value("${spring.application.maximum-sessions}")
    private int maximumSessions;


    @Bean
    public PasswordEncoder passwordEncoder(@Value("${spring.datasource.pbkdf2_pepper}") String pepper) {
        return new Pbkdf2PasswordEncoder(
            pepper,
            64,
            185000,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http
            .userDetailsService(userDetailsService)
            .formLogin(form -> form
                .loginProcessingUrl("/auth/signin")
                .successHandler(senifitAuthenticationSuccessHandler())
                .failureHandler(senifitAuthenticationFailureHandler())
                .usernameParameter("id")
            )
            .logout(logout -> logout
                .logoutUrl("/auth/signout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(senifitSignOutSuccessHandler())
            )
            .sessionManagement(session -> session
                .maximumSessions(maximumSessions)
                .maxSessionsPreventsLogin(true)
            )
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }

    @Bean
    public AuthenticationSuccessHandler senifitAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            log.info("signIn success");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.success()));
        };
    }

    @Bean
    public LogoutSuccessHandler senifitSignOutSuccessHandler() {
        return (request, response, authentication) -> {
            log.info("signOut success");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.success()));
        };
    }

    @Bean
    public AuthenticationFailureHandler senifitAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            log.info("signIn fail");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();

            ApiException e;
            if (exception instanceof BadCredentialsException)
                e = new SigninAuthenticationFailureException();
            else if (exception instanceof SessionAuthenticationException)
                e = new SigninAuthenticationFailureException();
            else
                e = new ApiException(ApiExceptionDetails.SIGNIN_ELSE);

            ApiResponse<Void> apiResponse = ApiResponse.failure(e);
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        };
    }


}
