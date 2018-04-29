package com.revature.gambit.messaging;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Sender {
	
	private static Logger logger = Logger.getLogger(Sender.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	KafkaTemplate<String,String> template;
	
	public void publish(String topic, Object payload) {
		logger.info("Sending payload for topic: " + topic);
		logger.trace(topic + ": Sending " + payload);
		try {
			template.send(topic, mapper.writeValueAsString(payload));
			logger.trace("Payload, " + payload + ", for topic, " + topic + " successfully sent.");
		} catch (Exception e) {
			logger.error("Couldn't stringify POJO in sender.", e);
		}
	}
}
