package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Person{

	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long employeeID;

	@Column(name = "USER_NAME", columnDefinition = "VARCHAR(55) UNIQUE")
	@NotBlank(message = "Thiếu Username rồi")
	private String userName;
	
	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(1000)")
	@NotBlank(message = "Thiếu Password rồi")
	private String password;

	@Column(name = "IS_ACTIVE", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isActive;
	
	@ManyToOne
	@NotNull(message = "Chức vụ không được để trống!")
	private Position position;	
	
	@OneToOne
	private Image avatar;

	public long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

}