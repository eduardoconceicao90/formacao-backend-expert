package io.gitbhub.eduardoconceicao90.helpdesk_bff.client;

import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "localhost:8765/user-service-api",
        path = "/api/users"
)
public interface UserFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable(name = "id") final String id);

    @PostMapping
    ResponseEntity<Void> save(@RequestBody CreateUserRequest createUserRequest);

    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(@PathVariable(name = "id") final String id, @RequestBody UpdateUserRequest updateUserRequest);

}
