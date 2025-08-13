package com.authguard.authguard_app_service.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_app_service.dtos.AppResponse;
import com.authguard.authguard_app_service.exception.ResourceException;
import com.authguard.authguard_app_service.services.AppService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/service/apps")
@RequiredArgsConstructor
public class AppServiceController {

    private final AppService appService;

    @GetMapping("/app/{client_id}")
    public ResponseEntity<AppResponse> getApp(@PathVariable UUID client_id) throws ResourceException {
        return ResponseEntity.ok(appService.getAppByClient_Id(client_id));
    }

}
