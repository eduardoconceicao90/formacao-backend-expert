package io.gitbhub.eduardoconceicao90.helpdesk_bff.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader(AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        if (header.startsWith("Bearer ")) {
            try {
                UsernamePasswordAuthenticationToken auth = getAuthentication(request);
                if (auth != null) SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
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

}
