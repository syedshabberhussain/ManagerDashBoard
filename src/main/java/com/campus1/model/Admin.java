package com.campus1.model;

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


@Entity
public class Admin {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    @Column(unique = true, nullable = false)
	    private String email;
	    @Column(unique = false, nullable = false)
	    private Long phone;
	    private String designation;
	    private String pwd;
	    private String address;

	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name = "manager_id")
	    private Manager manager;
	    
	    @OneToMany(mappedBy = "admin")
	    private List<Users> users;
	    
	    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<AdminTicket> tickets;
	    
	    
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

		public Long getPhone() {
			return phone;
		}

		public void setPhone(Long phone) {
			this.phone = phone;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Manager getManager() {
			return manager;
		}

		public void setManager(Manager manager) {
			this.manager = manager;
		}

		public List<Users> getUsers() {
			return users;
		}

		public void setUsers(List<Users> users) {
			this.users = users;
		}
}
