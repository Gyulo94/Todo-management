package com.gyulo94.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gyulo94.todo.security.JwtAuthenticationEntryPoint;
import com.gyulo94.todo.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize) -> {
            // authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
            // authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
            // authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
            // authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN",
            // "USER");
            // authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN",
            // "USER");
            // authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
            authorize.requestMatchers("/api/auth/**").permitAll();
            authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            authorize.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails gyulo94 = User.builder()
    // .username("gyulo94")
    // .password(passwordEncoder().encode("q1w2e3"))
    // .roles("USER")
    // .build();

    // UserDetails admin = User.builder()
    // .username("admin")
    // .password(passwordEncoder().encode("admin"))
    // .roles("ADMIN")
    // .build();

    // return new InMemoryUserDetailsManager(gyulo94, admin);
    // }
}
