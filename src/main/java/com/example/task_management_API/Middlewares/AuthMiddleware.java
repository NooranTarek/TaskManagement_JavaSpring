package com.example.task_management_API.Middlewares;

import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.UserService;
import com.example.task_management_API.utilities.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class AuthMiddleware extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserService userService;

    public AuthMiddleware(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            ApiResponse<String> apiResponse = new ApiResponse<>("You must log in first", HttpStatus.UNAUTHORIZED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
            return;
        }
        token = token.replace("Bearer ", "");
        try {
            boolean tokenExpired = jwtUtil.validateToken(token, jwtUtil.extractUserName(token));
        } catch (Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Token EXPIRED", HttpStatus.BAD_REQUEST);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
            return;
        }
        Integer userId = jwtUtil.extractUserId(token);
        if (userId != null) {
            User user = userService.findUserById(userId);
            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                if (authentication != null) {
                    User userr = (User) authentication.getPrincipal();
                }
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
