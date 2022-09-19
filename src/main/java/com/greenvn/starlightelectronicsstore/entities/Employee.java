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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
}