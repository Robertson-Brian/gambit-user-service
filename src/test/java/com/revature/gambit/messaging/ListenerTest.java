package com.revature.gambit.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_BATCH;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gambit.entities.BatchDTO;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.services.TraineeService;

@Transactional
public class ListenerTest extends KafkaTest{
	
	@Autowired
	TraineeService traineeService;
	
	@Autowired
	Sender sender;
	
	@Autowired
	Reciever reciever;
	
	/**
	 * Tests the listener Reciever.removeDeletedBatchFromTrainees(String)
	 * 
	 * @author Mark Fleres
	 */
	@Test
	public void deleteBatchListener() {
		ObjectMapper mapper = new ObjectMapper();
		
		//Find a batch id that has at least one trainee.
		List<Trainee> allTrainees = traineeService.getAll();
		int batchId = -1;
		getBatch: for(Trainee trainee : allTrainees) {
			Set<Integer> batches;
			if(!(batches = trainee.getBatches()).isEmpty()) {
				for(Integer existingBatchId : batches) {
					batchId = existingBatchId;
					break getBatch;
				}
			}
		}
		assertNotEquals(-1,batchId);
		
		//Double check that there are trainees in the batch
		List<Trainee> traineesInBatch = traineeService.findAllByBatch(batchId);
		assertNotEquals(0,traineesInBatch.size());
		
		//Publish the topic to simulate a batch delete
		BatchDTO batch = new BatchDTO(batchId,new HashSet<Integer>());
		
//		sender.publish(TOPIC_DELETE_BATCH, batch);
//		BatchDTO deletedBatch = (BatchDTO)receive(TOPIC_DELETE_BATCH,BatchDTO.class);
//		assertNotNull(deletedBatch);
		try {
			reciever.removeDeletedBatchFromTrainees(mapper.writeValueAsString(batch));
		} catch (JsonProcessingException e) {
			assertNull(batch);
		}
		
		//Test that there are no trainees in that batch anymore
		traineesInBatch = traineeService.findAllByBatch(batchId);
		assertEquals(0,traineesInBatch.size());
	}
}
