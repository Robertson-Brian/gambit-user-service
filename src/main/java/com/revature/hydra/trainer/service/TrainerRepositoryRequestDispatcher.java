package com.revature.hydra.trainer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.revature.entities.BatchTrainer;
import com.revature.hydra.trainer.data.TrainerRepository;

@Service
public class TrainerRepositoryRequestDispatcher {

	@Autowired
	private TrainerRepository trainerRepository;

	@Autowired
	private TrainerService trainerService;

	private static final Logger log = Logger.getLogger(TrainerRepositoryRequestDispatcher.class);

	/**
	 * Parse JsonObject for method to execute Executable methods: findOne - find a
	 * SimpleTrainer by trainerId delete - delete a Trainer by trainerId
	 *
	 * @param request
	 *
	 * @return result
	 */
	public BatchTrainer processSingleSimpleTrainerRequest(JsonObject request) {

		BatchTrainer result = null;
		String methodName = request.get("methodName").getAsString();

		if (methodName.equals("findOne")) {
			Integer trainerId = request.get("trainerId").getAsInt();
			result = trainerRepository.findOne(trainerId); // was findByTrainerId
		} else if (methodName.equals("delete")) {
			trainerRepository.delete(request.get("trainerId").getAsInt());
			result = null;
		}

		return result;
	}

	/**
	 * Parse JsonObject for method to execute Executable methods: findAll - find all
	 * SimpleTrainer
	 *
	 * @param request
	 *
	 * @return result
	 */
	public List<BatchTrainer> processListSimpleTrainerRequest(JsonObject request) {
		List<BatchTrainer> result = null;
		String methodName = request.get("methodName").getAsString();

		if (methodName.equals("findAll")) {
			result = trainerRepository.findAll();
		}

		return result;
	}
}

	/**
	 * Parse JsonObject for method to execute Executable methods: findByEmail - find
	 * a Trainer by email
	 *
	 * @param request
	 *
	 * @return result
	 */
//	public SimpleTrainer processSingleTrainerRequest(JsonObject request) {
//		SimpleTrainer result = null;
//		String methodName = request.get("methodName").getAsString();
//
//		if (methodName.equals("findByEmail")) {
//			result = trainerService.findByEmail(request.get("email").getAsString());
//		}
//		return result;
//	}
//}
