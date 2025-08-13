package com.authguard.authguard_app_service.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

/**
 * Service layer for handling application-related operations.
 *
 * <p>
 * This class provides methods to:
 * <ul>
 * <li>Create new applications for the logged-in user</li>
 * <li>Retrieve an application by its client ID (with caching)</li>
 * <li>Fetch all applications for the current user (with caching)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Uses {@link UserContext} to determine the user making the request.
 * Uses Redis caching for performance optimization.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new application for the currently authenticated user.
     *
     * <p>
     * Generates a random client secret and assigns the current user's ID
     * from {@link UserContext}.
     * </p>
     *
     * @param appRequest DTO containing the application name
     * @return the created application's details
     */
    public AppResponse createApp(AppRequest appRequest) {
        UUID userId = UserContext.getUserId();

        AppEntity app = AppEntity.builder()
                .appName(appRequest.getAppName())
                .userId(userId)
                .client_secret(UUID.randomUUID().toString())
                .build();

        app = appRepository.save(app);

        return AppResponse.builder()
                .client_Id(app.getClient_id())
                .userId(userId)
                .appName(app.getAppName())
                .build();
    }

    /**
     * Retrieves an application by its client ID.
     * 
     * <p>
     * Result is cached in Redis under {@code appById}.
     * </p>
     *
     * @param client_Id the UUID of the application
     * @return application details as {@link AppResponse}
     * @throws ResourceException if no application is found with the given ID
     */
    @Cacheable(cacheNames = "appById", key = "#client_Id")
    public AppResponse getAppByClient_Id(UUID client_Id) throws ResourceException {
        AppEntity data = appRepository.findById(client_Id)
                .orElseThrow(() -> new ResourceException("App not found for id : " + client_Id));
        return modelMapper.map(data, AppResponse.class);
    }

    /**
     * Retrieves all applications for the current user.
     * 
     * <p>
     * Result is cached in Redis under {@code appListByUser}
     * using {@code userContextKeyGenerator}.
     * </p>
     *
     * @return list of {@link AppResponse} for the user
     */
    @Cacheable(cacheNames = "appListByUser", keyGenerator = "userContextKeyGenerator")
    public List<AppResponse> getAppList() {
        UUID userId = UserContext.getUserId();
        return appRepository.findByUserId(userId).stream()
                .map(appEntity -> modelMapper.map(appEntity, AppResponse.class))
                .collect(Collectors.toList());
    }
}
