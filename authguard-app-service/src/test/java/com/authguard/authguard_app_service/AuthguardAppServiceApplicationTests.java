package com.authguard.authguard_app_service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.authguard.authguard_app_service.dtos.AppResponse;
import com.authguard.authguard_app_service.model.entity.AppEntity;
import com.authguard.authguard_app_service.repository.AppRepository;

@SpringBootTest
class AuthguardAppServiceApplicationTests {

	@Autowired
	private AppRepository appRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void ProjectionTest() {
		List<AppEntity> apps = appRepo.findAll();
		System.out.println("Size of apps " + apps.size());
		for (AppEntity app : apps) {
			System.out.print("App is :");
			System.out.println(app);
		}
	}

	@Test
	public void AppListTest() {
		UUID userId = UUID.randomUUID();
		AppEntity app = AppEntity.builder()
				.appName("demo1")
				.userId(userId)
				.client_secret(UUID.randomUUID().toString())
				.build();
		app = appRepo.save(app);
		app = AppEntity.builder()
				.appName("demo2")
				.userId(userId)
				.client_secret(UUID.randomUUID().toString())
				.build();
		app = appRepo.save(app);
		app = AppEntity.builder()
				.appName("demo3")
				.userId(userId)
				.client_secret(UUID.randomUUID().toString())
				.build();
		app = appRepo.save(app);
		System.out.println("App got created");
		List<AppResponse> appList = appRepo.findByUserId(userId)
				.stream()
				.map(appEntity -> modelMapper.map(appEntity, AppResponse.class))
				.collect(Collectors.toList());

		for (AppResponse a : appList) {
			System.out.println(a);
		}

	}
}
