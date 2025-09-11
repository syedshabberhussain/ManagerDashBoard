package com.campus1.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.campus1.dto.NotificationRequest;
import com.campus1.dto.NotificationResponseDto;
import com.campus1.service.NotificationsService;

@Controller
@RequestMapping("/api/notifications")
public class NotificationsController {
	
	private final NotificationsService notificationService;

    public NotificationsController(NotificationsService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationResponseDto> sendNotification(@RequestBody NotificationRequest request) {
    	NotificationResponseDto notification = notificationService.sendNotification(request);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<NotificationResponseDto>> getAllNotifications(@RequestParam String email) {
        List<NotificationResponseDto> notifications = notificationService.getAllNotifications(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/received")
    public ResponseEntity<List<NotificationResponseDto>> getReceivedNotifications(@RequestParam String email) {
       List<NotificationResponseDto> lis = notificationService.getReceivedNotifications(email);
       return ResponseEntity.ok(lis);
    }
    
    @PostMapping("/mark-read")
    public ResponseEntity<Void> markAsRead(@RequestParam Long notificationId,@RequestParam String managerEmail) {
        notificationService.markNotificationAsRead(notificationId, managerEmail);
        return ResponseEntity.ok().build();
    }
}
