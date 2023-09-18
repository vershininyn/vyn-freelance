package dev.projects.sspsoft.apiship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiShipServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiShipServiceApplication.class, args);
	}
}
