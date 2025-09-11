package com.campus1.dto;

public class AdminCreateDto {
		private String name;
	    private String email;
	    private Long phone;
	    private String designation;
	    private String address;
	    private String password;
	    private String managerMail;
	    
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
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getManagerMail() {
			return managerMail;
		}
		public void setManagerMail(String managerMail) {
			this.managerMail = managerMail;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
}
