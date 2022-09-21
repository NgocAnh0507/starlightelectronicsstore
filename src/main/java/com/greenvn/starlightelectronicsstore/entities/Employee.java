package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Person{

	@Id
	@Column(name = "EMPLOYEE_ID", columnDefinition = "VARCHAR(155)")
	private Long idEmployee;
	
	@ManyToOne
	@NotBlank(message = "Chức vụ không được để trống!")
	private Position position;

	@Column(name = "USER_NAME", columnDefinition = "VARCHAR(55) UNIQUE")
	@NotBlank(message = "Thiếu Username rồi")
	private String userName;
	
	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Thiếu Password rồi")
	private String password;

	@Column(name = "IS_ACTIVE", length = 1)
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

	public Long getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Long idEmployee) {
		this.idEmployee = idEmployee;
	}
	
}