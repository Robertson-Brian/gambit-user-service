package com.revature.gambit.util;

/**
 * This class contains currently used topics within the user-service,
 * including the internal ones, which are used to send messages to the Kafka queue
 * and the external ones, which are the ones that the service is subscribing to.
 * 
 * This class can be improved by getting the values of the topic from the configuration
 * properties, which are available as of now in the Configuration Server.
 * 
 * @author Mark Fleres
 * @author Jeffrey Reyes
 *
 */
public class MessagingUtil {
	//Internal Topics
	public static final String TOPIC_REGISTER_TRAINER = "trainer.register.t";
	public static final String TOPIC_UPDATE_TRAINER = "trainer.update.t";
	public static final String TOPIC_DELETE_TRAINER = "trainer.delete.t";
	public static final String TOPIC_REGISTER_TRAINEE = "trainee.register.t";
	public static final String TOPIC_UPDATE_TRAINEE = "trainee.update.t";
	public static final String TOPIC_DELETE_TRAINEE = "trainee.delete.t";
	public static final String TOPIC_PROMOTE_USER = "user.promote.t";
	public static final String TOPIC_REGISTER_USER = "user.register.t";
	public static final String TOPIC_UPDATE_USER = "user.update.t";
	public static final String TOPIC_DELETE_USER = "user.delete.t";
	
	//External Topics
	public static final String TOPIC_DELETE_BATCH = "batch.delete.t";

	private MessagingUtil() {
		throw new IllegalStateException("Utility class");
	}
}
