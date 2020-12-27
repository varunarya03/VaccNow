package com.xebia.vaccnow.services;

import java.time.LocalDate;
import java.util.Map;

import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.dto.ScheduleTimeslotDTO;

public interface BranchVaccineService {

	Map<String, BranchDTO> listAllVaccines(Integer branchId);

	String scheduleVaccinationTimeslot(ScheduleTimeslotDTO scheduleTimeslotDTO);

	boolean slotAvailable(ScheduleTimeslotDTO scheduleTimeslotDTO);

	Map<String, BranchDTO> getScheduleVaccinationByBranch(Integer branchId);

	Map<String, BranchDTO> getScheduleVaccinationPerDay(LocalDate start, LocalDate end);

	Map<String, BranchDTO> confirmedVaccinations(LocalDate start, LocalDate end);

	String confirmVaccination(String email);

}
