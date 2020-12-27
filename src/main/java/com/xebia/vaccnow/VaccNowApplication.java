package com.xebia.vaccnow;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xebia.vaccnow.dto.ScheduleTimeslotDTO;
import com.xebia.vaccnow.models.Branch;
import com.xebia.vaccnow.models.BranchVaccine;
import com.xebia.vaccnow.models.Vaccine;
import com.xebia.vaccnow.repositories.BranchVaccineRepository;
import com.xebia.vaccnow.services.BranchVaccineService;
import com.xebia.vaccnow.utils.GlobalConstants;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class VaccNowApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(VaccNowApplication.class, args);
	}
	
	@Bean
	@Primary
	public DozerBeanMapper getDozerBean() {
		return new DozerBeanMapper();
	}
	
	@Autowired 
    private CacheManager cacheManager;         
	
    @Scheduled(cron = GlobalConstants.CACHE_CLEAR_CRON)
    public void clearCacheSchedule(){
        for(String name:cacheManager.getCacheNames()){
            cacheManager.getCache(name).clear();
        }
    }

	/* Default data set */
	@Autowired
	private BranchVaccineRepository branchVaccineRepository;
	@Autowired
	private BranchVaccineService branchVaccineService;

	@Override
	public void run(String... args) throws Exception {
		Vaccine vaccine1 = new Vaccine(101, "Bharat Vaccine", (short) 1, Calendar.getInstance().getTime(), 0,
				Calendar.getInstance().getTime(), 0);
		Vaccine vaccine2 = new Vaccine(102, "Moderna", (short) 1, Calendar.getInstance().getTime(), 0,
				Calendar.getInstance().getTime(), 0);

		Branch branchA = new Branch(201, "Delhi", Time.valueOf("10:00:00"), Time.valueOf("17:00:00"), (short) 1, Calendar.getInstance().getTime(), 0,
				Calendar.getInstance().getTime(), 0);
		Branch branchB = new Branch(202, "Gurgaon", Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), (short) 1, Calendar.getInstance().getTime(), 0,
				Calendar.getInstance().getTime(), 0);

		BranchVaccine branchVaccine1 = new BranchVaccine(301, branchA, vaccine1, 100, (short) 1, Calendar.getInstance().getTime(), 0,
		Calendar.getInstance().getTime(), 0);
		BranchVaccine branchVaccine2 = new BranchVaccine(302, branchB, vaccine2, 50, (short) 1, Calendar.getInstance().getTime(), 0,
		Calendar.getInstance().getTime(), 0);
		BranchVaccine branchVaccine3 = new BranchVaccine(303, branchA, vaccine2, 50, (short) 1, Calendar.getInstance().getTime(), 0,
		Calendar.getInstance().getTime(), 0);

		branchVaccineRepository.saveAll(Arrays.asList(branchVaccine1,branchVaccine2,branchVaccine3));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.YYYY_MM_DD);
		SimpleDateFormat sdf = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD_HH_MM);
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = date1.plusDays(1);
		ScheduleTimeslotDTO scheduleTimeslotDTO1 = new ScheduleTimeslotDTO();
		scheduleTimeslotDTO1.setEmail("user1@user.com");
		scheduleTimeslotDTO1.setVaccineId(vaccine1.getId());
		scheduleTimeslotDTO1.setBranchId(branchA.getId());
		scheduleTimeslotDTO1.setSlotDate(sdf.parse(date1.format(formatter)+" 10:00"));
		ScheduleTimeslotDTO scheduleTimeslotDTO2 = new ScheduleTimeslotDTO();
		scheduleTimeslotDTO2.setEmail("user2@user.com");
		scheduleTimeslotDTO2.setVaccineId(vaccine2.getId());
		scheduleTimeslotDTO2.setBranchId(branchA.getId());
		scheduleTimeslotDTO2.setSlotDate(sdf.parse(date1.format(formatter)+" 11:30"));
		ScheduleTimeslotDTO scheduleTimeslotDTO3 = new ScheduleTimeslotDTO();
		scheduleTimeslotDTO3.setEmail("user3@user.com");
		scheduleTimeslotDTO3.setVaccineId(vaccine2.getId());
		scheduleTimeslotDTO3.setBranchId(branchB.getId());
		scheduleTimeslotDTO3.setSlotDate(sdf.parse(date2.format(formatter)+" 11:00"));
		branchVaccineService.scheduleVaccinationTimeslot(scheduleTimeslotDTO1);
		branchVaccineService.scheduleVaccinationTimeslot(scheduleTimeslotDTO2);
		branchVaccineService.scheduleVaccinationTimeslot(scheduleTimeslotDTO3);
		
		branchVaccineService.confirmVaccination("user2@user.com");
	}
}
