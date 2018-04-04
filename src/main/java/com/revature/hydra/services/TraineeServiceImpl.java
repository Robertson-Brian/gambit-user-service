package com.revature.hydra.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.hydra.controllers.TraineeController;
import com.revature.hydra.entities.Trainee;
import com.revature.hydra.entities.TraineeBatch;
//import com.revature.hydra.entities.TraineeBatch;
import com.revature.hydra.entities.User;
import com.revature.hydra.messaging.UserSender;
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

	@Autowired
	private UserSender us;

	private static final Logger log = Logger.getLogger(TraineeController.class);

	/**
	 * The implemented method to create a new trainee.
	 */
	@Override
	@Transactional
	public Trainee save(Trainee trainee) {
		log.trace("Method called to save a trainee.");

		log.trace("Getting the trainees user information.");
		// A trainee is a User, and that information should already exist.
		User persisted = userRepo.save(trainee.getTraineeUserInfo());
		Trainee toSend = trainee;
		toSend.setTraineeUserInfo(persisted);

		// Trainee id must be 0 to create a new trainee
		toSend.setTraineeId(0);

		log.trace("Saving the trainee.");
		Trainee toReturn = traineeRepo.save(toSend);

		// Create a list of trainee batches
		List<TraineeBatch> ltb = new ArrayList<TraineeBatch>();
		System.out.println(traineeRepo.findAll().size());// necessary in order to force loading. @Function is default
															// lazy and cannot be changed as far as we know
		// The Trainee entity has a list of batches
		// This for loop saves those batches to the traineeBatch repo.
		for (int i = 0; i < trainee.getBatches().size(); i++) {
			TraineeBatch tb = new TraineeBatch(toReturn.getTraineeId(), toSend.getBatches().get(i).getBatch_id());
			ltb.add(traineeBatchRepo.save(tb));
		}

		// Trainee toReturn = traineeRepo.findOne(toPersist.getTraineeId());
		toReturn.setBatches(ltb);
		try {
			us.sendTrainee(toReturn, "POST");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	/**
	 * The implemented method to find all trainees in the batch with the provided
	 * batchId.
	 */
	@Override
	@Transactional
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		log.trace("Method called to findAllByBatchAndStatus.");
		return traineeRepo.findAllByBatchesBatchIdAndTrainingStatus(batchId, status);
	}

	/**
	 * The implemented method to update a trainee.
	 */
	@Override
	@Transactional
	public void update(Trainee trainee) {
		log.trace("Method called to update a Trainee.");
		traineeRepo.save(trainee);
		try {
			us.sendTrainee(trainee, "PUT");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The implemented method to delete a trainee.
	 */
	@Override
	@Transactional
	public void delete(Trainee trainee) {
		log.trace("Method called to delete a trainee.");
		traineeRepo.delete(trainee);

	}

	@Override
	@Transactional
	public List<Trainee> getAll() {
		log.trace("Method called to findAll Trainees.");
		return traineeRepo.findAll();
	}

}
