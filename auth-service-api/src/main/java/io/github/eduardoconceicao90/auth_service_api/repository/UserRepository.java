package io.github.eduardoconceicao90.auth_service_api.repository;

import io.github.eduardoconceicao90.auth_service_api.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(final String email);

}
