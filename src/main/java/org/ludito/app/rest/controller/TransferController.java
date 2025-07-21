package org.ludito.app.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseURI;
import org.ludito.app.config.doc.DocController;
import org.ludito.app.rest.payload.req.transfer.ReqTransferExecute;
import org.ludito.app.rest.payload.req.transfer.ReqTransferFee;
import org.ludito.app.rest.payload.req.transfer.ReqTransferInit;
import org.ludito.app.rest.payload.res.transaction.ResTransferExecute;
import org.ludito.app.rest.payload.res.transaction.ResTransferFee;
import org.ludito.app.rest.payload.res.transaction.ResTransferInit;
import org.ludito.app.rest.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@DocController(name = "Transaction Module", description = "Transaction Controller")
@RequestMapping(BaseURI.API1 + BaseURI.TRANSFER)
public class TransferController {

    private final TransferService service;


    @PostMapping(BaseURI.FEE)
    public ResponseEntity<ResTransferFee> feeTransfer(@RequestBody @Valid ReqTransferFee req) {
        ResTransferFee res = service.feeTransfer(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping(BaseURI.INIT)
    public ResponseEntity<ResTransferInit> initTransfer(@RequestBody @Valid ReqTransferInit req) {
        ResTransferInit res = service.initTransfer(req);
        return ResponseEntity.ok(res);
    }


    @PostMapping(BaseURI.EXECUTE)
    public ResponseEntity<ResTransferExecute> executeTransfer(@RequestBody @Valid ReqTransferExecute req) {
        ResTransferExecute res = service.executeTransfer(req);
        return ResponseEntity.ok(res);
    }


}
