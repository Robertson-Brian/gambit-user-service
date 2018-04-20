package com.revature.gambit.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Trainer Entity
 */
@Entity
@Table(name = "TRAINER")
@Cacheable
public class Trainer extends User {

	@NotEmpty
	@Column(name = "TITLE")
	private String title;

	public Trainer() {
		super();
	}
	
	public Trainer(String firstName, String lastName, String email, String title){
		super( firstName,lastName,email);
		this.title = title;
		
	}
	
	public Trainer(User user, String title) {
		this.setFirstName(user.getFirstName());
		this.setMiddleName(user.getMiddleName());
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setHomePhone(user.getHomePhone());
		this.setMobilePhone(user.getMobilePhone());
		this.setRole(user.getRole());
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainer other = (Trainer) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainer [trainerId= " + getUserId() + ", title=" + title + "]";
	}

}
