package com.revature.gambit.messaging;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gambit.GambitTest;

@DirtiesContext
public class SenderTest extends GambitTest{
	private static KafkaMessageListenerContainer<String, String> container;

	private static BlockingQueue<ConsumerRecord<String, String>> records;
  
	private static ObjectMapper mapper = new ObjectMapper();

	@ClassRule
	public static KafkaEmbedded embeddedKafka = 
		new KafkaEmbedded(1, true, 6);

	@BeforeClass
	public static void setUp() throws Exception {
		// set up the Kafka consumer properties
		Map<String, Object> consumerProperties =
				KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);

		// create a Kafka consumer factory
		DefaultKafkaConsumerFactory<String, String> consumerFactory =
				new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

		// set the topic that needs to be consumed
		ContainerProperties containerProperties = new ContainerProperties(
				"trainer.register.t", "trainer.update.t", "trainer.delete.t",
				"trainee.register.t", "trainee.update.t", "trainee.delete.t");

		// create a Kafka MessageListenerContainer
		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

		// create a thread safe queue to store the received message
		records = new LinkedBlockingQueue<>();

		// setup a Kafka message listener
		container.setupMessageListener(new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				//logger.debug("test-listener received message=" + record.toString());
				records.add(record);
			}
		});

		// start the container and underlying message listener
		container.start();

		// wait until the container has the required number of assigned partitions
		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
	}

	@AfterClass
	public static void tearDown() {
		// stop the container
		container.stop();
	}
  
	public Object receive(Class<?> clazz) throws InterruptedException{
		// Use this to receive object from mock kafka server. It will unmarshalled the json.
		ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
		try {
			return mapper.readValue(received.value(), clazz);
		} catch (Exception e) {
			return null;
		}  
	}

}
