package com.authguard.authguard_app_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authguard.authguard_app_service.model.entity.AppEntity;

public interface AppRepository extends  JpaRepository<AppEntity, UUID> {
    
}
