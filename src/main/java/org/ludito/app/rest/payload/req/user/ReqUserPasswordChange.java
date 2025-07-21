package org.ludito.app.rest.payload.req.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserPasswordChange {

    @NotBlank
    private String oldPassword;


    @NotBlank
    private String newPassword;


}
