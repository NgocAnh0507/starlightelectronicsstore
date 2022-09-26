package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "POSITION")
public class Position {

	@Id
	@Column(name = "POSITION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long positionID;
	
	@Column(name = "NAME",columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên chức vụ không được để trống!")
	private String name;
	
	@Column(name = "EDIT_DATA",columnDefinition = "BOOLEAN")
	@NotNull(message = "Quyền chỉnh sủa không được để trống!")
	private Boolean editData = false;

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

	public Boolean getEditData() {
		return editData;
	}

	public void setEditData(Boolean editData) {
		this.editData = editData;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
