package com.campus1.service;



import org.springframework.stereotype.Service;

import com.campus1.dto.AdminCreateDto;
import com.campus1.model.Admin;
import com.campus1.model.Manager;
import com.campus1.repository.AdminRepository;
@Service
public class AdminService {
	
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }



	public void createAdminUnderManagerEmail(AdminCreateDto dto,Manager m) {
	    Admin admin = new Admin()                                             ;
	    admin.setName(dto.getName());
	    admin.setEmail(dto.getEmail());
	    admin.setPhone(Long.valueOf(dto.getPhone()));
	    admin.setPwd(dto.getPassword());
	    admin.setDesignation(dto.getDesignation());
	    admin.setAddress(dto.getAddress());
	    admin.setManager(m);
	    adminRepository.save(admin);
	}
}
