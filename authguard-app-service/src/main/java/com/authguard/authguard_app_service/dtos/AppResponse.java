package com.authguard.authguard_app_service.dtos;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppResponse implements Serializable{
    private String appName;
    private UUID client_Id;
    private UUID userId;
    private String client_secret;
    public AppResponse(String appName, UUID client_Id, UUID user_Id) {
        this.appName = appName;
        this.client_Id = client_Id;
        this.userId = userId;
    }
}
