package io.gitbhub.eduardoconceicao90.helpdesk_bff.service;

import io.gitbhub.eduardoconceicao90.helpdesk_bff.client.UserFeignClient;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Cacheable(value = "users", key = "#id")
    public UserResponse findById(final String id) {
        return userFeignClient.findById(id).getBody();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void save(CreateUserRequest createUserRequest) {
        userFeignClient.save(createUserRequest);
    }

    @Cacheable(value = "users")
    public List<UserResponse> findAll() {
        return userFeignClient.findAll().getBody();
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserResponse update(final String id, UpdateUserRequest updateUserRequest) {
        return userFeignClient.update(id, updateUserRequest).getBody();
    }

}
