package com.revature.gambit.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Trainee
 */
@Entity
@Table(name = "TRAINEE")
public class Trainee extends User {

	@JsonIgnore
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Integer> batches;
	
	@Column(name = "RESOURCE_ID")
	private String resourceId;

	@Enumerated(EnumType.STRING)
	@Column(name = "TRAINING_STATUS")
	private TrainingStatus trainingStatus;

	@Column(name = "PROFILE_URL")
	private String profileUrl;

	@Column(name = "RECRUITER_NAME")
	private String recruiterName;

	@Column(name = "COLLEGE")
	private String college;

	@Column(name = "DEGREE")
	private String degree;

	@Column(name = "MAJOR")
	private String major;

	@Column(name = "TECH_SCREENER")
	private String techScreenerName;

	@Column(name = "PROJECT_COMPLETION")
	private String projectCompletion;

	@Enumerated(EnumType.STRING)
	@Column(name = "FLAG_STATUS")
	private TraineeFlag flagStatus;

	@Length(min = 0, max = 4000)
	@Column(name = "FLAG_NOTES", length = 4000)
	private String flagNotes;

	@Column(name = "MARKETING_STATUS")
	private Integer marketingStatus;

	@Column(name = "CLIENT")
	private String client;

	@Column(name = "END_CLIENT")
	private String endClient;

	public Trainee() {
		super();
		batches = new HashSet<>();
	}
	
	public Trainee(String firstName, String lastName, String email) {
		this();
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
	}
	
	public Trainee(String firstName, String lastName, String email, String resourceId, TrainingStatus trainingStatus, String recruiterName) {
		this(firstName, lastName, email);
		this.resourceId = resourceId;
		this.trainingStatus = trainingStatus;
		this.recruiterName = recruiterName;
	}

	public Trainee(String firstName, String lastName, String email, String resourceId, TrainingStatus trainingStatus, Integer marketingStatus, String client) {
		this(firstName, lastName, email);
		this.resourceId = resourceId;
		this.trainingStatus = trainingStatus;
		this.marketingStatus = marketingStatus;
		this.client = client;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public TrainingStatus getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(TrainingStatus trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	public Set<Integer> getBatches() {
		return batches;
	}

	@JsonCreator
	public void setBatches(Set<Integer> batches) {
		this.batches = batches;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getRecruiterName() {
		return recruiterName;
	}

	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getTechScreenerName() {
		return techScreenerName;
	}

	public void setTechScreenerName(String techScreenerName) {
		this.techScreenerName = techScreenerName;
	}

	public String getProjectCompletion() {
		return projectCompletion;
	}

	public void setProjectCompletion(String projectCompletion) {
		this.projectCompletion = projectCompletion;
	}

	public TraineeFlag getFlagStatus() {
		return flagStatus;
	}

	public void setFlagStatus(TraineeFlag flagStatus) {
		this.flagStatus = flagStatus;
	}

	public String getFlagNotes() {
		return flagNotes;
	}

	public void setFlagNotes(String flagNotes) {
		this.flagNotes = flagNotes;
	}

	public Integer getMarketingStatus() {
		return marketingStatus;
	}

	public void setMarketingStatus(Integer marketingStatus) {
		this.marketingStatus = marketingStatus;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getEndClient() {
		return endClient;
	}

	public void setEndClient(String endClient) {
		this.endClient = endClient;
	}

	@Override
	public String toString() {
		return "Trainee [traineeId=" + getUserId() + ", batches=" + batches + ", resourceId=" + resourceId
				+ ", trainingStatus=" + trainingStatus + ", profileUrl=" + profileUrl + ", recruiterName="
				+ recruiterName + ", college=" + college + ", degree=" + degree + ", major=" + major
				+ ", techScreenerName=" + techScreenerName + ", projectCompletion=" + projectCompletion
				+ ", flagStatus=" + flagStatus + ", flagNotes=" + flagNotes + ", marketingStatus=" + marketingStatus
				+ ", client=" + client + ", endClient=" + endClient + "]";
	}

}
