package com.revature.hydra.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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
public class UserReceiver {

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

	private static final String TRAINEE_EXCHANGE_NAME = "hydra.trainee.exchange";
	private static final String TRAINER_EXCHANGE_NAME = "hydra.trainer.exchange";

	public void receiveTrainer() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		// Currently this is the hard coded address of the host.
		factory.setHost("10.226.102.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// Fanout is important
		channel.exchangeDeclare(TRAINER_EXCHANGE_NAME, "fanout");
		// I believe this needs to be changed so that messages aren't dropped.
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, TRAINER_EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages.");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Trainer Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

	public void receiveTrainee() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		// This user was created on host machine through the rabbitmq management console
		// (localhost:15672 as of 3/28/2018)
		factory.setUsername("test");
		factory.setPassword("test");
		// Currently this is the hard coded address of the host.
		factory.setHost("10.226.102.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// Fanout is important
		channel.exchangeDeclare(TRAINEE_EXCHANGE_NAME, "fanout");
		// I believe this needs to be changed so that messages aren't dropped.
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, TRAINEE_EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages.");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Trainee Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

}
