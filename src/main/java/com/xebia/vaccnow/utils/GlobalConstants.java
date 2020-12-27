package com.xebia.vaccnow.utils;

import com.itextpdf.text.Paragraph;

public interface GlobalConstants {

	String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	String YYYY_MM_DD = "yyyy-MM-dd";
	String CACHE_CLEAR_CRON = "0 0/5 * * * ?";

	int MINUTES_INTERVAL=15;
	int MAIL_SCHEDULE_TIME = 10*1000;
	
	String SUCCESS = "SUCCESS";
	String UPDATED = "UPDATED";
	String NOT_FOUND = "NOT_FOUND";
	Float AMOUNT = 5000.00f;
}

