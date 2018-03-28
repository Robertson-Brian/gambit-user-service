package com.revature.hydra.trainer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.hydra.trainer.service.TrainerService;

@Configuration
public class TrainerProducerConfiguration {
	@Autowired
	private TrainerService mms;

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory factory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
		rabbitTemplate.setExchange("revature.hydra.repos");
		return new RabbitTemplate(factory);
	}

	@Bean
	public TrainerService trainerService() {
		return new TrainerService();
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {

		};
	}

}
