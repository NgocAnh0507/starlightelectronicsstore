package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Person{

	@Id
	@Column(name = "EMPLOYEE_ID", columnDefinition = "VARCHAR(155)")
	private Integer idEmployee;

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

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
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
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	private List<Permission> permissions;

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	
}