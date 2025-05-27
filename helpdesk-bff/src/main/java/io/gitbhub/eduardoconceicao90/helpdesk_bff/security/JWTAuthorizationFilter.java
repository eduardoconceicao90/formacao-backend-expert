package io.gitbhub.eduardoconceicao90.helpdesk_bff.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.exceptions.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final String[] publicRoutes;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, String[] publicRoutes) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.publicRoutes = publicRoutes;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(isPublicRoute(request.getRequestURI())){
            chain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")) {
            handleException(request.getRequestURI(), "Authorization header is missing", response);
            return;
        }

        if (header.startsWith("Bearer ")) {
            try {
                UsernamePasswordAuthenticationToken auth = getAuthentication(request);
                if (auth != null) SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                handleException(request.getRequestURI(), e.getMessage(), response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTHORIZATION).substring(7);

        String username = jwtUtil.getUsername(token);
        Claims claims = jwtUtil.getClaims(token);
        List<GrantedAuthority> authorities = jwtUtil.getAuthorities(claims);

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
    }

    private boolean isPublicRoute(String uri) {
        for (String route : publicRoutes) {
            if (uri.startsWith(route)) return true;
        }
        return false;
    }

    private void handleException(String requestURI, String message, HttpServletResponse response) throws IOException {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(message)
                .path(requestURI)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(error);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }

}
