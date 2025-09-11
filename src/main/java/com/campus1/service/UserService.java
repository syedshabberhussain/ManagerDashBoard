package com.campus1.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.campus1.dto.UserModelDto;
import com.campus1.dto.UserPieModelDto;
import com.campus1.exception.UsersNotFound;
import com.campus1.model.Users;
import com.campus1.repository.UserRepository;




@Service
public class UserService {
	
	private final UserRepository usersRepo;
	
	
	public UserService(UserRepository usersRepo ) {
		this.usersRepo=usersRepo;
	}
	
    public List<Users> getTasksAssignedToManager(Long managerId) {
        return usersRepo.findByManagerIdAndAdminIsNull(managerId);
    }
    
    public List<Users> getTaskAssignedToManagerToAdmin(Long managerid){
    	return usersRepo.findByManagerIdAndAdminIsNotNull(managerid);
    }
    
    public List<Users> getUnClosedTasks(Long managerId) {
        List<Users> abc = usersRepo.findUnClosedTasks(managerId);
        return abc;
    }

    public List<Users> getClosedTasks(Long managerId) {
        return usersRepo.findClosedTasks(managerId);
    }
    
    public List<UserPieModelDto> getUsersByAdminId(Long adminId, LocalDate date, String tab) {
        List<Users> users = new ArrayList<>();

        if ("assigned".equalsIgnoreCase(tab)) {
            if (date != null) {
                // Assigned on specific date
                users = usersRepo.findByAdminIdAndAssignedDate(adminId, date);
            } else {
                // All assigned but not closed (under that admin)
                users = usersRepo.findUnClosedTasksByAdminId(adminId); // Updated
            }
        } else if ("recruited".equalsIgnoreCase(tab)) {
            if (date != null) {
                users = usersRepo.findByAdminIdAndFinalStatusInAndRecruitedDate(
                        adminId,
                        List.of("Recruited"),
                        date
                );
            } else {
                users = usersRepo.findClosedTasksByAdminId(adminId).stream() // Updated
                        .filter(u -> "Recruited".equalsIgnoreCase(u.getFinalStatus()))
                        .toList();
            }
        } else if ("fileClosed".equalsIgnoreCase(tab)) {
            if (date != null) {
                users = usersRepo.findByAdminIdAndFinalStatusInAndRecruitedDate(
                        adminId,
                        List.of("Recruited", "Rejected"),
                        date
                );
            } else {
                users = usersRepo.findClosedTasksByAdminId(adminId); // Updated
            }
        } else {
            // Fallback: return all users under admin
            users = usersRepo.findByAdminId(adminId);
        }
        return users.stream().map(UserPieModelDto::new).toList();
    }	
	
	public UserModelDto getUserById(Long userId) {
		Users user = usersRepo.findById(userId)
	            .orElseThrow(() -> new UsersNotFound("Users Not Found"));
	    
		return new UserModelDto(user.getId(),user.getName(),user.getEmail(),user.getDepartment(),user.getAge(),
	            user.getPhone(),user.getCallStatus(),user.getAnsweredCall(),user.getLookingForJob(),
	            user.getEligibleForClient(),user.getTelephoneInterview(),user.getHrInterview(),
	            user.getOperationRound(),user.getVersantVoice(),user.getDocumentation(),
	            user.getOfferLetter(),user.getJoining(),user.getFinalStatus(),user.getRecruitedDate()
	    );
	}

}
