package io.gitbhub.eduardoconceicao90.helpdesk_bff.controller.impl;

import io.gitbhub.eduardoconceicao90.helpdesk_bff.controller.AuthController;
import io.gitbhub.eduardoconceicao90.helpdesk_bff.service.AuthService;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(authService.authenticate(request));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok().body(authService.refreshToken(request));
    }

}
