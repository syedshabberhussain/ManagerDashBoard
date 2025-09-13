package com.campus1.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.model.ManagerUsers;

@Repository
public interface ManagerUserRepository extends JpaRepository<ManagerUsers,Long> {
	
	
	List<ManagerUsers> findByAdminId(Long adminId);
	
	List<ManagerUsers> findByManagerIdAndAdminIsNull(Long managerId);
	
	List<ManagerUsers> findByManagerIdAndAdminIsNotNull(Long managerId);
}
