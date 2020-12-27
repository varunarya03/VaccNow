package com.xebia.vaccnow.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "vaccine")
public class Vaccine implements Serializable {
	
	
	@Id
    @Column(name = "vaccine_id")
    private Integer id;
    @Column(name = "vaccine_name")
    private String vaccineName;

    private Short active;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "modified_at")
    private Date modifiedAt;
    @Column(name = "modified_by")
    private Integer modifiedBy;

    public Vaccine() {
    }

    public Vaccine(Integer id, String vaccineName, Short active, Date createdAt, Integer createdBy, Date modifiedAt, Integer modifiedBy) {
        this.id = id;
        this.vaccineName = vaccineName;
        this.active = active;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public Vaccine(Integer vaccineId) {
		this.id = vaccineId;
	}

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

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
   
}
