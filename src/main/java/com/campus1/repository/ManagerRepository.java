package com.campus1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long>{
	 Manager findByEmail(String email);
}
