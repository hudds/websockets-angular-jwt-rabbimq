package dev.hudsonprojects.backend.security.configuration;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import static dev.hudsonprojects.backend.integration.coursesapi.course.webhook.CourseWebhookSubscriberService.WEBHOOK_LISTENER_COURSE_URI;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
	public static final List<String> ALLOWED_ORIGINS = List.of("*");
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfiguration(AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(configurer -> configurer.disable());
        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/auth", "/user/registration").permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/events/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/" + WEBHOOK_LISTENER_COURSE_URI).permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/auth/refresh-token").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/user/auth/refresh-token").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(sessionConf -> sessionConf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(customize -> customize.authenticationEntryPoint(securityException401EntryPoint()))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    public AuthenticationEntryPoint securityException401EntryPoint(){
        return (request, response, authException) -> {
            response.setHeader("WWW-Authenticate", "Bearer");
            response.setStatus(401);
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(ALLOWED_ORIGINS);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
        		.addSecurityItem(new SecurityRequirement()
        				.addList(securitySchemeName))
        		.components(new Components()
        				.addSecuritySchemes(securitySchemeName, new SecurityScheme()
        						.name(securitySchemeName)
        						.type(SecurityScheme.Type.HTTP)
        						.scheme("Bearer")
        						.bearerFormat("JWT")));
    }
    

}
