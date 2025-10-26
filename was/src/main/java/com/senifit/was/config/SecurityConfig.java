package com.senifit.was.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.entity.Center;
import com.senifit.was.entity.LoginAccessStatus;
import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.security.CenterDetail;
import com.senifit.was.service.auth.LoginAccessService;
import com.senifit.was.service.auth.exception.SigninAuthenticationFailureException;
import com.senifit.was.util.IpAddressUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final LoginAccessService loginAccessService;
    private final CentersRepository centersRepository;

    @Value("${spring.application.maximum-sessions}")
    private int maximumSessions;

    @Value("${server.ssl.enabled:false}")
    private boolean sslEnabled;


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
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/health", "/actuator/health", "/actuator/prometheus", "/actuator/metrics/**", "/ui/home").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
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
            
            // 로그인 이력 저장
            try {
                CenterDetail centerDetail = (CenterDetail) authentication.getPrincipal();
                String ipAddress = IpAddressUtil.getClientIp(request);
                loginAccessService.saveLoginAccess(
                    centerDetail.getLoginId(),
                    centerDetail.getCenterName(),
                    ipAddress,
                    LoginAccessStatus.SUCCESS
                );
            } catch (Exception e) {
                log.error("Failed to log login access: {}", e.getMessage(), e);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.success()));
        };
    }

    // 시큐어 쿠키 설정
    @Bean
    public CookieHttpSessionIdResolver httpSessionIdResolver() {
        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        
        cookieSerializer.setCookieName("JSESSIONID");
        cookieSerializer.setUseSecureCookie(sslEnabled);  // HTTPS에서만 전송
        cookieSerializer.setUseHttpOnlyCookie(true);      // JavaScript 접근 차단
        cookieSerializer.setSameSite("None");           // CSRF 방지
        cookieSerializer.setCookieMaxAge(1800);          // 30분
        
        resolver.setCookieSerializer(cookieSerializer);
        log.info("Secure cookie configured - SSL enabled: {}", sslEnabled);
        
        return resolver;
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
            
            // 로그인 실패 이력 저장 (아이디가 존재하고 비밀번호가 틀린 경우에만)
            try {
                String loginId = request.getParameter("id");
                if (loginId != null && !loginId.isEmpty()) {
                    // 아이디가 DB에 존재하는지 확인
                    Optional<Center> centerOptional = centersRepository.findByLoginId(loginId);
                    if (centerOptional.isPresent()) {
                        Center center = centerOptional.get();
                        String ipAddress = IpAddressUtil.getClientIp(request);
                        loginAccessService.saveLoginAccess(
                            center.getLoginId(),
                            center.getName(),
                            ipAddress,
                            LoginAccessStatus.FAILED
                        );
                    }
                }
            } catch (Exception e) {
                log.error("Failed to log login access: {}", e.getMessage(), e);
            }
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
