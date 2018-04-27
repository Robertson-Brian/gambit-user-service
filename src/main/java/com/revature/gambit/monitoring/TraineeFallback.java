package com.revature.gambit.monitoring;

import java.util.List;

import org.springframework.stereotype.Component;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.services.TraineeService;
@Component
public class TraineeFallback implements TraineeService{

	
	
	@Override
	public Trainee save(Trainee trainee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trainee update(Trainee trainee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Trainee trainee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trainee> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trainee findByEmail(String email) {

		return new Trainee();
	}

	 
}
