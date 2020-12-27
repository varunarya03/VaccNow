package com.xebia.vaccnow.services;

import java.util.List;

import com.xebia.vaccnow.dto.BranchDTO;
import com.xebia.vaccnow.models.Branch;

public interface BranchService {
    public Branch save(Branch branch);

	public List<BranchDTO> listAllBranches();

	public BranchDTO availableBranchTime(Integer branchId);
}
