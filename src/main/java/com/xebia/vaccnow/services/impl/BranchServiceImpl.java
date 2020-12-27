package com.xebia.vaccnow.services.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.models.Branch;
import com.xebia.vaccnow.repositories.BranchRepository;
import com.xebia.vaccnow.services.BranchService;
import com.xebia.vaccnow.utils.DozerHelper;
import com.xebia.vaccnow.utils.GlobalConstants;

@Service
public class BranchServiceImpl implements BranchService{
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private DozerBeanMapper dozerBeanMapper;
	
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Branch save(Branch branch){
    	log.info("save called");
        return branchRepository.save(branch);
    }

	@Override
	public List<BranchDTO> listAllBranches() {
		log.info("listAllBranches called");
		List<Branch> branches = branchRepository.findByActive((short)1);
		List<BranchDTO> branchesDtos = DozerHelper.map(dozerBeanMapper, branches, BranchDTO.class);
		return branchesDtos;
	}

	@Override
	public BranchDTO availableBranchTime(Integer branchId) {
		log.info("availableBranchTime called for branchId :: "+branchId);
		Optional<Branch> branch = branchRepository.findById(branchId);
		BranchDTO branchDto = null;
		if(branch.isPresent()) {
			List<String> slots = new ArrayList<>();
			branchDto = dozerBeanMapper.map(branch.get(), BranchDTO.class);
			LocalTime from = branchDto.getTimeFrom().toLocalTime();
			LocalTime to = branchDto.getTimeTo().toLocalTime();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(GlobalConstants.YYYY_MM_DD_HH_MM);
			while(from.isBefore(to.plusMinutes(1))) {
				slots.add(LocalDateTime.now().withHour(from.getHour()).withMinute(from.getMinute()).format(dateTimeFormatter));
				from = from.plusMinutes(GlobalConstants.MINUTES_INTERVAL);
			}
			branchDto.setSlots(slots);
		}
		return branchDto;
	}
	
}
