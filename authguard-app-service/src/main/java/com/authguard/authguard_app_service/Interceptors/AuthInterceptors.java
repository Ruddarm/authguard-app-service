package com.authguard.authguard_app_service.Interceptors;

import java.util.UUID;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.authguard.authguard_app_service.Context.UserContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor for extracting and validating the user ID from incoming HTTP
 * requests.
 *
 * <p>
 * This interceptor checks for the presence of the {@code X-USER-Id} HTTP header
 * before the request reaches the controller layer. If found, the user ID is
 * stored
 * in {@link UserContext} (ThreadLocal) for downstream use in the current
 * request.
 * </p>
 *
 * <p>
 * If the header is missing or invalid, the request is rejected with an
 * exception.
 * </p>
 *
 * <p>
 * The stored user ID is cleared after the request is processed to avoid
 * ThreadLocal memory leaks.
 * </p>
 */

@Slf4j
@Component
public class AuthInterceptors implements HandlerInterceptor {
    /**
     * Extracts {@code X-USER-Id} from the request header and stores it in the
     * UserContext.
     *
     * @return {@code true} if request should proceed, otherwise throws an exception
     * @throws Exception if the {@code X-USER-Id} header is missing or invalid
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("pre handling : {} " + request.getRequestURI());
        String userId = request.getHeader("X-USER-Id");
        if (userId == null) {
            throw new Exception("Invlid user Id");
        }
        UserContext.setUserId(UUID.fromString(userId));
        return true;
    }

    /**
     * Clears the UserContext after request processing is complete.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        UserContext.clear();
        log.info("post handling : {} " + request.getRequestURI());

    }
}
