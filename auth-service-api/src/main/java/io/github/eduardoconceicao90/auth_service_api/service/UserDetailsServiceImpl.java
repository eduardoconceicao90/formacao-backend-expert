package io.github.eduardoconceicao90.auth_service_api.service;

import io.github.eduardoconceicao90.auth_service_api.repository.UserRepository;
import io.github.eduardoconceicao90.auth_service_api.security.dto.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return UserDetailsDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getProfiles().stream()
                        .map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
