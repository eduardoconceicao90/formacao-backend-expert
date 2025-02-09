package io.github.eduardoconceicao90.auth_service_api.security;

import io.github.eduardoconceicao90.auth_service_api.security.dto.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(final UserDetailsDTO userDetails) {
        return Jwts.builder()
                .claim("id", userDetails.getId())
                .claim("name", userDetails.getName())
                .claim("authorities", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

}
