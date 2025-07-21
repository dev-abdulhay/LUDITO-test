package org.ludito.app.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseURI;
import org.ludito.app.config.doc.DocController;
import org.ludito.app.rest.payload.req.auth.ReqLogin;
import org.ludito.app.rest.payload.req.auth.ReqLoginVerify;
import org.ludito.app.rest.payload.req.user.ReqUserRegister;
import org.ludito.app.rest.payload.res.auth.ResLogin;
import org.ludito.app.rest.payload.res.auth.ResLoginVerify;
import org.ludito.app.rest.payload.res.auth.ResLogout;
import org.ludito.app.rest.payload.res.user.ResUser;
import org.ludito.app.rest.service.AuthService;
import org.ludito.app.rest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@DocController(name = "Auth Module", description = "Auth Controller")
@RequestMapping(BaseURI.API1 + BaseURI.AUTH)
public class AuthController {

    private final AuthService service;
    private final UserService userService;


    @PostMapping(BaseURI.REGISTER)
    public ResponseEntity<ResUser> register(@RequestBody @Valid ReqUserRegister req) {
        ResUser res = userService.register(req);
        return ResponseEntity.ok(res);
    }


    @PostMapping(BaseURI.LOGIN)
    public ResponseEntity<ResLogin> loginInit(@RequestBody @Valid ReqLogin req) {
        ResLogin res = service.loginInit(req);
        return ResponseEntity.ok(res);
    }


    @PostMapping(BaseURI.LOGIN + BaseURI.VERIFY)
    public ResponseEntity<ResLoginVerify> loginVerify(@RequestBody @Valid ReqLoginVerify req) {
        ResLoginVerify res = service.loginVerify(req);
        return ResponseEntity.ok(res);
    }


    @PostMapping(BaseURI.LOGOUT)
    public ResponseEntity<ResLogout> logout() {
        ResLogout res = service.logout();
        return ResponseEntity.ok(res);
    }

}
