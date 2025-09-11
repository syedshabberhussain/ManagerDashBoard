package com.campus1.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class SuperAdmin {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 private String name;
	 @Column(unique = true, nullable = false)
	 private String email;

	 @OneToMany(mappedBy = "superAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Manager> managers;
	 
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

	 public List<Manager> getManagers() {
		 return managers;
	 }

	 public void setManagers(List<Manager> managers) {
		 this.managers = managers;
	 }

}
