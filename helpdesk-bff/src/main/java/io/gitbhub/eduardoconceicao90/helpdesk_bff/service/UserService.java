package io.gitbhub.eduardoconceicao90.helpdesk_bff.service;

import io.gitbhub.eduardoconceicao90.helpdesk_bff.client.UserFeignClient;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    public UserResponse findById(final String id) {
        return userFeignClient.findById(id).getBody();
    }

    public void save(CreateUserRequest createUserRequest) {
        userFeignClient.save(createUserRequest);
    }

    public List<UserResponse> findAll() {
        return userFeignClient.findAll().getBody();
    }

    public UserResponse update(final String id, UpdateUserRequest updateUserRequest) {
        return userFeignClient.update(id, updateUserRequest).getBody();
    }

}
