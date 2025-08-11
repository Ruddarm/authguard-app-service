package com.authguard.authguard_app_service.Context;

import java.util.UUID;

/**
 * Thread-local storage for the currently authenticated user's ID.
 *
 * <p>This utility allows storing and retrieving the user ID associated
 * with the current request thread without passing it explicitly between methods.</p>
 *
 * <p>Usage:</p>
 * <ul>
 *   <li>Set the user ID at the beginning of request processing 
 *       (e.g., in an interceptor or filter).</li>
 *   <li>Retrieve the user ID anywhere in the request-handling thread.</li>
 *   <li>Clear the stored value at the end of request processing to 
 *       prevent memory leaks.</li>
 * </ul>
 *
 * <p><strong>Important:</strong> Always call {@link #clear()} after 
 * the request completes (typically in a post-handle or after-completion phase).</p>
 */
public class UserContext {

    /** ThreadLocal variable to store user ID for the current request thread */
    private static final ThreadLocal<UUID> userContext = new ThreadLocal<>();

    /**
     * Retrieves the current request's user ID.
     *
     * @return the user ID, or {@code null} if not set
     */
    public static UUID getUserId() {
        return userContext.get();
    }

    /**
     * Stores the given user ID in the current thread's context.
     *
     * @param userId the user ID to store
     */
    public static void setUserId(UUID userId) {
        userContext.set(userId);
    }

    /**
     * Clears the stored user ID from the current thread's context.
     * This should always be called after request processing to 
     * prevent memory leaks.
     */
    public static void clear() {
        userContext.remove();
    }
}
