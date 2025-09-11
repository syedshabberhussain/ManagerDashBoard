package com.campus1.model;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Notifications {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    
    @Column(length = 2000)
    private String message;

    private String sentBy;
    private LocalDateTime sentAt;
    private String sentRole;
    private String priority;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationAdmin> adminRecipients = new HashSet<>();

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationUser> userRecipients = new HashSet<>();

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationManager> managerRecipients = new HashSet<>();
    
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationSuperAdmin> superAdminRecipients = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSentBy() {
		return sentBy;
	}

	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public Set<NotificationAdmin> getAdminRecipients() {
		return adminRecipients;
	}

	public void setAdminRecipients(Set<NotificationAdmin> adminRecipients) {
		this.adminRecipients = adminRecipients;
	}

	public Set<NotificationUser> getUserRecipients() {
		return userRecipients;
	}

	public void setUserRecipients(Set<NotificationUser> userRecipients) {
		this.userRecipients = userRecipients;
	}

	public Set<NotificationManager> getManagerRecipients() {
		return managerRecipients;
	}

	public void setManagerRecipients(Set<NotificationManager> managerRecipients) {
		this.managerRecipients = managerRecipients;
	}

	public String getSentRole() {
		return sentRole;
	}

	public void setSentRole(String setRole) {
		this.sentRole = setRole;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Notifications [id=" + id + ", subject=" + subject + ", message=" + message + ", sentBy=" + sentBy
				+ ", sentAt=" + sentAt + ", sentRole=" + sentRole + ", priority=" + priority + ", adminRecipients="
				+ adminRecipients + ", userRecipients=" + userRecipients + ", managerRecipients=" + managerRecipients
				+ ", superAdminRecipients=" + superAdminRecipients + ", getId()=" + getId() + ", getSubject()="
				+ getSubject() + ", getMessage()=" + getMessage() + ", getSentBy()=" + getSentBy() + ", getSentAt()="
				+ getSentAt() + ", getAdminRecipients()=" + getAdminRecipients() + ", getUserRecipients()="
				+ getUserRecipients() + ", getManagerRecipients()=" + getManagerRecipients() + ", getSentRole()="
				+ getSentRole() + ", getPriority()=" + getPriority() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}