package com.authguard.authguard_app_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.authguard.authguard_app_service.model.entity.AppEntity;

import java.util.List;

import com.authguard.authguard_app_service.dtos.AppResponse;


public interface AppRepository extends  JpaRepository<AppEntity, UUID> {
    
    @Query("SELECT new com.authguard.authguard_app_service.dtos.AppResponse(app.appName,app.client_id,app.userId) from AppEntity app where app.userId=:userId")
    public List<AppResponse> findAppListByUserId(@Param("userId") UUID userId);

}
