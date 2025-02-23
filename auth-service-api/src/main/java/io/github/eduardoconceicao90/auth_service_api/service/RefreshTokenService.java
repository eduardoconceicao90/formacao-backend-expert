package io.github.eduardoconceicao90.auth_service_api.service;

import io.github.eduardoconceicao90.auth_service_api.entity.RefreshToken;
import io.github.eduardoconceicao90.auth_service_api.repository.RefreshTokenRepository;
import io.github.eduardoconceicao90.auth_service_api.security.JWTUtil;
import io.github.eduardoconceicao90.auth_service_api.security.dto.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpired;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    @Value("${jwt.expiration-refresh-hours}")
    private Long expiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;

    public RefreshToken save(final String username){
        return refreshTokenRepository.save(
                RefreshToken.builder()
                    .id(UUID.randomUUID().toString())
                    .username(username)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusHours(expiration))
                .build());
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId){
        final var refreshToken = refreshTokenRepository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found. Id: " + refreshTokenId));

        if(refreshToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RefreshTokenExpired("Refresh token expired. Id: " + refreshTokenId);
        }

        return new RefreshTokenResponse(
                jwtUtil.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()
        )));
    }
}
