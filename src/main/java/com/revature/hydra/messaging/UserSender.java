package com.revature.hydra.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.revature.hydra.entities.Trainee;
import com.revature.hydra.entities.TraineeDTO;
import com.revature.hydra.entities.TrainerDTO;
import com.revature.hydra.entities.TrainerUser;

/**
 * 
 * @author Brandon Cross, Ben Zahler (Blake 1801)
 *
 *         In order to receive messages through rabbitmq your computer must have
 *         port 5672 open.
 * 
 *         This class is the second edition of utilizing rabbitmq messaging
 *         queues. Currently a queue is not specified and instead rabbitmq
 *         creates it for us. This probably needs to change. Also the IP address
 *         is hard coded.
 *
 */
@Service
public class UserSender {

	private static final Logger log = Logger.getLogger(UserSender.class);

	private static final String TRAINEE_EXCHANGE = "hydra.trainee.exchange";
	private static final String TRAINER_EXCHANGE = "hydra.trainer.exchange";
	ObjectMapper om = new ObjectMapper();

	/**
	 * Sends message to trainer exchange when a new trainer is created. 
	 * 
	 * Creates a Wrapper Object called TrainerDTO containing a timestamp and IP address.
	 * Declares exchange as TRAINER_EXCHANGE for listeners to obtain message from.
	 * Message is JSON representation of the DTO, written by Object Mapper.
	 */
	public void sendTrainer(TrainerUser tu, String type) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		
		// Wrapper Object containing the trainer, timestamp and sender IP address
		TrainerDTO trainer = new TrainerDTO(tu, type);
		
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		
		// Gets IP of the sender
		factory.setHost(trainer.getSender());
		
		// Creates a connection channel, declares Trainer exchange
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINER_EXCHANGE, "fanout");
		
		// Object Mapper writes JSON of Trainer DTO
		String message = om.writeValueAsString(trainer);
		channel.basicPublish(TRAINER_EXCHANGE, "", null, message.getBytes());
		log.info(" [x] Sent new trainer: '" + message + "'");

		channel.close();
		connection.close();
	}

	/**
	 * Sends message to trainee exchange when a new trainee is created.
	 * 
	 * Creates a Wrapper Object called TraineeDTO containing a timestamp and IP address.
	 * Declares exchange as TRAINEE_EXCHANGE for listeners to obtain message from.
	 * Message is JSON representation of the DTO, written by Object Mapper.
	 */
	public void sendTrainee(Trainee t, String type) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		
		// Wrapper Object containing the trainee, timestamp and sender IP address
		TraineeDTO trainee = new TraineeDTO(t, type);
		
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		
		// Gets IP of the sender
		factory.setHost("10.226.124.149");
		
		// Creates a connection channel, declares Trainee exchange
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINEE_EXCHANGE, "fanout");
		
		// Object Mapper writes JSON of Trainer DTO
		String message = om.writeValueAsString(trainee);
		channel.basicPublish(TRAINEE_EXCHANGE, "", null, message.getBytes());
		System.out.println(" [x] Sent new trainee: '" + message + "'");

		channel.close();
		connection.close();
	}
}
