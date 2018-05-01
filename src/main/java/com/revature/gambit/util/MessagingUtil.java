package com.revature.gambit.util;


public class MessagingUtil {
	public static final String TOPIC_REGISTER_TRAINER = "trainer.register.t";
	public static final String TOPIC_UPDATE_TRAINER = "trainer.update.t";
	public static final String TOPIC_DELETE_TRAINER = "trainer.delete.t";
	public static final String TOPIC_REGISTER_TRAINEE = "trainee.register.t";
	public static final String TOPIC_UPDATE_TRAINEE = "trainee.update.t";
	public static final String TOPIC_DELETE_TRAINEE = "trainee.delete.t";
	public static final String TOPIC_PROMOTE_USER_TO_TRAINER = "trainer.promote.t";
	public static final String TOPIC_REGISTER_USER = "user.register.t";
	public static final String TOPIC_UPDATE_USER = "user.update.t";
	public static final String TOPIC_DELETE_USER = "user.delete.t";
	
	private MessagingUtil(){
		throw new IllegalStateException("Utility class");
	}
}
