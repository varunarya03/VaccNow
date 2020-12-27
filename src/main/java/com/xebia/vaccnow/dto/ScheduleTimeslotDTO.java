package com.xebia.vaccnow.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleTimeslotDTO implements Serializable {

	private Integer id;
	private Integer branchId;
	private Integer vaccineId;
	private Date slotDate;
	private String email;
	private short vaccinactionDone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(Integer vaccineId) {
		this.vaccineId = vaccineId;
	}

	public Date getSlotDate() {
		return slotDate;
	}

	public void setSlotDate(Date slotDate) {
		this.slotDate = slotDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public short getVaccinactionDone() {
		return vaccinactionDone;
	}

	public void setVaccinactionDone(short vaccinactionDone) {
		this.vaccinactionDone = vaccinactionDone;
	}

}
