package org.ludito.app.rest.payload.res.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ludito.app.rest.enums.UserStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.ludito.app.rest.entity.auth.User}
 */
@AllArgsConstructor
@Getter
public class ResUserDto implements Serializable {

    private final UUID uuid;

    private final String username;

    private final String firstName;

    private final String lastName;

    private final String phone;

    private final UserStatus status;
}