package com.campus1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
	
	List<Admin> findByManagerId(Long managerId);
	
	Optional<Admin> findByEmail(String email);
	}
