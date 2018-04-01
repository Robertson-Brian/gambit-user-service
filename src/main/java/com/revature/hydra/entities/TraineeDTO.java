package com.revature.hydra.entities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

public class TraineeDTO {

	private Timestamp time;

	private Trainee trainee;

	private String sender;

	private String requestType;

	public TraineeDTO() {
		super();
	}

	public TraineeDTO(Trainee t, String type) {
		super();
		this.time = new Timestamp(new Date().getTime());
		this.trainee = t;
		this.requestType = type;
		try {
			this.sender = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public TraineeDTO(Timestamp time, Trainee t, String sender, String requestType) {
		super();
		this.time = time;
		this.trainee = t;
		this.sender = sender;
		this.requestType = requestType;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee t) {
		this.trainee = t;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return "TraineeDTO [time=" + time + ", trainee=" + trainee + ", sender=" + sender + "]";
	}

}
