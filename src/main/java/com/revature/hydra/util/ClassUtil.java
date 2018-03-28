package com.revature.hydra.util;

import org.springframework.beans.BeanUtils;

import com.revature.hydra.entities.BatchTrainer;
import com.revature.hydra.entities.TrainerUser;
import com.revature.hydra.entities.User;

public class ClassUtil {
	
	public static TrainerUser merge(User u, BatchTrainer bt) {
		TrainerUser tu = new TrainerUser();
		BeanUtils.copyProperties(u, tu);
		tu.setTitle(bt.getTitle());
		tu.setTrainerId(bt.getTrainerId());
		return tu;
		
	}
	
}
