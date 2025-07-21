package org.ludito.app.rest.service;

import org.ludito.app.config.base.BaseService;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.payload.req.transfer.ReqTransferExecute;
import org.ludito.app.rest.payload.req.transfer.ReqTransferFee;
import org.ludito.app.rest.payload.req.transfer.ReqTransferInit;
import org.ludito.app.rest.payload.res.transaction.ResTransferExecute;
import org.ludito.app.rest.payload.res.transaction.ResTransferFee;
import org.ludito.app.rest.payload.res.transaction.ResTransferInit;

public interface TransferService {

    public ResTransferFee feeTransfer(ReqTransferFee req);

    public ResTransferInit initTransfer(ReqTransferInit req);

    public ResTransferExecute executeTransfer(ReqTransferExecute req);
}
