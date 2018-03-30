package com.revature.hydra.entities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class TrainerDTO {

	private LocalDateTime time;

	private TrainerUser tu;

	private String sender;

	public TrainerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainerDTO(TrainerUser tu) {
		super();
		this.time = LocalDateTime.now();
		this.tu = tu;
		try {
			this.sender = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public TrainerUser getTu() {
		return tu;
	}

	public void setTu(TrainerUser tu) {
		this.tu = tu;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
