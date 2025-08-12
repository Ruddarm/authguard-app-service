package com.authguard.authguard_app_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 * DTO represent required for creation of app Request.
 * <p>
 * Currently contains one feild appName which is required to create instance of app.
 * </p>
 */
@Data
public class AppRequest {
    /*
     * appName is intial configuration requried to creat apps.
     * 
     */
    @NotNull
    @NotBlank
    private String appName;
}
