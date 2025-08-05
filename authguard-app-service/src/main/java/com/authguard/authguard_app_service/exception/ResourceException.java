package com.authguard.authguard_app_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResourceException extends Exception {
    String msg;

    public ResourceException(String msg) {
        super(msg);
    }
}
