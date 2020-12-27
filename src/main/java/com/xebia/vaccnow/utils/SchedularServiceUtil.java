package com.xebia.vaccnow.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.vaccnow.models.ScheduleTimeslot;
import com.xebia.vaccnow.repositories.ScheduleTimeslotRepository;

@Service
public class SchedularServiceUtil {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ScheduleTimeslotRepository scheduleTimeslotRepository;
	@Autowired
	private VaccineCertificateService vaccineCertificateService;

	@Scheduled(fixedDelay = GlobalConstants.MAIL_SCHEDULE_TIME)
	@Transactional
	public void generateVaccineCertificates() {
		log.info("Generate Vaccine and Certificates schedular called");
		List<ScheduleTimeslot> pendingMails = scheduleTimeslotRepository
				.generateVaccineCertificates(Arrays.asList(TransactionStatus.INITIATED, TransactionStatus.SUCCESS));
		pendingMails.forEach(new Consumer<ScheduleTimeslot>() {
			@Override
			public void accept(ScheduleTimeslot scheduleTimeslot) {
				log.info("Generating Invoice for "+scheduleTimeslot.getEmail());
				vaccineCertificateService.generateInvoice(scheduleTimeslot);
				log.info("Generating Vaccine Certificate for "+scheduleTimeslot.getEmail());
				vaccineCertificateService.generateVaccineCertificate(scheduleTimeslot);
				
				scheduleTimeslot.setVaccinactionDone((short)2);
				scheduleTimeslotRepository.save(scheduleTimeslot);
			}
		});
	}
}
