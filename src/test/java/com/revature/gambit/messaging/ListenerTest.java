package com.revature.gambit.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_BATCH;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
	Receiver receiver;
	
	/**
	 * Tests the Kafka listener Reciever.removeDeletedBatchFromTrainees(String)
	 * 
	 * @author Mark Fleres
	 */
	@Test
	public void deleteBatchListener() {
		
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
		
		//Check that there are trainees in the batch to be modified.
		List<Trainee> traineesInBatch = traineeService.findAllByBatch(batchId);
		assertNotEquals(0,traineesInBatch.size());
		
		//Create a mock BatchDTO to be posted using the batchId in use.
		HashSet<Integer> batchTraineeIds = new HashSet<>();
		for(Trainee trainee : traineesInBatch) {
			batchTraineeIds.add(trainee.getUserId());
		}
		BatchDTO batch = new BatchDTO(batchId,batchTraineeIds);
		
		//Post to Kafka that the batch has been deleted.
		sender.publish(TOPIC_DELETE_BATCH, batch);
		
		//Call the listener method using the information posted.
		String deletedBatchJson = (String)receive(TOPIC_DELETE_BATCH,null);
		assertNotNull(deletedBatchJson);
		receiver.removeDeletedBatchFromTrainees(deletedBatchJson);
		
		//Test that there are no trainees in that batch after the listener method has completed.
		traineesInBatch = traineeService.findAllByBatch(batchId);
		assertEquals(0,traineesInBatch.size());
		
		//Test to show that the trainees still exist
		assertEquals(allTrainees.size(),traineeService.getAll().size());
	}
}
