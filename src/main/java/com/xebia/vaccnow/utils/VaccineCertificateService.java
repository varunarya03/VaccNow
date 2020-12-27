package com.xebia.vaccnow.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.xebia.vaccnow.models.ScheduleTimeslot;

@Service
public class VaccineCertificateService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	public String generateVaccineCertificate(ScheduleTimeslot scheduleTimeslot) {
		String filePath = "Vaccine Certificate - " + scheduleTimeslot.getEmail() + ".pdf";
		SimpleDateFormat sdf = new SimpleDateFormat(GlobalConstants.YYYY_MM_DD_HH_MM);
		String msg = "\n\nDear "+scheduleTimeslot.getEmail()+",\n\n"
				+ "This is to certify that you have successfully vaccinated " + scheduleTimeslot.getVaccine().getVaccineName()
				+ " on " + scheduleTimeslot.getBranch().getBranchName()
				+ " at " + sdf.format(scheduleTimeslot.getSlotDate());
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
			Chunk chunk = new Chunk("Vaccine Certificate", font);
			document.add(chunk);
			Paragraph para = new Paragraph(msg); 
			document.add(para);
			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			log.error("Exception :: ", e);
		}
		return filePath;
	}

	public String generateInvoice(ScheduleTimeslot scheduleTimeslot) {
		String filePath = "Invoice - " + scheduleTimeslot.getEmail() + ".pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
			Chunk chunk = new Chunk("Invoice", font);
			document.add(chunk);
			Paragraph para = new Paragraph(
					"\n\nInvoice No : "+UUID.randomUUID()+"\n"+
					"Amount : "+GlobalConstants.AMOUNT
					); 
			document.add(para);
			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			log.error("Exception :: ", e);
		}
		return filePath;
	}

}
