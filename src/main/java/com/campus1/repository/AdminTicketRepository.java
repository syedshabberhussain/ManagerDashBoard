package com.campus1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campus1.model.AdminTicket;

public interface AdminTicketRepository extends JpaRepository<AdminTicket,Long> {
	
	@Query("SELECT t FROM AdminTicket t WHERE t.admin.email = :adminEmail")
    List<AdminTicket> findByAdminEmail(@Param("adminEmail") String adminEmail);

	// fetch all admin tickets under a specific manager
    List<AdminTicket> findByAdminManagerEmail(String managerEmail);
}
