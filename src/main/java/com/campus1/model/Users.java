package com.campus1.model;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Users {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String degree;
    private String department;
    private int age;
    private long phone;
    private String callStatus;
    private String answeredCall;
    private String lookingForJob;
    private String eligibleForClient;
    private String telephoneInterview;
    private String hrInterview;
    private String operationRound;
    private String versantVoice;
    private String documentation;
    private String offerLetter;
    private String joining;
    private String finalStatus;
    private LocalDate recruitedDate;
    private LocalDate assignedDate;
    
 
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    
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
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public String getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public String getAnsweredCall() {
		return answeredCall;
	}
	public void setAnsweredCall(String answeredCall) {
		this.answeredCall = answeredCall;
	}
	public String getLookingForJob() {
		return lookingForJob;
	}
	public void setLookingForJob(String lookingForJob) {
		this.lookingForJob = lookingForJob;
	}
	public String getEligibleForClient() {
		return eligibleForClient;
	}
	public void setEligibleForClient(String eligibleForClient) {
		this.eligibleForClient = eligibleForClient;
	}
	public String getTelephoneInterview() {
		return telephoneInterview;
	}
	public void setTelephoneInterview(String telephoneInterview) {
		this.telephoneInterview = telephoneInterview;
	}
	public String getHrInterview() {
		return hrInterview;
	}
	public void setHrInterview(String hrInterview) {
		this.hrInterview = hrInterview;
	}
	public String getOperationRound() {
		return operationRound;
	}
	public void setOperationRound(String operationRound) {
		this.operationRound = operationRound;
	}
	public String getVersantVoice() {
		return versantVoice;
	}
	public void setVersantVoice(String versantVoice) {
		this.versantVoice = versantVoice;
	}
	public String getDocumentation() {
		return documentation;
	}
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	public String getOfferLetter() {
		return offerLetter;
	}
	public void setOfferLetter(String offerLetter) {
		this.offerLetter = offerLetter;
	}
	public String getJoining() {
		return joining;
	}
	public void setJoining(String joining) {
		this.joining = joining;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public LocalDate getRecruitedDate() {
		return recruitedDate;
	}
	public void setRecruitedDate(LocalDate recruitedDate) {
		this.recruitedDate = recruitedDate;
	}
	public LocalDate getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", email=" + email + ", degree=" + degree + ", department="
				+ department + ", age=" + age + ", phone=" + phone + ", callStatus=" + callStatus + ", answeredCall="
				+ answeredCall + ", lookingForJob=" + lookingForJob + ", eligibleForClient=" + eligibleForClient
				+ ", telephoneInterview=" + telephoneInterview + ", hrInterview=" + hrInterview + ", operationRound="
				+ operationRound + ", versantVoice=" + versantVoice + ", documentation=" + documentation
				+ ", offerLetter=" + offerLetter + ", joining=" + joining + ", finalStatus=" + finalStatus
				+ ", recruitedDate=" + recruitedDate + ", assignedDate=" + assignedDate + ", manager=" + manager
				+ ", admin=" + admin + "]";
	}
	
	
}
