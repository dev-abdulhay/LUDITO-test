package org.ludito.app.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseURI;
import org.ludito.app.config.doc.DocController;
import org.ludito.app.rest.payload.req.user.ReqUserPasswordChange;
import org.ludito.app.rest.payload.req.user.ReqUserUpdate;
import org.ludito.app.rest.payload.res.user.ResUser;
import org.ludito.app.rest.payload.res.user.ResUserPasswordChange;
import org.ludito.app.rest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@DocController(name = "User Module", description = "User Controller")
@RequestMapping(BaseURI.API1 + BaseURI.USER)
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResUser> getMe() {
        ResUser res = userService.getMe();
        return ResponseEntity.ok(res);
    }


    @PostMapping(BaseURI.UPDATE)
    public ResponseEntity<ResUser> update(@RequestBody @Valid ReqUserUpdate req) {
        ResUser res = userService.update(req);
        return ResponseEntity.ok(res);
    }


    @PostMapping(BaseURI.PASSWORD + BaseURI.CHANGE)
    public ResponseEntity<ResUserPasswordChange> passwordChange(@RequestBody @Valid ReqUserPasswordChange req) {
        ResUserPasswordChange res = userService.passwordChange(req);
        return ResponseEntity.ok(res);
    }


}
