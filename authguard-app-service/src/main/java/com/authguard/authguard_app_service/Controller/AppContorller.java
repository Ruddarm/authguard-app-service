package com.authguard.authguard_app_service.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_app_service.dtos.AppRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.authguard.authguard_app_service.dtos.AppResponse;
import com.authguard.authguard_app_service.exception.ResourceException;
import com.authguard.authguard_app_service.services.AppService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apps")
@RequiredArgsConstructor
public class AppContorller {

    private final AppService appService;

    @GetMapping("/name")
    public String getMethodName() {
        return "App name is fuckman";
    }

    @PostMapping("/app")
    public ResponseEntity<AppResponse> createApp(@RequestBody AppRequest appRequest) {
        return new ResponseEntity<>(appService.createApp(appRequest), HttpStatus.CREATED);
    }

    @GetMapping("/app/{client_id}")
    public ResponseEntity<AppResponse> getApp(@PathVariable UUID client_id) throws ResourceException {
        return ResponseEntity.ok(appService.getAppByClient_Id(client_id));
    }

    @GetMapping("/app/list")
    public ResponseEntity<List<AppResponse>> getListOfapp() {
        return ResponseEntity.ok(appService.getAppList());
    }

}
