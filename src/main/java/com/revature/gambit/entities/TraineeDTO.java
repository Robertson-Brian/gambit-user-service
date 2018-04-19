package com.revature.gambit.entities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * This object contains the trainee object, as well as some metadata for the
 * purpose of sending Kafka messages.
 */
public class TraineeDTO {

	/**
	 * The time the message was created
	 */
	private Timestamp time;

	/**
	 * The trainee to be sent over the queue
	 */
	private Trainee payload;

	/**
	 * The service that originated the request
	 */
	private String sender;

	private String requestType;

	public TraineeDTO() {
		super();
	}

	public TraineeDTO(Trainee t, String type) {
		super();
		this.time = new Timestamp(new Date().getTime());
		this.payload = t;
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
		this.payload = t;
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
		return payload;
	}

	public void setTrainee(Trainee t) {
		this.payload = t;
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
		return "TraineeDTO [time=" + time + ", trainee=" + payload + ", sender=" + sender + "]";
	}

}