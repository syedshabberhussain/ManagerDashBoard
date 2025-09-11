package com.campus1.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.campus1.dto.NotificationRequest;
import com.campus1.dto.NotificationResponseDto;
import com.campus1.exception.ManagerNotFoundException;
import com.campus1.model.Admin;
import com.campus1.model.Manager;
import com.campus1.model.NotificationAdmin;
import com.campus1.model.NotificationManager;
import com.campus1.model.NotificationUser;
import com.campus1.model.Notifications;
import com.campus1.model.Users;
import com.campus1.repository.AdminRepository;
import com.campus1.repository.ManagerRepository;
import com.campus1.repository.NotificationManagerRepository;
import com.campus1.repository.NotificationsRepository;
import com.campus1.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class NotificationsService {
	
	private final NotificationsRepository notificationsRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;
    private final NotificationManagerRepository notMangRepo;
    
    @Autowired
    private JavaMailSender mailSender;

    public NotificationsService(NotificationsRepository notificationsRepository,
                                ManagerRepository managerRepository,
                                AdminRepository adminRepository,NotificationManagerRepository notMangRepo) {
        this.notificationsRepository = notificationsRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
        this.notMangRepo=notMangRepo;
    }

    @Transactional
    public NotificationResponseDto sendNotification(NotificationRequest request) {
        Manager manager = managerRepository.findByEmail(request.getSentBy());

        Notifications notification = new Notifications();
        notification.setSubject(request.getSubject());
        notification.setMessage(request.getMessage());
        notification.setSentBy(request.getSentBy());
        notification.setSentAt(LocalDateTime.now());
        notification.setSentRole(manager.getRole());
        notification.setPriority(request.getPriority());

        List<NotificationAdmin> successfulAdmins = new ArrayList<>();

        if (request.getAdminIds() != null) {
            for (Long adminId : request.getAdminIds()) {
                Admin admin = adminRepository.findById(adminId).orElse(null);
                if (admin != null && isValidEmail(admin.getEmail())) {
                    // Attempt to send email to this admin
                    boolean emailSent = sendEmailToRecipient(admin.getEmail(), request.getSubject(), request.getMessage());
                    if (emailSent) {
                        NotificationAdmin na = new NotificationAdmin();
                        na.setNotification(notification);
                        na.setAdmin(admin);
                        na.setRead(false);
                        successfulAdmins.add(na);
                    }
                }
            }
        }

        // Save only if there are successful recipients
        NotificationResponseDto dto = new NotificationResponseDto();
        if (!successfulAdmins.isEmpty()) {
            notification.getAdminRecipients().addAll(successfulAdmins);
            notificationsRepository.save(notification);

            dto.setId(notification.getId());
            dto.setSubject(notification.getSubject());
            dto.setMessage(notification.getMessage());
            dto.setSentBy(notification.getSentBy());
            dto.setSentAt(notification.getSentAt());
            dto.setSentRole(notification.getSentRole());
            dto.setPriority(notification.getPriority());
        } else {
            // Return empty DTO or handle case where no emails were sent successfully
            dto.setSubject(request.getSubject());
            dto.setMessage("No valid emails were sent successfully.");
        }

        return dto;
    }

    private boolean sendEmailToRecipient(String email, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);

            // Load HTML template from resources
            String htmlContent = loadTemplate(subject, message);
            helper.setText(htmlContent, true); // true = HTML email

            mailSender.send(mimeMessage);
            System.out.println("Email sent to: " + email);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email to: " + email);
            return false;
        }
    }

    private String loadTemplate(String subject, String message) throws IOException {
        ClassPathResource resource = new ClassPathResource("email-template.html");
        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Replace placeholders
        return template.replace("{{subject}}", subject)
                       .replace("{{message}}", message.replace("\n", "<br>"));
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Basic email format validation using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    
    
    public List<NotificationResponseDto> getAllNotifications(String email) {
        List<Notifications> notifications = notificationsRepository.findBySentBy(email);

        List<NotificationResponseDto> list = new ArrayList<>();
        for(Notifications n : notifications) {
            NotificationResponseDto dto = new NotificationResponseDto();
            dto.setId(n.getId());
            dto.setSubject(n.getSubject());
            dto.setMessage(n.getMessage());
            dto.setSentBy(n.getSentBy());
            dto.setSentAt(n.getSentAt());
            dto.setSentRole(n.getSentRole());
            dto.setPriority(n.getPriority());
            list.add(dto);
        }

        return list;
    }

    public List<NotificationResponseDto> getReceivedNotifications(String email) {
        // ✅ Step 1: Validate manager by email
        Manager manager = managerRepository.findByEmail(email);
        if(manager==null) {
        	 throw new ManagerNotFoundException("Manager not found with email: " + email);
        }

        // ✅ Step 2: Fetch notifications by managerId
        List<Notifications> notifications = notificationsRepository.findReceivedByManagerId(manager.getId());

        // ✅ Step 3: Map to DTO
        return notifications.stream().map(n -> {
            NotificationManager nm = n.getManagerRecipients().stream()
                .filter(r -> r.getManager().getId().equals(manager.getId()))
                .findFirst()
                .orElse(null);

            return new NotificationResponseDto(
                n.getId(),
                n.getSubject(),
                n.getMessage(),
                n.getSentBy(),
                n.getSentAt(),
                n.getSentRole(),
                nm != null && nm.isRead(),
                n.getPriority()
            );
        }).toList();
    }

	public void markNotificationAsRead(Long notificationId, String managerEmail) {
		NotificationManager nm = notMangRepo
		        .findByNotificationIdAndManagerEmail(notificationId, managerEmail)
		        .orElseThrow(() -> new RuntimeException("Notification not found for manager"));

		    nm.setRead(true);
		    notMangRepo.save(nm);
	}
}
