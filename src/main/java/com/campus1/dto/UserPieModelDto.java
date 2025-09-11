package com.campus1.dto;

import java.time.LocalDate;

import com.campus1.model.Users;

public class UserPieModelDto {
	private Long id;
    private String name;
    private String email;
    private String finalStatus;
    
    
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	

	public UserPieModelDto(Users user) {
	    this.id = user.getId();
	    this.name = user.getName();
	    this.email = user.getEmail();
	    this.finalStatus = user.getFinalStatus();
	}
}
