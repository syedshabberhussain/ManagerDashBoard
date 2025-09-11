package com.campus1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campus1.model.ManagerTicket;

public interface ManagerTicketRepository extends JpaRepository<ManagerTicket,Long> {
	
	 @Query("SELECT t FROM ManagerTicket t WHERE t.manager.email = :managerEmail")
	    List<ManagerTicket> findByManagerEmail(@Param("managerEmail") String managerEmail);

}
