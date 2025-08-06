package com.authguard.authguard_app_service.services;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.authguard.authguard_app_service.Context.UserContext;
import com.authguard.authguard_app_service.dtos.AppRequest;
import com.authguard.authguard_app_service.dtos.AppResponse;
import com.authguard.authguard_app_service.exception.ResourceException;
import com.authguard.authguard_app_service.model.entity.AppEntity;
import com.authguard.authguard_app_service.repository.AppRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final ModelMapper modelMapper;

    public AppResponse createApp(AppRequest appRequest) {
        UUID userId = UserContext.getUserId();
        AppEntity app = AppEntity.builder().appName(appRequest.getAppName()).userId(userId)
                .client_secret(UUID.randomUUID().toString()).build();
        app = appRepository.save(app);
        return AppResponse.builder().client_Id(app.getClient_id()).userId(userId)
                .appName(app.getAppName()).build();
    }

    @Cacheable(cacheNames = "appById", key = "#client_Id")
    public AppResponse getAppByClient_Id(UUID client_Id) throws ResourceException {
        AppEntity data = appRepository.findById(client_Id)
                .orElseThrow(() -> new ResourceException("App not found for id : " + client_Id));
        return modelMapper.map(data, AppResponse.class);
    }

    @Cacheable(cacheNames = "appListByUser", keyGenerator = "userContextKeyGenerator")
    public List<AppResponse> getAppList() {
        UUID userId = UserContext.getUserId();
        return appRepository.findAppListByUserId(userId);
    }
}
