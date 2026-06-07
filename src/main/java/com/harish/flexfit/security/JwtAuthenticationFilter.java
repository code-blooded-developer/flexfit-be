package com.harish.flexfit.security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    private String accessTokenCookieName = "ACCESS_TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
       String token = null;

       // First, try to get token from Authorization header
       final String authHeader = request.getHeader("Authorization");
       if(authHeader != null && authHeader.startsWith("Bearer ")) {
           token = authHeader.substring(7);
       }

       // If no header token, try to get from cookie
       if(token == null) {
           Cookie[] cookies = request.getCookies();
           if(cookies != null) {
               for(Cookie cookie : cookies) {
                   if(accessTokenCookieName.equals(cookie.getName())) {
                       token = cookie.getValue();
                       break;
                   }
               }
           }
       }

       // If no token found, continue without authentication
       if(token == null) {
           filterChain.doFilter(request, response);
           return;
       }
       
       try {
            if(jwtUtil.validateToken(token) && jwtUtil.isAccessToken(token)) {
                UUID userId = jwtUtil.extractUserId(token);

                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userId, null, List.of());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
       } catch (Exception e) {
            // Invalid token - continue without authentication
       }

        filterChain.doFilter(request, response);
    }

}
