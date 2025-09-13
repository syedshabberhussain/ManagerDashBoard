package com.campus1.model;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class ManagerUsers {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String qualification;
    private int age;
    private long phone;
    
 
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private ManagerManager manager;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private ManagerAdmin admin;
    
 // User → Students (job applications / interview history)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ManagerStudents> applications;

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
	
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public ManagerManager getManager() {
		return manager;
	}

	public void setManager(ManagerManager manager) {
		this.manager = manager;
	}

	public ManagerAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(ManagerAdmin admin) {
		this.admin = admin;
	}

	public List<ManagerStudents> getApplications() {
		return applications;
	}

	public void setApplications(List<ManagerStudents> applications) {
		this.applications = applications;
	}
    
}
