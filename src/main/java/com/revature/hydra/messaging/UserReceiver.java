package com.revature.hydra.messaging;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.revature.hydra.controllers.TraineeController;
import com.revature.hydra.entities.TraineeDTO;
import com.revature.hydra.entities.TrainerDTO;
import com.revature.hydra.services.TraineeService;
import com.revature.hydra.services.TrainerService;

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
public class UserReceiver {

	// Turns on the receivers when the bean is initialized by spring.
	public UserReceiver() {
		super();
		try {
			this.receiveTrainer();
			this.receiveTrainee();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	private static final Logger log = Logger.getLogger(UserReceiver.class);
	private ObjectMapper om = new ObjectMapper();

	@Autowired
	private TrainerService trainerService;

	@Autowired
	private TraineeService traineeService;

	private static final String TRAINEE_EXCHANGE_NAME = "hydra.trainee.exchange";
	private static final String TRAINER_EXCHANGE_NAME = "hydra.trainer.exchange";

	/**
	 * Receives messages from the TRAINER exchange
	 */
	public void receiveTrainer() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		// Gets the address of the local machine
		// Has not been tested on an EC2  04/02/2018
		factory.setHost(InetAddress.getLocalHost().getHostAddress());
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(TRAINER_EXCHANGE_NAME, "fanout");
		// Declares a queue, allows rabbitmq to automatically generate the queue's name.
		// Recommend research on .queueDeclare() for more information on customizing queues
		String queueName = channel.queueDeclare().getQueue();
		// Binds the queue to the exchange
		channel.queueBind(queueName, TRAINER_EXCHANGE_NAME, "");

		log.info(" [*] Waiting for messages.");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				log.info(" [x] Trainer Received '" + message + "'");
				TrainerDTO trainer = om.readValue(message, TrainerDTO.class);
				
				// Checks if the sender receives their own message, which is intended behavior.
				// It is not required for function, but does show that it is working
				if (trainer.getSender().equals(InetAddress.getLocalHost().getHostAddress())) {
					log.info("Received own message, as intended.");
				} else {
					/* 
					 * Additional functions would be added here based on what you want to do with the received information
					*/
					if (trainer.getRequestType().equals("PUT")) {
						trainerService.update(trainer.getTrainerUser());
					}
					if (trainer.getRequestType().equals("POST")) {
						trainerService.newTrainer(trainer.getTrainerUser());
					}
				}
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

	/**
	 * Receives messages from the TRAINEE exchange
	 */
	public void receiveTrainee() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		// Gets the address of the local machine
		// Has not been tested on an EC2  04/02/2018
		factory.setHost(InetAddress.getLocalHost().getHostAddress());
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(TRAINEE_EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, TRAINEE_EXCHANGE_NAME, "");

		log.info(" [*] Waiting for messages.");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				log.info(" [x] Trainee Received '" + message + "'");
				TraineeDTO trainee = om.readValue(message, TraineeDTO.class);
				
				// Checks if the sender receives their own message, which is intended behavior.
				// It is not required for function, but does show that it is working
				if (trainee.getSender().equals(InetAddress.getLocalHost().getHostAddress())) {
					log.info("Received own message, as intended.");
				} else {
					/* 
					 * Additional functions would be added here based on what you want to do with the received information
					*/
					if (trainee.getRequestType().equals("PUT")) {
						traineeService.update(trainee.getTrainee());
					}
					if (trainee.getRequestType().equals("POST")) {
						// gets the trainee object from the wrapper object
						traineeService.save(trainee.getTrainee());
					}
				}

			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

}
