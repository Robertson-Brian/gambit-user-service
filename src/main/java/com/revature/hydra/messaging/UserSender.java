package com.revature.hydra.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
 * @author Ben Zahler (Blake 1801)
 *
 *         In order to receive messages through rabbitmq your computer must have
 *         port 5672 open.
 * 
 *         This class is the first edition of utilizing rabbitmq messaging
 *         queues. Currently a queue is not specified and instead rabbitmq
 *         creates it for us. This probably needs to change. Also the IP address
 *         is hard coded.
 * 
 *         Currently (3/28/2018) this function is called from the trainee
 *         controller and is triggered by making a postman request to
 *         trainees/testing
 *
 */
@Service
public class UserSender {

	private static final String TRAINEE_EXCHANGE_NAME = "hydra.trainee.exchange";
	private static final String TRAINER_EXCHANGE_NAME = "hydra.trainer.exchange";
	ObjectMapper om = new ObjectMapper();

	public void send() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		factory.setHost("10.226.102.1");
		// Currently this is the hard coded address of the host.
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINEE_EXCHANGE_NAME, "fanout");
		String message = "WEEEEoooooWEEEEEooooWEEEoooo";
		channel.basicPublish(TRAINEE_EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	public void sendNewTrainer(TrainerUser tu) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		TrainerDTO trainer = new TrainerDTO(tu);
		factory.setUsername("test");
		factory.setPassword("test");
		factory.setHost(trainer.getSender());
		// Currently this is the hard coded address of the host.
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINER_EXCHANGE_NAME, "fanout");
		String message = om.writeValueAsString(trainer);
		channel.basicPublish(TRAINER_EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent new trainer: '" + message + "'");

		channel.close();
		connection.close();
	}

	public void sendNewTrainee(Trainee t) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		TraineeDTO trainee = new TraineeDTO(t);
		factory.setUsername("test");
		factory.setPassword("test");
		factory.setHost(trainee.getSender());
		// Currently this is the hard coded address of the host.
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINEE_EXCHANGE_NAME, "fanout");
		String message = om.writeValueAsString(trainee);
		channel.basicPublish(TRAINEE_EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent new trainee: '" + message + "'");

		channel.close();
		connection.close();
	}

	public void sendUpdateTrainer(TrainerUser tu) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		TrainerDTO trainer = new TrainerDTO(tu);
		factory.setUsername("test");
		factory.setPassword("test");
		factory.setHost(trainer.getSender());
		// Currently this is the hard coded address of the host.
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINER_EXCHANGE_NAME, "fanout");
		String message = om.writeValueAsString(trainer);
		channel.basicPublish(TRAINER_EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent updated trainer: '" + message + "'");

		channel.close();
		connection.close();
	}

	public void sendUpdateTrainee(Trainee t) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		TraineeDTO trainee = new TraineeDTO(t);
		factory.setUsername("test");
		factory.setPassword("test");
		factory.setHost(trainee.getSender());
		// Currently this is the hard coded address of the host.
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(TRAINEE_EXCHANGE_NAME, "fanout");
		String message = om.writeValueAsString(trainee);
		channel.basicPublish(TRAINEE_EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent updated trainee: '" + message + "'");

		channel.close();
		connection.close();
	}

}
