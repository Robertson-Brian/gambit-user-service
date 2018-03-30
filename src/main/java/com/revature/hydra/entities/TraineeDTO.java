package com.revature.hydra.entities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class TraineeDTO {

	private LocalDateTime time;

	private Trainee t;

	private String sender;

	public TraineeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TraineeDTO(Trainee t) {
		super();
		this.time = LocalDateTime.now();
		this.t = t;
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

	public Trainee getT() {
		return t;
	}

	public void setT(Trainee t) {
		this.t = t;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "TraineeDTO [time=" + time + ", t=" + t + ", sender=" + sender + "]";
	}

}
