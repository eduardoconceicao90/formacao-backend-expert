package io.github.eduardoconceicao90.auth_service_api.security.jwt;

import io.github.eduardoconceicao90.auth_service_api.security.JWTUtil;
import io.github.eduardoconceicao90.auth_service_api.security.dto.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            return buildAuthenticationResponse((UserDetailsDTO) authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            log.error("Error on authenticate user: {}", request.email());
            throw new BadCredentialsException("Email or password invalid");
        }
    }

    protected AuthenticateResponse buildAuthenticationResponse(UserDetailsDTO userDetails) {
        log.info("Successfully authenticated user: {}", userDetails.getUsername());
        final var token = jwtUtil.generateToken(userDetails);
        return AuthenticateResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}
