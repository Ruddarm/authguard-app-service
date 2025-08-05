package com.authguard.authguard_app_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppRequest {
    @NotNull
    @NotBlank
    private String appName;
}
