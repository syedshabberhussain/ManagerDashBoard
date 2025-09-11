package com.campus1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.campus1.model.Notifications;

import java.util.List;


@Repository
public interface NotificationsRepository extends JpaRepository<Notifications,Long> {
	List<Notifications> findBySentBy(String email);
	
	 // Fetch all notifications where this manager is a recipient
    @Query("""
       SELECT n FROM Notifications n 
       JOIN n.managerRecipients nm 
       WHERE nm.manager.id = :managerId
    """)
    List<Notifications> findReceivedByManagerId(@Param("managerId") Long managerId);
}
