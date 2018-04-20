package com.revature.gambit.entities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * This object contains the trainer object, as well as
 * some metadata for the purpose of sending rabbitmq messages.
 */
public class TrainerDTO {

	private Timestamp time;

	private Trainer trainer;

	private String sender;

	private String requestType;

	public TrainerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainerDTO(Trainer trainer, String Type) {
		super();
		this.time = new Timestamp(new Date().getTime());
		this.trainer = trainer;
		this.requestType = Type;
		try {
			this.sender = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public TrainerDTO(Timestamp time, Trainer trainer, String sender, String requestType) {
		super();
		this.time = time;
		this.trainer = trainer;
		this.sender = sender;
		this.requestType = requestType;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Trainer getTrainerUser() {
		return trainer;
	}

	public void setTrainerUser(Trainer trainer) {
		this.trainer = trainer;
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

}
