package com.revature.gambit.util;

import org.springframework.beans.BeanUtils;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.TrainerUser;
import com.revature.gambit.entities.User;

public class ClassUtil {
	
	public static TrainerUser merge(User u, Trainer bt) {
		TrainerUser tu = new TrainerUser();
		BeanUtils.copyProperties(u, tu);
		tu.setTitle(bt.getTitle());
		tu.setTrainerId(bt.getTrainerId());
		return tu;
	}
	
}
