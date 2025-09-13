package com.campus1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.model.ManagerManager;

@Repository
public interface ManagerManagerRepository extends JpaRepository<ManagerManager,Long>{
	 ManagerManager findByEmail(String email);
}
