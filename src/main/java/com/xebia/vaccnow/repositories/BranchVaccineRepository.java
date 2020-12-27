package com.xebia.vaccnow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xebia.vaccnow.models.BranchVaccine;

public interface BranchVaccineRepository extends CrudRepository<BranchVaccine, Integer> {

	List<BranchVaccine> findAllByActive(short active);

	@Query("from BranchVaccine where branch.id = :branchId and active = 1")
	List<BranchVaccine> findByBranch(@Param(value = "branchId") Integer branchId);

	@Modifying
	@Query("update BranchVaccine set vaccineCount=vaccineCount-1 where branch.id = :branchId and vaccine.id = :vaccineId and active = 1")
	void decreaseVaccineCount(@Param(value = "branchId") Integer branchId, 
			@Param(value = "vaccineId") Integer vaccineId);
	
	@Modifying
	@Query("update BranchVaccine set vaccineCount=vaccineCount+1 where branch.id = :branchId and vaccine.id = :vaccineId and active = 1")
	void increaseVaccineCount(@Param(value = "branchId") Integer branchId, 
			@Param(value = "vaccineId") Integer vaccineId);

}
