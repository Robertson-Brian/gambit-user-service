package com.revature.hydra.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "TRAINEE_BATCH")
@Entity
public class TraineeBatch {
	@Column(name = "trainee_id")
	Integer trainee_id;
	
	@Column(name = "batch_id")
	Integer batch_id;

	public TraineeBatch(int traineeId, Integer batch_id) {
		this.trainee_id = traineeId;
		this.batch_id = batch_id;
		// TODO Auto-generated constructor stub
	}

	public Integer getTrainee_id() {
		return trainee_id;
	}

	public void setTrainee_id(Integer trainee_id) {
		this.trainee_id = trainee_id;
	}

	public Integer getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Integer batch_id) {
		this.batch_id = batch_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batch_id == null) ? 0 : batch_id.hashCode());
		result = prime * result + ((trainee_id == null) ? 0 : trainee_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraineeBatch other = (TraineeBatch) obj;
		if (batch_id == null) {
			if (other.batch_id != null)
				return false;
		} else if (!batch_id.equals(other.batch_id))
			return false;
		if (trainee_id == null) {
			if (other.trainee_id != null)
				return false;
		} else if (!trainee_id.equals(other.trainee_id))
			return false;
		return true;
	}
	
	
}
