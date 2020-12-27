package com.xebia.vaccnow.repositories;

import com.xebia.vaccnow.models.Vaccine;
import org.springframework.data.repository.CrudRepository;

public interface VaccineRepository extends CrudRepository<Vaccine, Integer> {
}
