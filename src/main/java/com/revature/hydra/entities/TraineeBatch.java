package com.revature.hydra.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
/**
 * This entity represents the Junction table between Trainees and Batches
 * (TrainerUser is NOT a similar combination)
 */
@Table(name = "TRAINEE_BATCH")
@Entity
@IdClass(TraineeBatch.class)
public class TraineeBatch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "trainee_id")
	Integer traineeId;

	@Id
	@Column(name = "batch_id")
	Integer batchId;

	public TraineeBatch() {
		super();
	}

	public TraineeBatch(int traineeId, Integer batch_id) {
		this.traineeId = traineeId;
		this.batchId = batch_id;
	}

	public Integer getTrainee_id() {
		return traineeId;
	}

	public void setTrainee_id(Integer trainee_id) {
		this.traineeId = trainee_id;
	}

	public Integer getBatch_id() {
		return batchId;
	}

	public void setBatch_id(Integer batch_id) {
		this.batchId = batch_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batchId == null) ? 0 : batchId.hashCode());
		result = prime * result + ((traineeId == null) ? 0 : traineeId.hashCode());
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
		if (batchId == null) {
			if (other.batchId != null)
				return false;
		} else if (!batchId.equals(other.batchId))
			return false;
		if (traineeId == null) {
			if (other.traineeId != null)
				return false;
		} else if (!traineeId.equals(other.traineeId))
			return false;
		return true;
	}

}
