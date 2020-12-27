package com.xebia.vaccnow.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.dto.ScheduleTimeslotDTO;
import com.xebia.vaccnow.dto.VaccineDTO;
import com.xebia.vaccnow.models.BranchVaccine;
import com.xebia.vaccnow.models.ScheduleTimeslot;
import com.xebia.vaccnow.repositories.BranchRepository;
import com.xebia.vaccnow.repositories.BranchVaccineRepository;
import com.xebia.vaccnow.repositories.ScheduleTimeslotRepository;
import com.xebia.vaccnow.repositories.VaccineRepository;
import com.xebia.vaccnow.services.BranchVaccineService;
import com.xebia.vaccnow.utils.EmailServiceUtil;
import com.xebia.vaccnow.utils.GlobalConstants;
import com.xebia.vaccnow.utils.TransactionStatus;

@Service
public class BranchVaccineServiceImpl implements BranchVaccineService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private DozerBeanMapper dozerBeanMapper;
	@Autowired
	private BranchVaccineRepository branchVaccineRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private VaccineRepository vaccineRepository;
	@Autowired
	private ScheduleTimeslotRepository scheduleTimeslotRepository;
	@Autowired
	private EmailServiceUtil emailServiceUtil;

	@Override
	public Map<String, BranchDTO> listAllVaccines(Integer branchId) {
		log.info("listAllVaccines called");
		List<BranchVaccine> branchVaccines = null;
		if (branchId == null) {
			branchVaccines = (List<BranchVaccine>) branchVaccineRepository.findAllByActive((short) 1);
		} else {
			branchVaccines = (List<BranchVaccine>) branchVaccineRepository.findByBranch(branchId);
		}
		Map<String, BranchDTO> branchVaccinesDtos = new HashMap<>();
		branchVaccines.forEach(preapareVaccineData(branchVaccinesDtos));
		return branchVaccinesDtos;
	}

	private Consumer<BranchVaccine> preapareVaccineData(Map<String, BranchDTO> branchVaccinesDtos) {
		return new Consumer<BranchVaccine>() {

			@Override
			public void accept(BranchVaccine t) {
				BranchDTO branchDTO = dozerBeanMapper.map(t.getBranch(), BranchDTO.class);
				VaccineDTO vaccineDTO = dozerBeanMapper.map(t.getVaccine(), VaccineDTO.class);
				vaccineDTO.setVaccineCount(t.getVaccineCount());
				if (!branchVaccinesDtos.containsKey(branchDTO.getBranchName())) {
					List<VaccineDTO> vaccineDTOs = new ArrayList<VaccineDTO>();
					branchDTO.setVaccines(vaccineDTOs);
					branchVaccinesDtos.put(branchDTO.getBranchName(), branchDTO);
				}
				branchVaccinesDtos.get(branchDTO.getBranchName()).getVaccines().add(vaccineDTO);
			}

		};
	}

	@Override
	@Transactional
	public String scheduleVaccinationTimeslot(ScheduleTimeslotDTO scheduleTimeslotDTO) {
		log.info("scheduleVaccinationTimeslot called");

		String response = null;

		// decreasing vaccine count
		branchVaccineRepository.decreaseVaccineCount(scheduleTimeslotDTO.getBranchId(),
				scheduleTimeslotDTO.getVaccineId());

		// saving time slot
		ScheduleTimeslot scheduleTimeslot = new ScheduleTimeslot();
		scheduleTimeslot.setBranch(branchRepository.findById(scheduleTimeslotDTO.getBranchId()).orElse(null));
		scheduleTimeslot.setVaccine(vaccineRepository.findById(scheduleTimeslotDTO.getVaccineId()).orElse(null));
		scheduleTimeslot.setEmail(scheduleTimeslotDTO.getEmail());
		scheduleTimeslot.setTransactionId(UUID.randomUUID().toString());
		scheduleTimeslot.setTransactionStatus(TransactionStatus.INITIATED);
		scheduleTimeslot.setSlotDate(scheduleTimeslotDTO.getSlotDate());
		scheduleTimeslot.setActive((short) 1);
		scheduleTimeslot.setCreatedAt(Calendar.getInstance().getTime());
		scheduleTimeslot.setCreatedBy(0);
		scheduleTimeslot.setModifiedAt(Calendar.getInstance().getTime());
		scheduleTimeslot.setModifiedBy(0);
		scheduleTimeslotRepository.save(scheduleTimeslot);

		// Sending scheduled email
		emailServiceUtil.sendScheduleMail(scheduleTimeslot);

		response = GlobalConstants.SUCCESS;
		return response;
	}

	@Override
	@Transactional
	public boolean slotAvailable(ScheduleTimeslotDTO scheduleTimeslotDTO) {
		try {
			ScheduleTimeslot scheduleTimeslot = scheduleTimeslotRepository.slotAvailable(
					scheduleTimeslotDTO.getSlotDate(),
					Arrays.asList(TransactionStatus.INITIATED, TransactionStatus.SUCCESS));
			if (scheduleTimeslot == null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Map<String, BranchDTO> getScheduleVaccinationByBranch(Integer branchId) {
		log.info("getScheduleVaccinationByBranch called for branch "+branchId);
		Map<String, BranchDTO> mapVaccineByBranch = new HashMap<>();
		List<ScheduleTimeslot> findAllByBranchId = scheduleTimeslotRepository.findAllByBranchId(branchId);
		findAllByBranchId.forEach(preapreListVaccineByBranch(mapVaccineByBranch));
		return mapVaccineByBranch;
	}

	private Consumer<ScheduleTimeslot> preapreListVaccineByBranch(Map<String, BranchDTO> mapVaccineByBranch) {
		return new Consumer<ScheduleTimeslot>() {
			@Override
			public void accept(ScheduleTimeslot t) {
				BranchDTO branchDTO = dozerBeanMapper.map(t.getBranch(), BranchDTO.class);
				VaccineDTO vaccineDTO = dozerBeanMapper.map(t.getVaccine(), VaccineDTO.class);
				vaccineDTO.setVaccineCount(1);
				vaccineDTO.setSloatDate(t.getSlotDate());
				if (!mapVaccineByBranch.containsKey(branchDTO.getBranchName())) {
					List<VaccineDTO> vaccineDTOs = new ArrayList<VaccineDTO>();
					branchDTO.setVaccines(vaccineDTOs);
					mapVaccineByBranch.put(branchDTO.getBranchName(), branchDTO);
				}
				mapVaccineByBranch.get(branchDTO.getBranchName()).getVaccines().add(vaccineDTO);
			}
		};
	}

	@Override
	public Map<String, BranchDTO> getScheduleVaccinationPerDay(LocalDate start, LocalDate end) {
		log.info("getScheduleVaccinationPerDay called for "+start);
		Map<String, BranchDTO> mapVaccineByBranch = new HashMap<>();
		try {
			List<ScheduleTimeslot> findAllByBranchId;
			Date startDate = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD).parse(start.toString());
			Date endDate = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD).parse(end.toString());
			log.info(""+startDate);
			log.info(""+endDate);
			findAllByBranchId = scheduleTimeslotRepository.findAllByDay(startDate,endDate);
			findAllByBranchId.forEach(preapreListVaccineByBranch(mapVaccineByBranch));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mapVaccineByBranch;
	}
	
	@Override
	public Map<String, BranchDTO> confirmedVaccinations(LocalDate start, LocalDate end) {
		log.info("getScheduleVaccinationPerDay called for "+start);
		Map<String, BranchDTO> mapVaccineByBranch = new HashMap<>();
		try {
			List<ScheduleTimeslot> findAllByBranchId;
			Date startDate = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD).parse(start.toString());
			Date endDate = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD).parse(end.toString());
			log.info(""+startDate);
			log.info(""+endDate);
			findAllByBranchId = scheduleTimeslotRepository.findAllConfirmedByDay(startDate,endDate);
			findAllByBranchId.forEach(preapreListVaccineByBranch(mapVaccineByBranch));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mapVaccineByBranch;
	}

	@Override
	@Transactional
	public String confirmVaccination(String email) {
		log.info("confirmVaccination called for "+email);
		ScheduleTimeslot scheduleTimeslot = scheduleTimeslotRepository.findByEmail(email);
		if(scheduleTimeslot!=null) {
			scheduleTimeslot.setVaccinactionDone((short)1);
			scheduleTimeslotRepository.save(scheduleTimeslot);
			return GlobalConstants.UPDATED;
		}else {
			return GlobalConstants.NOT_FOUND;
		}
	}
}
