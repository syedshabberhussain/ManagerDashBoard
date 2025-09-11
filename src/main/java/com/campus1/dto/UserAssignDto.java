package com.campus1.dto;

import java.time.LocalDate;

import com.campus1.model.Users;

public class UserAssignDto {
	private long id;
	private String name;
    private String degree;
    private String email;
    private String admin;
    private LocalDate assignedDate;
    
    
    public UserAssignDto(Users user) {
    	this.id=user.getId();
        this.name = user.getName();
        this.degree = user.getDegree();
        this.email = user.getEmail();
        this.admin =user.getAdmin() != null ? user.getAdmin().getName() : null;
        this.assignedDate = user.getAssignedDate();
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public LocalDate getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}
    
}
