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

	private TrainerUser trainerUser;

	private String sender;

	private String requestType;

	public TrainerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainerDTO(TrainerUser tu, String Type) {
		super();
		this.time = new Timestamp(new Date().getTime());
		this.trainerUser = tu;
		this.requestType = Type;
		try {
			this.sender = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public TrainerDTO(Timestamp time, TrainerUser tu, String sender, String requestType) {
		super();
		this.time = time;
		this.trainerUser = tu;
		this.sender = sender;
		this.requestType = requestType;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public TrainerUser getTrainerUser() {
		return trainerUser;
	}

	public void setTrainerUser(TrainerUser tu) {
		this.trainerUser = tu;
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
