package io.gitbhub.eduardoconceicao90.helpdesk_bff.service;

import io.gitbhub.eduardoconceicao90.helpdesk_bff.client.AuthFeignClient;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthFeignClient authFeignClient;

    public AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception {
        return authFeignClient.authenticate(request).getBody();
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        return authFeignClient.refreshToken(request).getBody();
    }

}
