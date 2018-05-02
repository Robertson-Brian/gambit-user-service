package com.revature.gambit.entities;

import java.util.Set;

public class BatchDTO {
	
	private int batchId;
	
	private Set<Integer> trainees;
	
	public BatchDTO() {}
	
	public BatchDTO(int batchId, Set<Integer> trainees) {
		this.batchId = batchId;
		this.trainees = trainees;
	}
	
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public Set<Integer> getTrainees() {
		return trainees;
	}
	public void setTrainees(Set<Integer> trainees) {
		this.trainees = trainees;
	}
}