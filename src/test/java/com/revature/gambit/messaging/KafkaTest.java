package com.revature.gambit.messaging;

import static com.revature.gambit.util.MessagingUtil.*;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gambit.GambitTest;

/**
 * Configuration class for Embedded Kafka Broker.
 * 
 * @author Jeffrey Reyes
 * @author Mark Fleres
 *
 */
public class KafkaTest extends GambitTest {
	private static KafkaMessageListenerContainer<String, String> container;

	private static BlockingQueue<ConsumerRecord<String, String>> records;

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private static final int POLLING_TIMEOUT = 10;
	
	private static final int CONTAINER_COUNT = 1;
	
	private static final int PARTITION_COUNT = 1;

	@ClassRule
	public static KafkaEmbedded embeddedKafka =
		new KafkaEmbedded(CONTAINER_COUNT, true, PARTITION_COUNT);

	
	@Before
	public void resetQueue() {
		records = new LinkedBlockingQueue<>();
	}

	@BeforeClass
	public static void setUp() throws Exception {
		// set up the Kafka consumer properties
		Map<String, Object> consumerProperties =
				KafkaTestUtils.consumerProps("gambit", "false", embeddedKafka);

		// create a Kafka consumer factory
		DefaultKafkaConsumerFactory<String, String> consumerFactory =
				new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

		// set the topic that needs to be consumed
		ContainerProperties containerProperties = new ContainerProperties(
				TOPIC_DELETE_TRAINEE, TOPIC_DELETE_TRAINER, TOPIC_DELETE_USER,
				TOPIC_REGISTER_TRAINEE, TOPIC_REGISTER_TRAINER, TOPIC_REGISTER_USER,
				TOPIC_UPDATE_TRAINEE, TOPIC_UPDATE_TRAINER, TOPIC_UPDATE_USER,
				TOPIC_PROMOTE_USER, TOPIC_DELETE_BATCH);


		// create a Kafka MessageListenerContainer
		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

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
	}

	@AfterClass
	public static void tearDown() {
		// stop the container
		container.stop();
	}

	public Object receive(Class<?> clazz) {
		try {
			// Use this to receive object from mock kafka server. It will unmarshall the json.
			ConsumerRecord<String, String> received = records.poll(POLLING_TIMEOUT, TimeUnit.SECONDS);
			return mapper.readValue(received.value(), clazz);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Receives the value posted to a specific topic. If there are no new postings over
	 * POLLING_TIMEOUT, returns null. If clazz is null, the Object will not be unmarshalled.
	 * 
	 * @author Mark Fleres
	 * @param topic
	 * @param clazz
	 * @return
	 */
	public Object receive(String topic, Class<?> clazz) {
		// Use this to receive object from mock kafka server. It will unmarshalled the json.
		BlockingQueue<ConsumerRecord<String, String>> backupRecords = new LinkedBlockingQueue<>();
		ConsumerRecord<String, String> received = null;
		try {
			while((received = records.poll(POLLING_TIMEOUT, TimeUnit.SECONDS)) != null) {
				if(received.topic().equals(topic)) {
					//Cleanup
					backupRecords.addAll(records);
					records = backupRecords;
					//Return the raw JSON
					if(clazz == null) {
						return received.value();
					} else {
						//Return the unmarshalled JSON
						try {
							return mapper.readValue(received.value(), clazz);
						} catch (Exception e) {
							return null;
						}
					}
				} else {
					backupRecords.put(received);
				}
			}
		} catch (InterruptedException e) {
			backupRecords.addAll(records);
			records = backupRecords;
			return null;
		}
		return null;
	}
}
