package com.campus1.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.campus1.dto.AdminDto;
import com.campus1.dto.ManagerProfileDto;
import com.campus1.exception.ManagerNotFoundException;
import com.campus1.model.Admin;
import com.campus1.model.Manager;
import com.campus1.model.Users;
import com.campus1.repository.AdminRepository;
import com.campus1.repository.ManagerRepository;
import com.campus1.repository.UserRepository;

@Service
public class ManagerService {
	
	
	private final ManagerRepository managerRepo;
    private final AdminRepository adminRepo;
    private final UserRepository userRepo;
    
    public ManagerService(ManagerRepository managerRepo,AdminRepository adminRepo,UserRepository userRepo) {
			this.managerRepo = managerRepo;
			this.adminRepo = adminRepo;
			this.userRepo = userRepo;
    }

    public Manager getManagerByEmail(String email) {
        Manager m=managerRepo.findByEmail(email);
        if(m==null) {
        	throw new ManagerNotFoundException("Manager not Found");
        }
        else {
        	return m;
        }
    }

    public List<Admin> getAdminsByManagerId(Long managerId) {
        return adminRepo.findByManagerId(managerId);
    }
    
    public List<AdminDto> getAdminsWithRecruitCount(Long managerId, LocalDate date) {
        List<Admin> admins = adminRepo.findByManagerId(managerId);

        return admins.stream()
            .map(admin -> {
                long recruitsCount;
                if (date != null) {
                    recruitsCount = userRepo.countRecruitsByAdminAndDate(admin.getId(), date);
                } else {
                    recruitsCount = userRepo.countRecruitsByAdmin(admin.getId());
                }

                return new AdminDto(
                        admin.getId(),
                        admin.getName(),
                        admin.getEmail(),
                        admin.getPhone(),
                        admin.getDesignation(),
                        admin.getAddress(),
                        recruitsCount
                );
            })
            .toList();
    }
    
    public ManagerProfileDto getManagerProfileByEmail(String email) {
        Manager manager = managerRepo.findByEmail(email);

        ManagerProfileDto dto = new ManagerProfileDto();
        dto.setName(manager.getName());
        dto.setEmail(manager.getEmail());
        dto.setRole(manager.getRole());
        dto.setPhone(manager.getPhone());
        dto.setAddress(manager.getAddress());

        if (manager.getProfilePicture() != null) {
            String base64 = Base64.getEncoder().encodeToString(manager.getProfilePicture());
            dto.setImageBase64("data:image/png;base64," + base64); // you can detect content-type if needed
        }

        return dto;
    }
    

    
    public void updateProfileWithImage(ManagerProfileDto dto, MultipartFile file) throws IOException {
        Manager existing = getManagerByEmail(dto.getEmail());

        existing.setName(dto.getName());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());

        if (file != null && !file.isEmpty()) {
            // store raw bytes in DB
            existing.setProfilePicture(file.getBytes());

            // also keep Base64 for frontend convenience
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            existing.setImageBase64("data:" + file.getContentType() + ";base64," + base64);
        }

        managerRepo.save(existing);
    }
    
    public void removeProfilePicture(String email) {
        Manager existing = getManagerByEmail(email);
        if (existing != null) {
            existing.setProfilePicture(null);
            managerRepo.save(existing);
        }
    }

    public void save(Manager manager) {
        managerRepo.save(manager);
    }

	
    public void assignMultipleTasks(String username, Long adminId, List<Long> userIds) {
		
		getManagerByEmail(username);
		Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + adminId));

        List<Users> tasks = userRepo.findAllById(userIds);

        for (Users task : tasks) {
            task.setAdmin(admin);
            task.setAssignedDate(LocalDate.now());
            System.out.println(LocalDate.now());
        }
        userRepo.saveAll(tasks);
	}
    
}
