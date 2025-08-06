package com.authguard.authguard_app_service.utils;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.authguard.authguard_app_service.Context.UserContext;

@Component("userContextKeyGenerator")
public class UserContextKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return "user-" + UserContext.getUserId(); // threadlocal logic
    }

    // @Override
    // public Object generate(Object target, Method method, Object... params) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'generate'");
    // }
}
