package org.ludito.app.rest.payload.res.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.enums.UserStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUser {


    private String username;

    private String firstName;

    private String lastName;

    private String phone;

    private UserStatus status;


    public static ResUser of(User authUser) {
        return ResUser.builder()
                .username(authUser.getUsername())
                .firstName(authUser.getFirstName())
                .lastName(authUser.getLastName())
                .phone(authUser.getPhone())
                .status(authUser.getStatus())
                .build();
    }
}
