package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Person{

	@Id
	@Column(name = "EMPLOYEE_ID", columnDefinition = "VARCHAR(155)")
	@GeneratedValue
	private Long employeeID;

	@Column(name = "USER_NAME", columnDefinition = "VARCHAR(55) UNIQUE")
	@NotBlank(message = "Thiếu Username rồi")
	private String userName;
	
	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Thiếu Password rồi")
	private String passWord;

	@Column(name = "IS_ACTIVE", columnDefinition = "BOOLEAN")
	private Boolean isActive = false;
	
	@ManyToOne
	@NotBlank(message = "Chức vụ không được để trống!")
	private Position position;

	public Long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return passWord;
	}

	public void setPassword(String password) {
		this.passWord = password;
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
	
}