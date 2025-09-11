package com.campus1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campus1.model.NotificationManager;

public interface NotificationManagerRepository extends JpaRepository<NotificationManager, Long> {

    @Query("SELECT nm FROM NotificationManager nm JOIN nm.manager m WHERE nm.notification.id = :notificationId AND m.email = :email")
    Optional<NotificationManager> findByNotificationIdAndManagerEmail(@Param("notificationId") Long notificationId,@Param("email") String email);

}
