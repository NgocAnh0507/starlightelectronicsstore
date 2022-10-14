package com.greenvn.starlightelectronicsstore.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class Person {


	@Column(name = "NAME",columnDefinition = "VARCHAR(155)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;
	
	@Column(name = "BIRTHDAY",columnDefinition = "DATETIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull(message = "Ngày sinh không được để trống!")
	private Date birthday;

	@Column(name = "PHONE_NUMBER",columnDefinition = "VARCHAR(15)")
	@NotBlank(message = "Số điện thoại không được để trống!")
	private String phoneNumber;

	@Column(name = "EMAIL",columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không hợp lệ!")
	private String email;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}