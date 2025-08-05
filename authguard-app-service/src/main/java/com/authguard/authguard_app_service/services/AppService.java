package com.authguard.authguard_app_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.authguard.authguard_app_service.Context.UserContext;
import com.authguard.authguard_app_service.dtos.AppRequest;
import com.authguard.authguard_app_service.dtos.AppResponse;
import com.authguard.authguard_app_service.model.entity.AppEntity;
import com.authguard.authguard_app_service.repository.AppRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    
    public AppResponse creatApp(AppRequest appRequest) {
        UUID userId = UserContext.getUserId();
        AppEntity app = AppEntity.builder().appName(appRequest.getAppName()).userId(userId)
                .client_secret(UUID.randomUUID().toString()).build();
        app = appRepository.save(app);
        return AppResponse.builder().client_Id(app.getClient_id().toString()).userId(userId.toString())
                .appName(app.getAppName()).build();
    }
}
