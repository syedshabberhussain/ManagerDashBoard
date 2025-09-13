package com.campus1.dto;

public class ManagerAdminDto {
	
	private Long id;
    private String name;
    private String email;
    private Long phone;
    private String designation;
    private String address;
    private Long recruitsCount;
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
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
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
	public Long getRecruitsCount() {
		return recruitsCount;
	}
	public void setRecruitsCount(Long recruitsCount) {
		this.recruitsCount = recruitsCount;
	}
	
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public ManagerAdminDto(Long id, String name, String email, Long phone, String designation, String address,
			Long recruitsCount) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.designation = designation;
		this.address = address;
		this.recruitsCount = recruitsCount;
	}
	
	
}
