package com.authguard.authguard_app_service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.authguard.authguard_app_service.model.entity.AppEntity;
import com.authguard.authguard_app_service.repository.AppRepository;

@SpringBootTest
class AuthguardAppServiceApplicationTests {

	@Autowired
	private AppRepository appRepo;


	@Test
	void contextLoads() {
	}

	@Test
	public void ProjectionTest(){
		List<AppEntity>  apps = appRepo.findAll();
		System.out.println("Size of apps "+apps.size());
		for(AppEntity  app : apps){
			System.out.print("App is :");
			System.out.println(app);
		}
	}

}
