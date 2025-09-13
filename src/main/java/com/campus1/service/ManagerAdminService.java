package com.campus1.service;



import org.springframework.stereotype.Service;

import com.campus1.dto.ManagerAdminCreateDto;
import com.campus1.model.ManagerAdmin;
import com.campus1.model.ManagerManager;
import com.campus1.repository.ManagerAdminRepository;
@Service
public class ManagerAdminService {
	
    private final ManagerAdminRepository managerAdminRepository;

    public ManagerAdminService(ManagerAdminRepository managerAdminRepository) {
        this.managerAdminRepository = managerAdminRepository;
    }



	public void createAdminUnderManagerEmail(ManagerAdminCreateDto dto,ManagerManager m) {
	    ManagerAdmin managerAdmin = new ManagerAdmin()                                             ;
	    managerAdmin.setName(dto.getName());
	    managerAdmin.setEmail(dto.getEmail());
	    managerAdmin.setPhone(Long.valueOf(dto.getPhone()));
	    managerAdmin.setPwd(dto.getPassword());
	    managerAdmin.setDesignation(dto.getDesignation());
	    managerAdmin.setAddress(dto.getAddress());
	    managerAdmin.setManager(m);
	    managerAdminRepository.save(managerAdmin);
	}
}
