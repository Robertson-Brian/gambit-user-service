package com.revature.gambit.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Trainer Entity
 */
@Entity
@Table(name = "TRAINER")
@Cacheable
public class Trainer {
	
	@Id
	@SequenceGenerator(name = "TRAINER_ID_SEQ", sequenceName = "TRAINER_ID_SEQ")
	@GeneratedValue(generator = "TRAINER_ID_SEQ", strategy = GenerationType.AUTO)
	private Integer trainerId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@NotEmpty
	private String title;

	public Trainer() {
		super();
	}

	public Trainer(Integer trainerId, Integer userId, String title) {
		super();
		this.trainerId = trainerId;
		this.userId = userId;
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((trainerId == null) ? 0 : trainerId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Trainer other = (Trainer) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (trainerId == null) {
			if (other.trainerId != null)
				return false;
		} else if (!trainerId.equals(other.trainerId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainer [trainerId=" + trainerId + ", userId=" + userId + ", title=" + title + "]";
	}

	public Integer getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}