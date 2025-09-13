package com.campus1.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="NotificationManager")
public class ManagerNotificationManager {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private ManagerNotifications notifications;

    @ManyToOne
    private ManagerManager manager;

    private boolean isRead = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ManagerNotifications getNotification() {
		return notifications;
	}

	public void setNotification(ManagerNotifications notification) {
		this.notifications = notification;
	}

	public ManagerNotifications getNotifications() {
		return notifications;
	}

	public void setNotifications(ManagerNotifications notifications) {
		this.notifications = notifications;
	}

	public ManagerManager getManager() {
		return manager;
	}

	public void setManager(ManagerManager manager) {
		this.manager = manager;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}
