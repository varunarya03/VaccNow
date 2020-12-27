package com.xebia.vaccnow.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.services.BranchService;
import com.xebia.vaccnow.services.BranchVaccineService;

@RestController
@RequestMapping(value = ("/availability"))
public class AvailabilityController {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private BranchService branchService;
	@Autowired
	private BranchVaccineService branchVaccineService;
	
	
	@GetMapping(value = "/list-branches")
	@Cacheable(value = "list-branches")
	public ResponseEntity<List<BranchDTO>> listAllBranches(){
		log.info("listAllBranches called");
		return ResponseEntity.ok(branchService.listAllBranches());
	}
	
	@GetMapping({"/list-vaccines","/list-vaccines/{branchId}"})
	@Cacheable(value = "list-vaccines")
	public ResponseEntity<Map<String, BranchDTO>> listAllVaccines(
			@PathVariable(required = false) Integer branchId){
		log.info("listAllVaccines called");
		return ResponseEntity.ok(branchVaccineService.listAllVaccines(branchId));
	}
	
	@GetMapping(value = "/available-branch-time/{branchId}")
	@Cacheable(value = "available-branch-time")
	public ResponseEntity<BranchDTO> availableBranchTime(
			@PathVariable Integer branchId){
		log.info("availableBranchTime called");
		return ResponseEntity.ok(branchService.availableBranchTime(branchId));
	}

}
