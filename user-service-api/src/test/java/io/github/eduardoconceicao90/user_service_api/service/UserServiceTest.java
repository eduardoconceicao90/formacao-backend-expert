package io.github.eduardoconceicao90.user_service_api.service;

import io.github.eduardoconceicao90.user_service_api.entity.User;
import io.github.eduardoconceicao90.user_service_api.mapper.UserMapper;
import io.github.eduardoconceicao90.user_service_api.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static io.github.eduardoconceicao90.user_service_api.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(userMapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var userResponse = userService.findById("1");

        // Assertions
        assertNotNull(userResponse);
        assertEquals(UserResponse.class, userResponse.getClass());

        verify(userRepository).findById(anyString());
        verify(userMapper).fromEntity(any(User.class));
    }

    @Test
    void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Assertions
        try {
            userService.findById("1");
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
        }

        verify(userRepository).findById(anyString());
        verify(userMapper, never()).fromEntity(any(User.class));
    }

    @Test
    void whenCalFindAllThenReturnListOfUserResponse() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        when(userMapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var userResponseList = userService.findAll();

        // Assertions
        assertNotNull(userResponseList);
        assertFalse(userResponseList.isEmpty());
        assertEquals(2, userResponseList.size());
        assertEquals(UserResponse.class, userResponseList.get(0).getClass());

        verify(userRepository).findAll();
        verify(userMapper, times(2)).fromEntity(any(User.class));
    }

    @Test
    void whenCallSaveThenSuccess() {
        final var request = generateMock(CreateUserRequest.class);

        when(userMapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.save(request);

        verify(userMapper).fromRequest(request);
        verify(encoder).encode(request.password());
        verify(userRepository).save(any(User.class));
        verify(userRepository).findByEmail(request.email());
    }

    @Test
    void whenCallSaveWithInvalidEmailThenThrowDataIntegrityViolationException() {
        final var request = generateMock(CreateUserRequest.class);
        final var entity = generateMock(User.class);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        // Assertions
        try {
            userService.save(request);
        } catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Email [ "+ request.email() + " ] already exists", e.getMessage());
        }

        verify(userRepository).findByEmail(request.email());
        verify(userMapper, never()).fromRequest(request);
        verify(encoder, never()).encode(request.password());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenCallUpdateWithInvalidIdThenThrowResourceNotFoundException() {
        final var request = generateMock(UpdateUserRequest.class);

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Assertions
        try {
            userService.update("1", request);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
        }

        verify(userRepository).findById(anyString());
        verify(userMapper, never()).update(any(), any());
        verify(encoder, never()).encode(request.password());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenCallUpdateWithInvalidEmailThenThrowDataIntegrityViolationException() {
        final var request = generateMock(UpdateUserRequest.class);
        final var entity = generateMock(User.class);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(entity));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        // Assertions
        try {
            userService.update("1", request);
        } catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Email [ "+ request.email() + " ] already exists", e.getMessage());
        }

        verify(userRepository).findById(anyString());
        verify(userRepository).findByEmail(request.email());
        verify(userMapper, never()).update(any(), any());
        verify(encoder, never()).encode(request.password());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenCallUpdateWithValidParamsThenSuccess() {
        final var id = "1";
        final var request = generateMock(UpdateUserRequest.class);
        final var entity = generateMock(User.class).withId(id);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(entity));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));
        when(userMapper.update(any(), any())).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(entity);
        when(encoder.encode(anyString())).thenReturn(entity.getPassword());

        userService.update(id, request);

        verify(userRepository).findById(anyString());
        verify(userRepository).findByEmail(request.email());
        verify(userMapper).update(request, entity);
        verify(userRepository).save(any(User.class));
        verify(encoder).encode(request.password());
        verify(userMapper).fromEntity(any(User.class));
    }

}