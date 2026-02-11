package com.inf.cscb869_pharmacy.config;

import com.inf.cscb869_pharmacy.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
     //   prePostEnabled = true,
        securedEnabled = true
)
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "http://localhost:8080/realms/pharmacy-application-realm";
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakAuthorityConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests
                        (
                                authz -> authz
                                        .requestMatchers("/api/recipes/**").hasAuthority("doctor")
                                        .requestMatchers("/api/medicines/**").hasAuthority("seller")
                                        .anyRequest().authenticated()
                        )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtCustomizer -> jwtCustomizer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }
}
