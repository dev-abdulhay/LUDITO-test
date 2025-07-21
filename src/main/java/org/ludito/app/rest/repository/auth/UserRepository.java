package org.ludito.app.rest.repository.auth;

import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.rest.entity.auth.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}