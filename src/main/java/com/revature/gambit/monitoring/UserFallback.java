package com.revature.gambit.monitoring;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;

import com.revature.gambit.services.UserService;
//@SpringBootApplication
//@EnableCircuitBreaker

public class UserFallback  {

  private static Logger log =Logger.getLogger(UserFallback.class);
//	@Autowired
//	ApplicationContext context;
//	public void init(){
//	
//		
//	log.info(context.getBean(UserService.class).getAllUsers());
//
//	}


}
