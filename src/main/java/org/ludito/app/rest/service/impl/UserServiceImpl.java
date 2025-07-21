package org.ludito.app.rest.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseRepository;

import java.security.SecureRandom;

import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.enums.UserStatus;
import org.ludito.app.rest.payload.req.user.ReqUserPasswordChange;
import org.ludito.app.rest.payload.req.user.ReqUserPasswordChangeVerify;
import org.ludito.app.rest.payload.req.user.ReqUserRegister;
import org.ludito.app.rest.payload.req.user.ReqUserUpdate;
import org.ludito.app.rest.payload.res.user.ResUser;
import org.ludito.app.rest.payload.res.user.ResUserPasswordChange;
import org.ludito.app.rest.payload.res.user.ResUserPasswordChangeVerify;
import org.ludito.app.rest.repository.auth.UserRepository;
import org.ludito.app.rest.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseRepository<User> getRepository() {
        return repository;
    }

    @Override
    public ResUser register(ReqUserRegister req) {
        if (repository.existsByUsername(req.getUsername())) {
            throw new CustomException("Username already exists");
        }

        User user = User.builder()
                .username(req.getUsername())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .status(UserStatus.ACTIVE)
                .build();

        save(user);

        return ResUser.of(user);
    }

    @Override
    public ResUser update(ReqUserUpdate req) {
        User user = getById(GlobalVar.getUserId());

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPhone(req.getPhone());

        save(user);

        return ResUser.of(user);
    }

    @Override
    public ResUserPasswordChange passwordChange(ReqUserPasswordChange req) {
        User user = getById(GlobalVar.getUserId());

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            throw new CustomException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        save(user);

        return ResUserPasswordChange.builder()
                .message("Password changed successfully")
                .build();
    }

    @Override
    public ResUser getMe() {
        return ResUser.of(GlobalVar.getAuthUser());
    }

    @Override
    @Transactional
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new CustomException("User not found with username: " + username));
    }

    @Override
    public String generateOtpCode(User user) {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(90000) + 10000;
        return String.valueOf(code);
    }
}
