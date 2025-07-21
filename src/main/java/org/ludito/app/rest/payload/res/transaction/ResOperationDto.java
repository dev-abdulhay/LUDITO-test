package org.ludito.app.rest.payload.res.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.enums.transaction.OperationStatus;
import org.ludito.app.rest.enums.transaction.OperationType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link org.ludito.app.rest.entity.transaction.Operation}
 */
@AllArgsConstructor
@Getter
public class ResOperationDto implements Serializable {

    private final UUID uuid;

    private final Integer order;

    private final OperationStatus status;

    private final OperationType type;

    private final UUID creditWalletUuid;

    private final UUID debitWalletUuid;

    private final Long creditAmount;

    private final Long debitAmount;

    private final Long commissionAmount;

    private final UUID userUuid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(type = "string", example = "2025-07-15 17:39:16", description = "Time in yyyy-MM-dd HH:mm:ss format")
    private final LocalDateTime executeDate;

    private final String description;


    public static List<ResOperationDto> of(List<Operation> operations) {
        return operations.stream()
                .map(ResOperationDto::of)
                .toList();
    }

    public static ResOperationDto of(Operation operations) {
        return new ResOperationDto(operations.getUuid(), operations.getOrder(), operations.getStatus(), operations.getType(), operations.getCreditWallet().getUuid(), operations.getDebitWallet().getUuid(), operations.getCreditAmount(), operations.getDebitAmount(), operations.getCommissionAmount(), operations.getUser().getUuid(), operations.getExecuteDate(), operations.getDescription());
    }
}