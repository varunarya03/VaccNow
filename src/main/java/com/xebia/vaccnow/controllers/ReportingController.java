package com.xebia.vaccnow.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.services.BranchVaccineService;
import com.xebia.vaccnow.utils.GlobalConstants;

@RestController
@RequestMapping(value = ("/reporting"))
public class ReportingController {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private BranchVaccineService branchVaccineService;

	@GetMapping(value = "/applied-vaccination-per-branch/{branchId}")
	public ResponseEntity<Map<String, BranchDTO>> appliedVaccinationPerBranch(
			@PathVariable Integer branchId) {
		log.info("scheduleVaccinationTimeslot called");
		return ResponseEntity.ok(branchVaccineService.getScheduleVaccinationByBranch(branchId));
	}
	
	@GetMapping(value = {"/applied-vaccination-per-day","/applied-vaccination-per-day/{start}/{end}"})
	public ResponseEntity<Map<String, BranchDTO>> appliedVaccinationPerBranch(
			@PathVariable(required = false) String start,
			@PathVariable(required = false) String end) {
		log.info("scheduleVaccinationTimeslot called");
		LocalDate startDate, endDate;
		if(start==null || end==null) {
			startDate = LocalDate.now();
			endDate = LocalDate.now().plusDays(1);
		}else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.YYYY_MM_DD);
			startDate = LocalDate.parse(start, formatter);
			endDate = LocalDate.parse(end, formatter).plusDays(1);
		}
		return ResponseEntity.ok(branchVaccineService.getScheduleVaccinationPerDay(startDate,endDate));
	}
	
	@GetMapping(value = {"/confirmed-vaccinations/{start}/{end}"})
	public ResponseEntity<Map<String, BranchDTO>> confirmedVaccinations(
			@PathVariable String start,
			@PathVariable String end) {
		log.info("confirmedVaccinations called");
		LocalDate startDate, endDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.YYYY_MM_DD);
		startDate = LocalDate.parse(start, formatter);
		endDate = LocalDate.parse(end, formatter).plusDays(1);
		return ResponseEntity.ok(branchVaccineService.confirmedVaccinations(startDate,endDate));
	}

}
