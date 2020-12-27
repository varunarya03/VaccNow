package com.xebia.vaccnow.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccineDTO implements Serializable {

	private Integer id;
	private String vaccineName;
	private Integer vaccineCount;
	private Date sloatDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public Integer getVaccineCount() {
		return vaccineCount;
	}

	public void setVaccineCount(Integer vaccineCount) {
		this.vaccineCount = vaccineCount;
	}

	public Date getSloatDate() {
		return sloatDate;
	}

	public void setSloatDate(Date sloatDate) {
		this.sloatDate = sloatDate;
	}

}
