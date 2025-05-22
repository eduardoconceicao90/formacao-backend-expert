package io.gitbhub.eduardoconceicao90.helpdesk_bff.client;

import jakarta.validation.Valid;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service-api", path = "/api/users")
public interface UserFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable(name = "id") final String id);

    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody CreateUserRequest createUserRequest);

    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(
            @PathVariable(name = "id") final String id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest
    );

}
