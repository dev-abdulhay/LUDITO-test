package org.ludito.app.rest.service;

import jakarta.validation.constraints.NotBlank;
import org.ludito.app.config.base.BaseService;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.payload.req.user.ReqUserPasswordChange;
import org.ludito.app.rest.payload.req.user.ReqUserRegister;
import org.ludito.app.rest.payload.req.user.ReqUserUpdate;
import org.ludito.app.rest.payload.res.user.ResUser;
import org.ludito.app.rest.payload.res.user.ResUserPasswordChange;

public interface UserService extends BaseService<User> {
    ResUser register(ReqUserRegister req);

    ResUser update(ReqUserUpdate req);

    ResUserPasswordChange passwordChange(ReqUserPasswordChange req);


    ResUser getMe();

    User getByUsername(@NotBlank String username);

    String generateOtpCode(User user);

}
