package io.github.eduardoconceicao90.auth_service_api.controller.impl;

import io.github.eduardoconceicao90.auth_service_api.controller.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest request) {
        return ResponseEntity.ok().body(AuthenticateResponse.builder()
                        .type("Bearer")
                        .token("token")
                .build());
    }
}
