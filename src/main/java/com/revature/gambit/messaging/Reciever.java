package com.revature.gambit.messaging;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gambit.entities.BatchDTO;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.services.TraineeService;

public class Reciever {
	
	@Autowired
	TraineeService traineeService;
	
	@KafkaListener(topics="${spring.kafka.topic.batch.delete}")
	public void removeDeletedBatchFromTrainees(String payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		BatchDTO batch = null;
		try {
			batch = (BatchDTO)objectMapper.readValue(payload, BatchDTO.class);
		} catch (Exception e) {
			batch = null;
		}finally {
			 if(batch == null) {
				 return;
			 }
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