package io.github.eduardoconceicao90.auth_service_api.repository;

import io.github.eduardoconceicao90.auth_service_api.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

}