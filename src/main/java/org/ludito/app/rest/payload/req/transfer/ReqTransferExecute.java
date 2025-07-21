package org.ludito.app.rest.payload.req.transfer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqTransferExecute {

    @NotNull
    private UUID transferId;

}
