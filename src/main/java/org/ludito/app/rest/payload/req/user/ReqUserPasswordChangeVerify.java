package org.ludito.app.rest.payload.req.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ReqUserPasswordChangeVerify {

    @NotNull
    private UUID identity;

    @NotBlank
    private String code;

}
