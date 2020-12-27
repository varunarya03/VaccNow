package com.xebia.vaccnow.repositories;

import com.xebia.vaccnow.models.Branch;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BranchRepository extends CrudRepository<Branch, Integer> {
	List<Branch> findByActive(short active);
}
