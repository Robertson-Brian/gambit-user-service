package com.revature.hydra.trainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;




@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
@EntityScan(value = "com.revature.entities")
public class TrainerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrainerApplication.class, args);
	}
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.revature.hydra.trainer"))              
          .paths(PathSelectors.any())                          
          .build();
    }
	
}
