package com.revature.gambit.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Batch.
 */
@Embeddable
public class Batch implements Serializable {
	private static final long serialVersionUID = -5755409643112884001L;

	@Column(name = "BATCH_ID")
	private int batchId;

	@Transient
	@Column(name = "RESOURCE_ID")
	private String resourceId;

	@Transient
	@NotNull
	@Column(name = "TRAINING_NAME")
	private String trainingName;

	@Transient
	@NotNull
	@JsonProperty
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "TRAINER_ID", nullable = false)
	private TrainerUser trainer;

	@Transient
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CO_TRAINER_ID")
	private TrainerUser coTrainer;

	@Transient
	@NotNull
	@Column(name = "SKILL_TYPE")
	private String skillType;

	@Transient
	@NotNull
	@Column(name = "TRAINING_TYPE")
	private String trainingType;

	@Transient
	@JsonFormat(timezone = "EST", pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", nullable = false)
	private Date startDate;

	@Transient
	@JsonFormat(timezone = "EST", pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", nullable = false)
	private Date endDate;

	@Transient
	@NotEmpty
	@Column(name = "LOCATION", nullable = false)
	private String location;

	@Transient
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ADDRESS_ID")
	private String address;

	/**
	 * Anything above this grade is GREEN
	 */
	@Transient
	@Min(value = 1)
	@Column(name = "GOOD_GRADE_THRESHOLD")
	private short goodGradeThreshold;

	/**
	 * Anything above this grade but below goodGradeThreshold is YELLOW Anything
	 * below this grade is RED
	 */
	@Transient
	@Min(value = 1)
	@Column(name = "BORDERLINE_GRADE_THRESHOLD")
	private short borderlineGradeThreshold;

	@Transient
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "TRAINEE_ID", nullable = false)
	private Set<Trainee> trainees;

	@Transient
	@Column(name = "NUMBER_OF_WEEKS", nullable = false)
	private int weeks;

	@Transient
	@Column(name = "GRADED_WEEKS")
	private int gradedWeeks;

	@Transient
	@JsonIgnore
	@OneToMany(mappedBy = "batch")
	private Set<String> notes;

	public Batch() {
		super();
	}

	public Batch(int batchId, String resourceId, String trainingName, TrainerUser trainer, TrainerUser coTrainer,
			String skillType, String trainingType, Date startDate, Date endDate, String location, String address,
			short goodGradeThreshold, short borderlineGradeThreshold, Set<Trainee> trainees, int weeks, int gradedWeeks,
			Set<String> notes) {
		this.batchId = batchId;
		this.resourceId = resourceId;
		this.trainingName = trainingName;
		this.trainer = trainer;
		this.coTrainer = coTrainer;
		this.skillType = skillType;
		this.trainingType = trainingType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.address = address;
		this.goodGradeThreshold = goodGradeThreshold;
		this.borderlineGradeThreshold = borderlineGradeThreshold;
		this.trainees = trainees;
		this.weeks = weeks;
		this.gradedWeeks = gradedWeeks;
		this.notes = notes;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public TrainerUser getTrainer() {
		return trainer;
	}

	public void setTrainer(TrainerUser trainer) {
		this.trainer = trainer;
	}

	public TrainerUser getCoTrainer() {
		return coTrainer;
	}

	public void setCoTrainer(TrainerUser coTrainer) {
		this.coTrainer = coTrainer;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public short getGoodGradeThreshold() {
		return goodGradeThreshold;
	}

	public void setGoodGradeThreshold(short goodGradeThreshold) {
		this.goodGradeThreshold = goodGradeThreshold;
	}

	public short getBorderlineGradeThreshold() {
		return borderlineGradeThreshold;
	}

	public void setBorderlineGradeThreshold(short borderlineGradeThreshold) {
		this.borderlineGradeThreshold = borderlineGradeThreshold;
	}

	public Set<Trainee> getTrainees() {
		return trainees;
	}

	public void setTrainees(Set<Trainee> trainees) {
		this.trainees = trainees;
	}

	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	public int getGradedWeeks() {
		return gradedWeeks;
	}

	public void setGradedWeeks(int gradedWeeks) {
		this.gradedWeeks = gradedWeeks;
	}

	public Set<String> getNotes() {
		return notes;
	}

	public void setNotes(Set<String> notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + batchId;
		result = prime * result + borderlineGradeThreshold;
		result = prime * result + ((coTrainer == null) ? 0 : coTrainer.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + goodGradeThreshold;
		result = prime * result + gradedWeeks;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		result = prime * result + ((skillType == null) ? 0 : skillType.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((trainees == null) ? 0 : trainees.hashCode());
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
		result = prime * result + ((trainingName == null) ? 0 : trainingName.hashCode());
		result = prime * result + ((trainingType == null) ? 0 : trainingType.hashCode());
		result = prime * result + weeks;
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
		Batch other = (Batch) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (batchId != other.batchId)
			return false;
		if (borderlineGradeThreshold != other.borderlineGradeThreshold)
			return false;
		if (coTrainer == null) {
			if (other.coTrainer != null)
				return false;
		} else if (!coTrainer.equals(other.coTrainer))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (goodGradeThreshold != other.goodGradeThreshold)
			return false;
		if (gradedWeeks != other.gradedWeeks)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		if (skillType == null) {
			if (other.skillType != null)
				return false;
		} else if (!skillType.equals(other.skillType))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (trainees == null) {
			if (other.trainees != null)
				return false;
		} else if (!trainees.equals(other.trainees))
			return false;
		if (trainer == null) {
			if (other.trainer != null)
				return false;
		} else if (!trainer.equals(other.trainer))
			return false;
		if (trainingName == null) {
			if (other.trainingName != null)
				return false;
		} else if (!trainingName.equals(other.trainingName))
			return false;
		if (trainingType == null) {
			if (other.trainingType != null)
				return false;
		} else if (!trainingType.equals(other.trainingType))
			return false;
		if (weeks != other.weeks)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Batch [batchId=" + batchId + ", resourceId=" + resourceId + ", trainingName=" + trainingName
				+ ", trainer=" + trainer + ", coTrainer=" + coTrainer + ", skillType=" + skillType + ", trainingType="
				+ trainingType + ", startDate=" + startDate + ", endDate=" + endDate + ", location=" + location
				+ ", address=" + address + ", goodGradeThreshold=" + goodGradeThreshold + ", borderlineGradeThreshold="
				+ borderlineGradeThreshold + ", trainees=" + trainees + ", weeks=" + weeks + ", gradedWeeks="
				+ gradedWeeks + ", notes=" + notes + "]";
	}
}
