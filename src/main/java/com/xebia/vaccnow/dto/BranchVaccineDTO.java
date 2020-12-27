package com.xebia.vaccnow.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xebia.vaccnow.models.Branch;
import com.xebia.vaccnow.models.Vaccine;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchVaccineDTO implements Serializable {

	private Integer id;
	private Branch branch;
	private Vaccine vaccine;
	private Integer vaccineCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Vaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(Vaccine vaccine) {
		this.vaccine = vaccine;
	}

	public Integer getVaccineCount() {
		return vaccineCount;
	}

	public void setVaccineCount(Integer vaccineCount) {
		this.vaccineCount = vaccineCount;
	}

	@Override
	public String toString() {
		return "BranchVaccineDTO [id=" + id + ", branch=" + branch + ", vaccine=" + vaccine + ", vaccineCount="
				+ vaccineCount + "]";
	}

}
