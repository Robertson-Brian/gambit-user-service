package com.revature.hydra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value = "com.revature.hydra.entities")
public class ApplicationTest {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class, args);
	}
}
 