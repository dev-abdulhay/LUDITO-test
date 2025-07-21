package org.ludito.app.rest.payload.req.transfer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ludito.app.rest.enums.transaction.OperationType;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqTransferFee {


    @NotNull
    private UUID debitAccountId; // yuboruvchi hisob

    private UUID creditAccountId;  // qabul qiluvchi hisobId

    private String creditAccount;  // qabul qiluvchi hisob

    @NotNull
    private OperationType operationType;

    @NotNull
    @Min(1)
    private Long amount;

    private String description;

}
