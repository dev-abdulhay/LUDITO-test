package org.ludito.app.rest.payload.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReqUUID {
    @NotNull
    private UUID id;
}
