package org.ludito.app.rest.payload.req.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLoginVerify {

    @NotNull
    private UUID identity;


    @NotBlank
    private String code;

}
