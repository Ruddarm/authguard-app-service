package com.authguard.authguard_app_service.Context;

import java.util.UUID;

public class UserContext {
    private static ThreadLocal<UUID> userContext = new ThreadLocal<>();

    public static UUID getUserId() {
        return userContext.get();
    }

    public static void setUserId(UUID UserId) {
        userContext.set(UserId);
    }

    public static void clear() {
        userContext.remove();
    }
}
