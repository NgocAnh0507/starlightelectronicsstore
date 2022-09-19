package com.greenvn.starlightelectronicsstore.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Person{

	@ManyToOne
	@NotBlank(message = "Chức vụ không được để trống!")
	private Position position;
	
	private String userName;
	private String password;
	private Boolean isActive = false;
	
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}