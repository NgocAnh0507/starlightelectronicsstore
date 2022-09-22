package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "POSITION")
public class Position {

	@Id
	@Column(name = "POSITION_ID")
	@GeneratedValue
	private long positionID;
	
	@Column(name = "NAME",columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên chức vụ không được để trống!")
	private String name;

	@OneToMany(mappedBy = "position")
	private List<Employee> employees;
	
	public long getPositionID() {
		return positionID;
	}

	public void setPositionID(long positionID) {
		this.positionID = positionID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
