package org.ludito.app.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseURI;
import org.ludito.app.config.doc.DocController;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.payload.req.ReqUUID;
import org.ludito.app.rest.payload.req.wallet.ReqAddWallet;
import org.ludito.app.rest.payload.res.wallet.ResWallet;
import org.ludito.app.rest.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@DocController(name = "Wallet Module", description = "Wallet Controller")
@RequestMapping(BaseURI.API1 + BaseURI.WALLET)
public class WalletController {

    private final WalletService userService;

    @GetMapping
    public ResponseEntity<List<ResWallet>> getAll() {
        List<Wallet> res = userService.getAllByUser(GlobalVar.getUserId());
        return ResponseEntity.ok(ResWallet.of(res));
    }


    @PostMapping(BaseURI.ONE)
    public ResponseEntity<ResWallet> getOne(@RequestBody @Valid ReqUUID req) {
        Wallet res = userService.getOne(req.getId());
        return ResponseEntity.ok(ResWallet.of(res));

    }

    @PostMapping(BaseURI.ADD)
    public ResponseEntity<ResWallet> getOne(@RequestBody @Valid ReqAddWallet req) {
        Wallet res = userService.add(req);
        return ResponseEntity.ok(ResWallet.of(res));

    }

}
