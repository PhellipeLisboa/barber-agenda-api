package com.phellipe.barber_agenda_api.infra.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll() //hasHole("ADMIN")?
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll() //hasHole("ADMIN")?

                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()


//                        .requestMatchers(HttpMethod.GET, "/appointments").hasAnyRole("ADMIN", "OWNER", "PROFESSIONAL", "USER")
//                        .requestMatchers(HttpMethod.POST, "/appointments").hasAnyRole("ADMIN", "OWNER", "PROFESSIONAL", "USER")
//
//                        .requestMatchers(HttpMethod.GET, "/appointments/*").hasAnyRole("ADMIN", "OWNER", "PROFESSIONAL", "USER")
//                        .requestMatchers(HttpMethod.DELETE, "/appointments/*").hasAnyRole("ADMIN", "OWNER", "PROFESSIONAL", "USER")
//                        .requestMatchers(HttpMethod.PATCH, "/appointments/*").hasAnyRole("ADMIN", "OWNER", "PROFESSIONAL", "USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            SecurityErrorResponse.write(
                                    request,
                                    response,
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    "Invalid or missing authentication token."
                            );
                        })
                        .accessDeniedHandler((request, response, permissionException) -> {
                            SecurityErrorResponse.write(
                                    request,
                                    response,
                                    HttpServletResponse.SC_FORBIDDEN,
                                    "You don't have permission to access this resource."
                            );
                        })
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
