package io.github.eduardoconceicao90.user_service_api.mapper;

import io.github.eduardoconceicao90.user_service_api.entity.User;
import models.responses.UserResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {

    UserResponse fromEntity(final User entity);

}
