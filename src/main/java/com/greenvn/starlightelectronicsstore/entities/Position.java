package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPositionID() {
		return positionID;
	}

	public void setPositionID(long positionID) {
		this.positionID = positionID;
	}
	
}
