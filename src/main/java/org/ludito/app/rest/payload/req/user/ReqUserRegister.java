package org.ludito.app.rest.payload.req.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserRegister {

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String phone;


}
