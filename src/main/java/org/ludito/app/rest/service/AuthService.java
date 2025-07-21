package org.ludito.app.rest.service;

import org.ludito.app.rest.payload.req.auth.ReqLogin;
import org.ludito.app.rest.payload.req.auth.ReqLoginVerify;
import org.ludito.app.rest.payload.req.auth.ReqRegister;
import org.ludito.app.rest.payload.res.auth.ResLogin;
import org.ludito.app.rest.payload.res.auth.ResLoginVerify;
import org.ludito.app.rest.payload.res.auth.ResLogout;
import org.ludito.app.rest.payload.res.auth.ResRegister;
import org.ludito.app.rest.payload.res.user.ResUser;

public interface AuthService {

    ResLogin loginInit(ReqLogin req);

    ResLoginVerify loginVerify(ReqLoginVerify req);

    ResLogout logout();
}
