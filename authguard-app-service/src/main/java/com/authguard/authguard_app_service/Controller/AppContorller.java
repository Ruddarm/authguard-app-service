package com.authguard.authguard_app_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_app_service.dtos.AppRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.authguard.authguard_app_service.dtos.AppResponse;
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
        return ResponseEntity.ok(appService.creatApp(appRequest));
    }

}
