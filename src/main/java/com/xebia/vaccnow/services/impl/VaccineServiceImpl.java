package com.xebia.vaccnow.services.impl;

import com.xebia.vaccnow.models.Vaccine;
import com.xebia.vaccnow.repositories.VaccineRepository;
import com.xebia.vaccnow.services.VaccineService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineServiceImpl implements VaccineService{
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    VaccineRepository vaccineRepository;

    @Override
    public Vaccine save(Vaccine vaccine) {
    	log.info("save called");
        return vaccineRepository.save(vaccine);
    }
}
