package com.revature.hydra.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.hydra.entities.Trainee;
import com.revature.hydra.entities.TraineeBatch;
//import com.revature.hydra.entities.TraineeBatch;
import com.revature.hydra.entities.User;
import com.revature.hydra.repo.TraineeBatchRepository;
//import com.revature.hydra.repo.TraineeBatchRepository;
import com.revature.hydra.repo.TraineeRepository;
import com.revature.hydra.repo.UserRepository;

/**
 * Our Hydra Trainee Service implementation class. Implements all of the methods
 * defined in the TraineeService interface.
 * 
 * @author Charles Courtois
 *
 */
@Service
public class TraineeServiceImpl implements TraineeService {
	@Autowired
	TraineeRepository traineeRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TraineeBatchRepository traineeBatchRepo;

	/**
	 * The implemented method to create a new trainee.
	 */
	@Override
	@Transactional
	public Trainee save(Trainee trainee) {
		User persisted = userRepo.save(trainee.getTraineeUserInfo());
		Trainee toSend = trainee;
		toSend.setTraineeUserInfo(persisted);
		// Trainee id must be 0 to create a new trainee
		toSend.setTraineeId(0);
		Trainee toReturn = traineeRepo.save(toSend);
		List<TraineeBatch> ltb = new ArrayList<TraineeBatch>();
		System.out.println(traineeRepo.findAll().size());//necessary in order to force loading. @Function is default lazy and cannot be changed as far as we know
		for(int i=0; i<trainee.getBatches().size(); i++) {
			TraineeBatch tb = new TraineeBatch(toReturn.getTraineeId(), toSend.getBatches().get(i).getBatch_id());
			ltb.add(traineeBatchRepo.save(tb));
		}
		//Trainee toReturn = traineeRepo.findOne(toPersist.getTraineeId());
		toReturn.setBatches(ltb);
		return toReturn;
	}

	/**
	 * The implemented method to find all trainees in the batch with the provided
	 * batchId.
	 */
	@Override
	@Transactional
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		return traineeRepo.findAllByBatchesBatchIdAndTrainingStatus(batchId, status);
	}
	
	/**
	 * The implemented method to update a trainee.
	 */
	@Override
	@Transactional
	public void update(Trainee trainee) {
		traineeRepo.save(trainee);
	}

	/**
	 * The implemented method to delete a trainee.
	 */
	@Override
	@Transactional
	public void delete(Trainee trainee) {
		traineeRepo.delete(trainee);

	}

	@Override
	@Transactional
	public List<Trainee> getAll() {
		return traineeRepo.findAll();
	}

}
