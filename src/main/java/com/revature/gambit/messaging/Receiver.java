package com.revature.gambit.messaging;

import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_BATCH;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gambit.entities.BatchDTO;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.services.TraineeService;

@Component
public class Receiver {
	
	private static Logger logger = Logger.getLogger(Receiver.class);
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	TraineeService traineeService;
	
	/**
	 * Removes the batch reference from all trainees with the deleted batch.
	 * 
	 * @author Mark Fleres
	 * @param payload
	 * 	The marshalled Batch Object
	 */
	@KafkaListener(topics=TOPIC_DELETE_BATCH)
	public void removeDeletedBatchFromTrainees(String payload) {
		logger.debug("Receiving payload for batch deletion: " + payload);
		BatchDTO batch;
		try {
			batch = (BatchDTO) objectMapper.readValue(payload, BatchDTO.class);
		} catch (Exception e) {
			logger.warn("Unable to unmarshall batch deletion payload.", e);
			return;
		}

		List<Trainee> affectedTrainees = traineeService.findAllByBatch(batch.getBatchId());
		for(Trainee trainee : affectedTrainees){
			Set<Integer> traineeBatches = trainee.getBatches();
			traineeBatches.remove(batch.getBatchId());
			trainee.setBatches(traineeBatches);
			traineeService.update(trainee);
		}
	}
}