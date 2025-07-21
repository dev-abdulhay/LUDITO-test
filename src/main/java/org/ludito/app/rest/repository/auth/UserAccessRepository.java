package org.ludito.app.rest.repository.auth;

import jakarta.validation.constraints.NotNull;
import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.rest.entity.auth.UserAccess;
import org.ludito.app.rest.enums.UserLoginStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccessRepository extends BaseRepository<UserAccess> {
    boolean existsByUser_Id(Long id);

    Optional<UserAccess> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);

    Optional<UserAccess> findByUuidAndStatus(@NotNull UUID identity, UserLoginStatus userLoginStatus);

    List<UserAccess> findAllByUser_IdAndStatus(
            @NotNull Long userId,
            @NotNull UserLoginStatus userLoginStatus
    );

    void deleteByUser_IdAndStatus(@NotNull Long userId, @NotNull UserLoginStatus userLoginStatus);

}