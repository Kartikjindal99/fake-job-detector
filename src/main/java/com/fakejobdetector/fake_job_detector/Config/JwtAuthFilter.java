package com.fakejobdetector.fake_job_detector.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final jwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //header se token nikalo
        String authHeader=request.getHeader("Authorization");
        // Debug ke liye temporarily add karo
        System.out.println("Header: " + authHeader);
        if(authHeader==null||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //"bearer" hata ke sirf token
        String token=authHeader.substring(7);

        //token valid
        if(!jwtUtil.isTokenValid(token)){
            filterChain.doFilter(request,response);
            return;
        }

        //Token se email aur role nikalo
        String email=jwtUtil.extractEmail(token);
        String role=jwtUtil.extractRole(token);

        //now tell spring security that the user is trusted
        UsernamePasswordAuthenticationToken authentication=
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+role))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }
}
