package org.ludito.app.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseURI;
import org.ludito.app.config.doc.DocController;
import org.ludito.app.rest.payload.req.history.ReqHistorySearch;
import org.ludito.app.rest.payload.res.history.ResHistorySearch;
import org.ludito.app.rest.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@DocController(name = "Transaction Module", description = "Transaction Controller")
@RequestMapping(BaseURI.API1 + BaseURI.HISTORY)
public class HistoryController {

    private final HistoryService service;


    @PostMapping(BaseURI.SEARCH)
    public ResponseEntity<ResHistorySearch> searchHistory(@RequestBody @Valid ReqHistorySearch req) {
        ResHistorySearch res = service.searchHistory(req);
        return ResponseEntity.ok(res);
    }


}
