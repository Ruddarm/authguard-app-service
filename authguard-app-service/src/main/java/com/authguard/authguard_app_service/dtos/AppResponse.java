package com.authguard.authguard_app_service.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppResponse {
    private String appName;
    private String client_Id;
    private String userId;
}
