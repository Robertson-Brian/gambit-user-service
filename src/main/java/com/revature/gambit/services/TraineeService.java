package com.revature.gambit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.gambit.entities.Trainee;

/**
 * Our Trainee Service implementation class. Implements all of the methods
 * defined in the TraineeService interface.
 */
@Service
public interface TraineeService {

	/**
	 * Creates a trainee. Checks if the trainee exists by the email. 
	 * Will return null if trainee already exists.
	 * 
	 * @author Shaleen Anwar
	 * @param trainee
	 * @return new trainee if email is not used; null if email exists.
	 */
	public Trainee save(Trainee trainee);

	/**
	 * Updates a trainee. First checks if the trainee exists by the email.
	 * If the trainee exists, we keep the pre-existing Salesforce resourceId
 	 * and the BatchAssignments, then update the remaining fields.
	 * 
	 * @author Ismael Khalil
	 * @param trainee
	 * @return null if email does not exist, updated trainee if email exists. 
	 */
	public Trainee update(Trainee trainee);

	/**
	 * Removes a trainee from the database entirely. 
	 * 
	 * @author Joseph Arbelaez
	 * @param trainee
	 */
	public void delete(Trainee trainee);

	/**
	 * Find all trainees given their batchId and their training status.
	 * 
	 * @author Brian Ethier
	 * @param batchId, status
	 * @return - List of all trainees in that batch number with the specified status.
	 */
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status);

	/**
	 * Find all of the trainees without restriction.
	 * 
	 * @author Alejandro Iparraguirre
	 * @return a List of all trainees
	 */
	public List<Trainee> getAll();

	/**
	 * Find a trainee by providing their email.
	 * Will return a 404 if email is null or not found.
	 * 
	 * @author Joel DeJesus
	 * @param email
	 * @return trainee if found, 404 if not found.
	 */
	public Trainee findByEmail(String email);

}
