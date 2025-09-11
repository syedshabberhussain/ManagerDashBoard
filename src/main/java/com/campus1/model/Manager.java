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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Manager {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  
	    
	    private String name;
	    
	    
	    private String password;
	    

	    @Column(unique = true, nullable = false)
	    private String email;
	    

	    private Long phone;
	    
	    private String role;
	
	    private String address;
	    
	    // ✅ Store image bytes directly in DB
	    @Lob
	    @Column(columnDefinition = "LONGBLOB")
	    private byte[] profilePicture;

	    @Transient
	    private String imageBase64;
	    
	    @ManyToOne
	    @JoinColumn(name = "super_admin_id")
	    private SuperAdmin superAdmin;

	    
	    @JsonIgnore
	    @OneToMany(mappedBy = "manager")
	    private List<Admin> admins;
	    
	    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<ManagerTicket> tickets;

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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
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

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public byte[] getProfilePicture() {
			return profilePicture;
		}

		public void setProfilePicture(byte[] profilePicture) {
			this.profilePicture = profilePicture;
		}

		public String getImageBase64() {
			return imageBase64;
		}

		public void setImageBase64(String imageBase64) {
			this.imageBase64 = imageBase64;
		}

		public SuperAdmin getSuperAdmin() {
			return superAdmin;
		}

		public void setSuperAdmin(SuperAdmin superAdmin) {
			this.superAdmin = superAdmin;
		}

		public List<Admin> getAdmins() {
			return admins;
		}

		public void setAdmins(List<Admin> admins) {
			this.admins = admins;
		}

		public List<ManagerTicket> getTickets() {
			return tickets;
		}

		public void setTickets(List<ManagerTicket> tickets) {
			this.tickets = tickets;
		}
}
