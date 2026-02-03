package com.cscb869.medical_record.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity(prePostEnabled = true) // TODO: Enable when implementing security
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: Uncomment when implementing full security
//    private final UserService userService;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TEMPORARY: Disable security for testing
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            );

        return http.build();

        // TODO: Uncomment when implementing full security
//        http
//            .authorizeHttpRequests(authorize -> authorize
//                // Public endpoints
//                .requestMatchers("/", "/index", "/login", "/error", "/css/**", "/js/**").permitAll()
//                // API endpoints - role-based access will be handled by @PreAuthorize annotations
//                .requestMatchers("/api/**").authenticated()
//                // All other requests require authentication
//                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                .loginPage("/login")
//                .defaultSuccessUrl("/index", true)
//                .permitAll()
//            )
//            .logout(logout -> logout
//                .logoutSuccessUrl("/login?logout")
//                .permitAll()
//            )
//            .exceptionHandling(exception -> exception
//                .accessDeniedPage("/errors/unauthorized-errors")
//            )
//            .authenticationProvider(authenticationProvider());
//
//        return http.build();
    }
}
