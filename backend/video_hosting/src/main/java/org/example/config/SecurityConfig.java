package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.model.RoleEnum;
import org.example.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailServiceImpl userService;

    private final String[] WHITE_LIST_URLS = {
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/api/auth/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("*"));
                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                            config.setAllowedHeaders(List.of("*"));
                            config.setExposedHeaders(List.of("Authorization"));
                            return config;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**", "/api/transfer/**")
                        .hasAuthority(RoleEnum.ADMIN.name())
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/video/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/user/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/search/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userService)
                .logout(logout -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .addLogoutHandler(logoutHandler())
                        .logoutSuccessHandler(logoutSuccessHandler())
                );
        return http.build();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write("Logout successful");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
