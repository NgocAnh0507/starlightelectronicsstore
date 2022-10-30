package com.greenvn.starlightelectronicsstore.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.greenvn.starlightelectronicsstore.entities.Employee;

public class EmployeeInfo {
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@NotBlank(message = "Email không được để trống!")
	private String email;
	@NotBlank(message = "Số điện thoại không được để trống!")
	private String phoneNumber;
	
	
	
	public EmployeeInfo() {
		super();
	}
	public EmployeeInfo(Employee emp) {
		super();
		this.name = emp.getName();
		this.birthday = emp.getBirthday();
		this.email = emp.getEmail();
		this.phoneNumber = emp.getPhoneNumber();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
