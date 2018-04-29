package com.revature.gambit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


import com.revature.gambit.services.TrainerServiceImpl;
import com.revature.gambit.controllers.UserControllerImpl;
import com.revature.gambit.services.TraineeServiceImpl;
import com.revature.gambit.services.UserServiceImpl;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class Application implements CommandLineRunner{
	
	private static final Logger log = Logger.getLogger(Application.class);
	
	@Autowired
	ApplicationContext context;
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.revature.gambit")).paths(PathSelectors.any()).build();
	}
	@Transactional
	@Override
	public void run(String... arg0) throws Exception {

		log.debug("Running init methods for all fallback classes");
		TrainerServiceImpl trainerService = context.getBean(TrainerServiceImpl.class);
		trainerService.init();
		log.debug("Loading All Trainees Information");
		TraineeServiceImpl traineeService = context.getBean(TraineeServiceImpl.class);
		traineeService.init();
		log.debug("Loading All User Information");
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		userService.init();
		        

	}
}
