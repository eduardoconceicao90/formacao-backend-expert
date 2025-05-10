package io.github.eduardoconceicao90.user_service_api.controller.impl;

import io.github.eduardoconceicao90.user_service_api.controller.UserController;
import io.github.eduardoconceicao90.user_service_api.mapper.UserMapper;
import io.github.eduardoconceicao90.user_service_api.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserResponse> findById(final String id) {
        return ResponseEntity.ok().body(userMapper.fromEntity(userService.findById(id)));
    }

    @Override
    public ResponseEntity<Void> save(CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Override
    public ResponseEntity<UserResponse> update(final String id, UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok().body(userService.update(id, updateUserRequest));
    }
}
