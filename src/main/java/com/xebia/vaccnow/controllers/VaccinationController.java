package com.xebia.vaccnow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.vaccnow.dto.ScheduleTimeslotDTO;
import com.xebia.vaccnow.services.BranchVaccineService;
import com.xebia.vaccnow.utils.GlobalConstants;

@RestController
@RequestMapping(value = ("/vaccination"))
public class VaccinationController {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private BranchVaccineService branchVaccineService;

	@PostMapping(value = "/schedule-vaccination-timeslot")
	public ResponseEntity<String> scheduleVaccinationTimeslot(@RequestBody ScheduleTimeslotDTO scheduleTimeslotDTO) {
		log.info("scheduleVaccinationTimeslot called");
		if(branchVaccineService.slotAvailable(scheduleTimeslotDTO)) { // We can have multiple validation also 			
			return ResponseEntity.ok(branchVaccineService.scheduleVaccinationTimeslot(scheduleTimeslotDTO));
		}else {
			return ResponseEntity.status(HttpStatus.IM_USED).body("Sloat Already Booked. Please try another sloat.");
		}
	}
	
	@PutMapping(value = "/confirm-vaccination/{email}")
	public ResponseEntity<String> confirmVaccinationTimeslot(
			@PathVariable String email) {
		log.info("scheduleVaccinationTimeslot called");
		if(GlobalConstants.UPDATED.equals(branchVaccineService.confirmVaccination(email))) {
			return ResponseEntity.ok(GlobalConstants.UPDATED);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
