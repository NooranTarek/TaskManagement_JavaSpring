package com.example.task_management_API;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j

public class TaskManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApiApplication.class, args);
		log.info("hello");
	}

}
