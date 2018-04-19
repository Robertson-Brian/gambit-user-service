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
	 * Saves or updates a trainee. Checks if the trainee exists by the email. If the
	 * trainee exists, we keep the pre-existing Salesforce resourceId and the
	 * BatchAssignments, then update the remaining fields.
	 * 
	 * @param trainee
	 * @return
	 */
	public Trainee save(Trainee trainee);

	/**
	 * Removes a trainee from the database entirely. 
	 * @param trainee
	 */
	public void delete(Trainee trainee);

	/**
	 * Find all trainees given their batchId and their training status.
	 * @param batchId -recieved ID number of batch for search.
	 * @param status - recieved training status of trainee for search.
	 * @return - list of all trainees in that batch number with the specified status.
	 */
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status);

	/**
	 * Find all of the trainees without restriction.
	 * 
	 * @return a List of all trainees
	 */
	public List<Trainee> getAll();

	/**
	 * Find a trainee by providing their email. Will return a 404 if email is null or not found.
	 * @param email
	 * @return
	 */
	public Trainee findByEmail(String email);

}
