package com.fakejobdetector.fake_job_detector.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // BCryptPasswordEncoder bean — poori app mein yahi use hoga
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       http.csrf(csrf->csrf.disable())
               //Session nahi banao
               .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               //Routes ke rules
               .authorizeHttpRequests(auth->auth.

                       requestMatchers(
                               "/swagger-ui/**",
                               "/swagger-ui.html",
                               "/v3/api-docs/**",
                               "/v3/api-docs",
                               "/webjars/**"
                       ).permitAll()
                       .requestMatchers("/api/auth/**").permitAll().
                       requestMatchers("/api/reports/leaderboard").permitAll()
                       .requestMatchers("/api/admin/**").hasRole("ADMIN")
                       .anyRequest().authenticated()
               )
               //jwtauthfilter
               .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();

    }
}