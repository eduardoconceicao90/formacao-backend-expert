package io.github.eduardoconceicao90.user_service_api.service;

import io.github.eduardoconceicao90.user_service_api.entity.User;
import io.github.eduardoconceicao90.user_service_api.mapper.UserMapper;
import io.github.eduardoconceicao90.user_service_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id){
        return userMapper.fromEntity(userRepository.findById(id).orElse(null));
    }
}
