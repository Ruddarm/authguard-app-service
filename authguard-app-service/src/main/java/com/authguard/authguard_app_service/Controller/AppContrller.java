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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
/*
 * A Controller class to handel request for management of apps
 * 
 * <p>It provide routes to perofrom following operation</p>
 * <ul>
 *      <li>create app</li>
 *      <li>get app details by Id</li>
 *      <li>get app list or authentcated user</li>
 * <ul>
 * 
 */
@RestController
@RequestMapping("/apps")
@RequiredArgsConstructor
public class AppContrller {

    private final AppService appService;

    /*
     * A temporary testing route
     * 
     * @Return  static String msg that indicate app-service is working
     */
    @GetMapping("/name")
    public String getMethodName() {
        return "App is working";
    }
    /*
     * 
     * A Post route for creation of app
     * @Param requreid a body of AppRequest
     * @Return
     */
    @PostMapping("/app")
    public ResponseEntity<AppResponse> createApp(@Valid @RequestBody AppRequest appRequest) {
        return new ResponseEntity<>(appService.createApp(appRequest), HttpStatus.CREATED);
    }
    /*
     * A Get Route to fetch the detials of app by client_id;
     * @Param requred a  pathvariable fo type UUID clientId;
     * @Return {@Link AppResponse} 
     */
    @GetMapping("/app/{client_id}")
    public ResponseEntity<AppResponse> getApp(@PathVariable UUID client_id) throws ResourceException {
        return ResponseEntity.ok(appService.getAppByClient_Id(client_id));
    }
    /*
     * A Get route to fethc the list of app 
     * 
     * @Return {@Link AppResponse} but only client_id and appName is set;
     */
    @GetMapping("/app/list")
    public ResponseEntity<List<AppResponse>> getListOfapp() {
        return ResponseEntity.ok(appService.getAppList());
    }

}
