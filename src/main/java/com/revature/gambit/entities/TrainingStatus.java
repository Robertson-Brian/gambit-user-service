package com.revature.gambit.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TrainingStatus implements Serializable{
	@JsonProperty("Scheduled")
	Scheduled,
	@JsonProperty("Signed")
	Signed,
	@JsonProperty("Selected")
	Selected,
	@JsonProperty("Training")
	Training,
	@JsonProperty("Marketing")
	Marketing,
	@JsonProperty("Confirmed")
	Confirmed,
	@JsonProperty("Employed")
	Employed,
	@JsonProperty("Dropped")
	Dropped
}
