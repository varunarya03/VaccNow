package com.xebia.vaccnow.dto;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchDTO implements Serializable {

	private Integer id;
	private String branchName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
	private Time timeFrom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    private Time timeTo;
	private List<VaccineDTO> vaccines;
	private List<String> slots;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<VaccineDTO> getVaccines() {
		return vaccines;
	}

	public void setVaccines(List<VaccineDTO> vaccines) {
		this.vaccines = vaccines;
	}

	public Time getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Time timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Time getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Time timeTo) {
		this.timeTo = timeTo;
	}

	public List<String> getSlots() {
		return slots;
	}

	public void setSlots(List<String> slots) {
		this.slots = slots;
	}

	@Override
	public String toString() {
		return "BranchDTO [id=" + id + ", branchName=" + branchName + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo
				+ ", vaccines=" + vaccines + ", slots=" + slots + "]";
	}
	
}
