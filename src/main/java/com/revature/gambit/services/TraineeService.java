package com.revature.gambit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.gambit.entities.Trainee;

/**
 * Our Trainee Service implementation class. Implements all of the methods
 * defined in the TraineeService interface.
 * 
 * @author Charles Courtois
 * @author Patrick Walsh
 * @author Blake Kruppa
 * @author Nick Jurczak
 * @author Emily Higgins
 * @author Peter Alagna
 *
 */
@Service
public interface TraineeService {

	/**
	 * Creates a trainee. Checks if the trainee exists by the email. 
	 * Will return null if trainee already exists.
	 * 
	 * @param trainee
	 * @author Shaleen Anwar
	 * @return new trainee if email is not used; null if email exists.
	 */
	public Trainee save(Trainee trainee);

	/**
	 * Updates a trainee. First checks if the trainee exists by the email.
	 * If the trainee exists, we keep the pre-existing Salesforce resourceId
 	 * and the BatchAssignments, then update the remaining fields.
	 * 
	 * @param trainee
	 * @author Ismael Khalil
	 * @return null if email does not exist, updated trainee if email exists. 
	 */
	public Trainee update(Trainee trainee);

	/**
	 * Removes a trainee from the database entirely. 
	 * 
	 * @param trainee
	 * @author Joseph Arbelaez
	 */
	public void delete(Trainee trainee);

	/**
	 * Find all trainees given their batchId and their training status.
	 * 
	 * @param batchId, status
	 * @author Brian Ethier
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
	 * Find a trainee by userId
	 * 
	 * @return trainee with the corresponding userId
	 */
	public Trainee findByUserId(int userId);

	/**
	 * Find a trainee by providing their email.
	 * Will return a 404 if email is null or not found.
	 * 
	 * @param email
	 * @author Joel DeJesus
	 * @return trainee if found, 404 if not found.
	 */
	public Trainee findByEmail(String email);

}
