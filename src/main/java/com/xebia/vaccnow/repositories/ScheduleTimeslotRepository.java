package com.xebia.vaccnow.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xebia.vaccnow.models.ScheduleTimeslot;

public interface ScheduleTimeslotRepository extends CrudRepository<ScheduleTimeslot, Integer> {

	List<ScheduleTimeslot> findAllByActive(short active);

	ScheduleTimeslot findBySlotDateAndActive(Date slotDate, short active);

	@Query("from ScheduleTimeslot where slotDate = :slotDate "
			+ "and transactionStatus in (:transactionStatus) "
			+ "and active = 1")
	ScheduleTimeslot slotAvailable(@Param(value = "slotDate") Date slotDate, 
			@Param(value = "transactionStatus") List<String> transactionStatus);


	@Query("from ScheduleTimeslot where vaccinactionDone=1 "
			+ "and transactionStatus in (:transactionStatus) "
			+ "and active = 1")
	List<ScheduleTimeslot> generateVaccineCertificates(@Param(value = "transactionStatus") List<String> transactionStatus);

	@Modifying
	@Query("update ScheduleTimeslot set vaccinactionDone=1 where id = :id")
	void updateStatus(@Param(value = "id") Integer id);
	
	@Query("from ScheduleTimeslot where branch.id = :branchId and active = 1")
	List<ScheduleTimeslot> findAllByBranchId(@Param(value = "branchId") Integer branchId);
	
	@Query("from ScheduleTimeslot where slotDate between :start and :end and active = 1")
	List<ScheduleTimeslot> findAllByDay(@Param(value = "start") Date start,
			@Param(value = "end") Date end);
	
	@Query("from ScheduleTimeslot where vaccinactionDone=1 and slotDate between :start and :end and active = 1")
	List<ScheduleTimeslot> findAllConfirmedByDay(@Param(value = "start") Date start,
			@Param(value = "end") Date end);

	@Query("from ScheduleTimeslot where email = :email and active = 1")
	ScheduleTimeslot findByEmail(@Param(value = "email") String email);
}
