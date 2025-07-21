package org.ludito.app.config.utils;

import org.apache.logging.log4j.ThreadContext;
import org.ludito.app.rest.entity.auth.User;

import java.util.UUID;

public class GlobalVar {

    private final static ThreadLocal<UUID> USER_UUID = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<Long> USER_ID = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<String> H_AUTHORIZATION = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<User> AUTH_USER = ThreadLocal.withInitial(() -> null);


    public static UUID getUserUUID() {
        return GlobalVar.USER_UUID.get();
    }


    public static Long getUserId() {
        return GlobalVar.USER_ID.get();
    }

    public static void setUserUuid(UUID userUuid) {
        GlobalVar.USER_UUID.set(userUuid);
    }

    public static void setUserId(Long userUuid) {
        GlobalVar.USER_ID.set(userUuid);
    }

    public static String getAuthorization() {
        return GlobalVar.H_AUTHORIZATION.get();
    }

    public static void setAuthorization(String authorization) {
        GlobalVar.H_AUTHORIZATION.set(authorization);
    }

    public static void setAuthUser(User authUser) {
        GlobalVar.AUTH_USER.set(authUser);
    }

    public static User getAuthUser() {
        return GlobalVar.AUTH_USER.get();
    }


    public static void clearContext() {
        GlobalVar.USER_UUID.remove();
        GlobalVar.H_AUTHORIZATION.remove();
        GlobalVar.AUTH_USER.remove();
        ThreadContext.clearAll();
    }


}
