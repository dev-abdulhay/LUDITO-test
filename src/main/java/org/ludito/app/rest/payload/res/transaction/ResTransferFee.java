package org.ludito.app.rest.payload.res.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResTransferFee {

    private Long commissionAmount;

    private Long creditAmount;

    private Long debitAmount;

}
