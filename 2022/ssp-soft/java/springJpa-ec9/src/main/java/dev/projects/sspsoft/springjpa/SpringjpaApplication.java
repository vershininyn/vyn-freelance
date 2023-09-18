package dev.projects.sspsoft.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SpringjpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringjpaApplication.class, args);
	}
}
