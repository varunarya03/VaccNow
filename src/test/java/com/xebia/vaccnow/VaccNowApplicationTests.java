package com.xebia.vaccnow;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.vaccnow.controllers.AvailabilityController;
import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.models.Branch;
import com.xebia.vaccnow.repositories.BranchVaccineRepository;
import com.xebia.vaccnow.services.BranchService;
import com.xebia.vaccnow.services.BranchVaccineService;
import com.xebia.vaccnow.utils.DozerHelper;

//@SpringBootTest
@WebMvcTest(AvailabilityController.class)
@ActiveProfiles("test")
class VaccNowApplicationTests {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper mapper;
	@MockBean BranchService branchService;
	@MockBean BranchVaccineRepository branchVaccineRepository;
	@MockBean BranchVaccineService branchVaccineService;

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	
	// Sample test case for first API call
	@Test
	public void testListAllBranches() throws Exception {
		
		String expectedResult = "[{\"id\":201,\"branchName\":\"Delhi\",\"timeFrom\":\"10:00:00\",\"timeTo\":\"17:00:00\"},{\"id\":202,\"branchName\":\"Gurgaon\",\"timeFrom\":\"09:00:00\",\"timeTo\":\"18:00:00\"}]";
		
		List<Branch> branchList = new ArrayList<>();
		Branch branchA = new Branch(201, "Delhi", Time.valueOf("10:00:00"), Time.valueOf("17:00:00"), (short) 1,
				Calendar.getInstance().getTime(), 0, Calendar.getInstance().getTime(), 0);
		Branch branchB = new Branch(202, "Gurgaon", Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), (short) 1,
				Calendar.getInstance().getTime(), 0, Calendar.getInstance().getTime(), 0);
		branchList.add(branchA);
		branchList.add(branchB);
		List<BranchDTO> branchesListDto = DozerHelper.map(dozerBeanMapper, branchList, BranchDTO.class);
		
		Mockito.when(branchService.listAllBranches()).thenReturn(branchesListDto);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/availability/list-branches").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(MockMvcResultMatchers.content().string(expectedResult))
						;
	}
}
