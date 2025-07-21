package org.ludito.app.rest.payload.req.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLogin {

    @NotBlank
    private String username;


    @NotBlank
    private String password;

}
