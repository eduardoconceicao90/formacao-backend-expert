package io.github.eduardoconceicao90.user_service_api.service;

import io.github.eduardoconceicao90.user_service_api.entity.User;
import io.github.eduardoconceicao90.user_service_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(final String id){
        return userRepository.findById(id).orElse(null);
    }
}
