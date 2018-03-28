package com.revature.hydra.entities;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Trainee
 */
@Entity
@Table(name = "Trainee")
public class Trainee implements Serializable {
	private static final long serialVersionUID = -9090223980655307018L;

	@Id
	@Column(name = "TRAINEE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINEE_ID_SEQUENCE")
	@SequenceGenerator(name = "TRAINEE_ID_SEQUENCE", sequenceName = "TRAINEE_ID_SEQUENCE")
	private int traineeId;

	@Column(name = "RESOURCE_ID")
	private int resourceId;

	@NotNull
	@Column(name = "TRAINING_STATUS")
	private String trainingStatus;

	@Embedded
	@AttributeOverrides(value = { @AttributeOverride(name = "batchId", column = @Column(name = "BATCH_ID")) })
	private Batch batch;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "SKYPE_ID")
	private String skypeId;

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

	@Column(name = "FLAG_STATUS")
	private String flagStatus;

	@Length(min = 0, max = 4000)
	@Column(name = "FLAG_NOTES", length = 4000)
	private String flagNotes;

	@JsonIgnore
	@Column(name = "GRADES")
	private String grades;

	@JsonIgnore
	@Column(name = "NOTES")
	private String notes;

	@JsonIgnore
	@OrderBy(value = "INTERVIEW_DATE DESC")
	private String panelInterviews;

	@Column(name = "MARKETING_STATUS")
	private String marketingStatus;

	@Column(name = "CLIENT")
	private String client;

	@Column(name = "END_CLIENT")
	private String endClient;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User traineeUserInfo;

	public Trainee() {
		super();
	}

	public Trainee(int traineeId, int resourceId, String trainingStatus, Batch batch, String phoneNumber,
			String skypeId, String profileUrl, String recruiterName, String college, String degree, String major,
			String techScreenerName, String projectCompletion, String flagStatus, String flagNotes, String grades,
			String notes, String panelInterviews, String marketingStatus, String client, String endClient,
			User traineeUserInfo) {
		this.traineeId = traineeId;
		this.resourceId = resourceId;
		this.trainingStatus = trainingStatus;
		this.batch = batch;
		this.phoneNumber = phoneNumber;
		this.skypeId = skypeId;
		this.profileUrl = profileUrl;
		this.recruiterName = recruiterName;
		this.college = college;
		this.degree = degree;
		this.major = major;
		this.techScreenerName = techScreenerName;
		this.projectCompletion = projectCompletion;
		this.flagStatus = flagStatus;
		this.flagNotes = flagNotes;
		this.grades = grades;
		this.notes = notes;
		this.panelInterviews = panelInterviews;
		this.marketingStatus = marketingStatus;
		this.client = client;
		this.endClient = endClient;
		this.traineeUserInfo = traineeUserInfo;
	}

	public int getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
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

	public String getFlagStatus() {
		return flagStatus;
	}

	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}

	public String getFlagNotes() {
		return flagNotes;
	}

	public void setFlagNotes(String flagNotes) {
		this.flagNotes = flagNotes;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPanelInterviews() {
		return panelInterviews;
	}

	public void setPanelInterviews(String panelInterviews) {
		this.panelInterviews = panelInterviews;
	}

	public String getMarketingStatus() {
		return marketingStatus;
	}

	public void setMarketingStatus(String marketingStatus) {
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

	public User getTraineeUserInfo() {
		return traineeUserInfo;
	}

	public void setTraineeUserInfo(User traineeUserInfo) {
		this.traineeUserInfo = traineeUserInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batch == null) ? 0 : batch.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((college == null) ? 0 : college.hashCode());
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((endClient == null) ? 0 : endClient.hashCode());
		result = prime * result + ((flagNotes == null) ? 0 : flagNotes.hashCode());
		result = prime * result + ((flagStatus == null) ? 0 : flagStatus.hashCode());
		result = prime * result + ((grades == null) ? 0 : grades.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((marketingStatus == null) ? 0 : marketingStatus.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((panelInterviews == null) ? 0 : panelInterviews.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((profileUrl == null) ? 0 : profileUrl.hashCode());
		result = prime * result + ((projectCompletion == null) ? 0 : projectCompletion.hashCode());
		result = prime * result + ((recruiterName == null) ? 0 : recruiterName.hashCode());
		result = prime * result + resourceId;
		result = prime * result + ((skypeId == null) ? 0 : skypeId.hashCode());
		result = prime * result + ((techScreenerName == null) ? 0 : techScreenerName.hashCode());
		result = prime * result + traineeId;
		result = prime * result + ((traineeUserInfo == null) ? 0 : traineeUserInfo.hashCode());
		result = prime * result + ((trainingStatus == null) ? 0 : trainingStatus.hashCode());
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
		Trainee other = (Trainee) obj;
		if (batch == null) {
			if (other.batch != null)
				return false;
		} else if (!batch.equals(other.batch))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (college == null) {
			if (other.college != null)
				return false;
		} else if (!college.equals(other.college))
			return false;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (endClient == null) {
			if (other.endClient != null)
				return false;
		} else if (!endClient.equals(other.endClient))
			return false;
		if (flagNotes == null) {
			if (other.flagNotes != null)
				return false;
		} else if (!flagNotes.equals(other.flagNotes))
			return false;
		if (flagStatus == null) {
			if (other.flagStatus != null)
				return false;
		} else if (!flagStatus.equals(other.flagStatus))
			return false;
		if (grades == null) {
			if (other.grades != null)
				return false;
		} else if (!grades.equals(other.grades))
			return false;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (marketingStatus == null) {
			if (other.marketingStatus != null)
				return false;
		} else if (!marketingStatus.equals(other.marketingStatus))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (panelInterviews == null) {
			if (other.panelInterviews != null)
				return false;
		} else if (!panelInterviews.equals(other.panelInterviews))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (profileUrl == null) {
			if (other.profileUrl != null)
				return false;
		} else if (!profileUrl.equals(other.profileUrl))
			return false;
		if (projectCompletion == null) {
			if (other.projectCompletion != null)
				return false;
		} else if (!projectCompletion.equals(other.projectCompletion))
			return false;
		if (recruiterName == null) {
			if (other.recruiterName != null)
				return false;
		} else if (!recruiterName.equals(other.recruiterName))
			return false;
		if (resourceId != other.resourceId)
			return false;
		if (skypeId == null) {
			if (other.skypeId != null)
				return false;
		} else if (!skypeId.equals(other.skypeId))
			return false;
		if (techScreenerName == null) {
			if (other.techScreenerName != null)
				return false;
		} else if (!techScreenerName.equals(other.techScreenerName))
			return false;
		if (traineeId != other.traineeId)
			return false;
		if (traineeUserInfo == null) {
			if (other.traineeUserInfo != null)
				return false;
		} else if (!traineeUserInfo.equals(other.traineeUserInfo))
			return false;
		if (trainingStatus == null) {
			if (other.trainingStatus != null)
				return false;
		} else if (!trainingStatus.equals(other.trainingStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainee [traineeId=" + traineeId + ", resourceId=" + resourceId + ", trainingStatus=" + trainingStatus
				+ ", batch=" + batch + ", phoneNumber=" + phoneNumber + ", skypeId=" + skypeId + ", profileUrl="
				+ profileUrl + ", recruiterName=" + recruiterName + ", college=" + college + ", degree=" + degree
				+ ", major=" + major + ", techScreenerName=" + techScreenerName + ", projectCompletion="
				+ projectCompletion + ", flagStatus=" + flagStatus + ", flagNotes=" + flagNotes + ", grades=" + grades
				+ ", notes=" + notes + ", panelInterviews=" + panelInterviews + ", marketingStatus=" + marketingStatus
				+ ", client=" + client + ", endClient=" + endClient + ", traineeUserInfo=" + traineeUserInfo + "]";
	}
}
