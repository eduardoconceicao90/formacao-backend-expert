package io.gitbhub.eduardoconceicao90.helpdesk_bff.service;

import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    public void save(CreateUserRequest createUserRequest) {

    }

    public List<UserResponse> findAll() {
        return null;
    }

    public UserResponse update(final String id, UpdateUserRequest updateUserRequest) {
        return null;
    }

    public UserResponse findById(final String id) {
        return null;
    }

}
