package com.revature.gambit.messaging;

import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_TRAINER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_PROMOTE_USER_TO_TRAINER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_TRAINER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_TRAINER;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

	@ClassRule
	public static KafkaEmbedded embeddedKafka =
		new KafkaEmbedded(1, true, 7);
	
	@Before
	public void resetQueue() {
		records = new LinkedBlockingQueue<>();
	}

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
				TOPIC_DELETE_TRAINEE, TOPIC_DELETE_TRAINER,
				TOPIC_REGISTER_TRAINEE, TOPIC_REGISTER_TRAINER,
				TOPIC_UPDATE_TRAINEE, TOPIC_UPDATE_TRAINER,
				TOPIC_PROMOTE_USER_TO_TRAINER);

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
}