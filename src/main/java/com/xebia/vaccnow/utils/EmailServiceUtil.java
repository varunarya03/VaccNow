package com.xebia.vaccnow.utils;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xebia.vaccnow.models.ScheduleTimeslot;

@Service
public class EmailServiceUtil {
	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	public void sendScheduleMail(ScheduleTimeslot scheduleTimeslot) {
		log.info("Sending mail to " + scheduleTimeslot.getEmail());
		SimpleDateFormat sdf = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD_HH_MM);
		String msg = "\n\n\n"
				+ "Hello "+scheduleTimeslot.getEmail()+","
				+ "\nYou have scheduled appointment for " + scheduleTimeslot.getVaccine().getVaccineName()
				+ " at " + scheduleTimeslot.getBranch().getBranchName()
				+ " - " + sdf.format(scheduleTimeslot.getSlotDate())
				+ ".\n\n\n";
		log.info(msg);
	}

}
