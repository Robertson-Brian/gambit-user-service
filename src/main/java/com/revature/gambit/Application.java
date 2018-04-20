package com.revature.gambit;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;
import com.revature.gambit.services.TraineeService;
import com.revature.gambit.services.TrainerService;
import com.revature.gambit.services.UserService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class Application implements CommandLineRunner {
	  private static Logger log =Logger.getLogger(Application.class);

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

	@Override
	public void run(String... arg0) throws Exception {
//		UserService bean = context.getBean(UserService.class);
//		List<User> allUsers = bean.getAllUsers();
//		log.info("Getting All Users: "+allUsers);
		
		 TrainerService bean = context.getBean(TrainerService.class);
		  List<Trainer> all = bean.getAll();
		  log.info("Getting All tainer:" +all);
		  log.info("Getting All tainer size :" +all.size());

		  
		  
	  
//	  TraineeService traineeService = context.getBean(TraineeService.class);
//	  List<Trainee> getAllTrainee = traineeService.getAll();
//		  log.info("Getting All trainee " +getAllTrainee );
//          
	}
}
