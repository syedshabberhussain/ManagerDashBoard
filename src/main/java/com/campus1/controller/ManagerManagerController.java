package com.campus1.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.campus1.dto.ManagerAdminCreateDto;
import com.campus1.dto.ManagerAdminDto;
import com.campus1.dto.ManagerAssignTaskDto;
import com.campus1.dto.ManagerLoginResponseDto;
import com.campus1.dto.ManagerManagerProfileDto;
import com.campus1.dto.ManagerStudentModelDto;
import com.campus1.dto.ManagerUserAssignDto;
import com.campus1.dto.ManagerUserPieModelDto;
import com.campus1.dto.ManagerUserTrackingDto;
import com.campus1.model.ManagerManager;
import com.campus1.model.ManagerStudents;
import com.campus1.model.ManagerUsers;
import com.campus1.repository.ManagerStudentsRepository;
import com.campus1.service.ManagerAdminService;
import com.campus1.service.ManagerManagerService;
import com.campus1.service.ManagerStudentsService;
import com.campus1.service.ManagerUserService;



@Controller
@RequestMapping("/manager")
public class ManagerManagerController {


    private final ManagerManagerService managerManagerService;
    private final ManagerUserService managerUserService;
    private final ManagerStudentsService studService;
    private final ManagerStudentsRepository stuRepo;
    private final ManagerAdminService managerAdminService;
    
    public ManagerManagerController(ManagerManagerService managerManagerService,ManagerUserService managerUserService,ManagerAdminService managerAdminService,ManagerStudentsService studService,ManagerStudentsRepository stuRepo) {
		this.managerManagerService = managerManagerService;
		this.managerUserService = managerUserService;
		this.managerAdminService = managerAdminService;
		this.studService=studService;
		this.stuRepo=stuRepo;
    	
    }
    
    @GetMapping("/loginform")
    public String loginForm() {
        return "forward:/Login.html";
    }

    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/Logout.html";
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<ManagerLoginResponseDto> login(@RequestParam String email,@RequestParam String password) {
        System.out.println(email);
    	ManagerManager managerManager = managerManagerService.getManagerByEmail(email);
        if (managerManager != null && managerManager.getPassword().equals(password)) {
            ManagerLoginResponseDto dto = new ManagerLoginResponseDto(
                "success",
                "Login successful",
                managerManager.getEmail()
            );
            return ResponseEntity.ok(dto);
        }
        ManagerLoginResponseDto dto = new ManagerLoginResponseDto(
            "error",
            "Invalid email or password",
            null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }
    
    
    @GetMapping("/profile")
    public ResponseEntity<ManagerManagerProfileDto> getManagerProfile(@RequestParam String email) {
    	ManagerManagerProfileDto dto = managerManagerService.getManagerProfileByEmail(email);
        return ResponseEntity.ok(dto);
    }
    
    
    

    // Update profile + picture
    @PutMapping("/profile/update")
    public ResponseEntity<String> updateProfile(@RequestPart("manager") ManagerManagerProfileDto dto,@RequestPart(value = "profilePicture", required = false) MultipartFile file) {
        try {
            managerManagerService.updateProfileWithImage(dto, file);
            return ResponseEntity.ok("Profile updated successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload image.");
        }
    }
    
    @PutMapping("/profile/remove-picture")
    public ResponseEntity<String> removeProfilePicture(@RequestParam String email) {
        try {
            managerManagerService.removeProfilePicture(email);
            return ResponseEntity.ok("Profile picture removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to remove profile picture.");
        }
    }
    
    @GetMapping("/api/admins")
    public ResponseEntity<List<ManagerAdminDto>> getAdmins(
            @RequestParam String managerEmail,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ManagerManager managerManager = managerManagerService.getManagerByEmail(managerEmail);
        List<ManagerAdminDto> admins = managerManagerService.getAdminsWithRecruitCount(managerManager.getId(), date);
        return ResponseEntity.ok(admins);
    }
    
    
    @PostMapping("/api/saveadmin")
    public  ResponseEntity<String> createAdmin(@RequestBody ManagerAdminCreateDto adminDto) {
    	ManagerManager m=managerManagerService.getManagerByEmail(adminDto.getManagerMail());
        managerAdminService.createAdminUnderManagerEmail(adminDto,m);
        return ResponseEntity.status(HttpStatus.CREATED).body("ManagerAdmin created successfully");
    }
    
    
    @GetMapping("/api/admin/{adminId}/users")
    @ResponseBody
    public ResponseEntity<List<ManagerUserPieModelDto>> getUsersByAdminAndDate(
            @PathVariable Long adminId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false)  String tab) {

        List<ManagerUserPieModelDto> users = studService.getUsersByAdminId(adminId, date, tab);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/api/user/{userId}")
    public ResponseEntity<ManagerStudentModelDto> getUserDetails(@PathVariable Long userId) {
        ManagerStudentModelDto user = studService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    
    
    @GetMapping("/api/mytasks")
    public ResponseEntity<List<ManagerUserAssignDto>> getMyTasks(@RequestParam String managerEmail,@RequestParam(required = false) String tab) {
        ManagerManager managerManager = managerManagerService.getManagerByEmail(managerEmail);
        List<ManagerUserAssignDto> tasks;
        if ("assigned".equalsIgnoreCase(tab)) {
            // ✅ Assigned users → ManagerStudents table
            List<ManagerStudents> managerStudents = studService.getTaskAssignedToManagerToAdmin(managerManager.getId());
            tasks = managerStudents.stream()
                            .map(ManagerUserAssignDto::new)
                            .toList();
        } else {
            // ✅ Not-assigned users → ManagerUsers table
            List<ManagerUsers> managerUsers = managerUserService.getTasksAssignedToManager(managerManager.getId());
            tasks = managerUsers.stream()
                         .map(ManagerUserAssignDto::new)
                         .toList();
        }

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/api/assignedtasks")
    public ResponseEntity<List<ManagerUserTrackingDto>> getTasks(@RequestParam String managerEmail,@RequestParam(required = false) String tab) {
        ManagerManager managerManager = managerManagerService.getManagerByEmail(managerEmail);
        List<ManagerStudents> managerStudents;
        if ("fileClosed".equals(tab)) {
        	managerStudents = studService.getClosedTasks(managerManager.getId());
        } else {
        	managerStudents = studService.getUnClosedTasks(managerManager.getId());
        }
        List<ManagerUserTrackingDto> tasks = managerStudents.stream().map(ManagerUserTrackingDto::new).toList();
        return ResponseEntity.ok(tasks);
    }
    
    
    @PostMapping("/api/assign-multiple-tasks")
    public ResponseEntity<String> assignMultipleTasks(@RequestBody ManagerAssignTaskDto task) {
        managerManagerService.assignMultipleTasks(task.getManagerEmail(),task.getAdminId(),task.getUserIds());
        return ResponseEntity.ok("Tasks assigned successfully");
    }
    
    
    @GetMapping("/filters")
    public ResponseEntity<Map<String, List<String>>> getFilterOptions() {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("callStatus", stuRepo.findDistinctCallStatus());
        filters.put("answeredCall", stuRepo.findDistinctAnsweredCall());
        filters.put("lookingForJob", stuRepo.findDistinctLookingForJob());
        filters.put("eligibleForClient", stuRepo.findDistinctEligibleForClient());
        filters.put("telephoneInterview", stuRepo.findDistinctTelephoneInterview());
        filters.put("hrInterview", stuRepo.findDistinctHrInterview());
        filters.put("operationRound", stuRepo.findDistinctOperationRound());
        filters.put("versantVoice", stuRepo.findDistinctVersantVoice());
        filters.put("documentation", stuRepo.findDistinctDocumentation());
        filters.put("offerLetter", stuRepo.findDistinctOfferLetter());
        filters.put("joining", stuRepo.findDistinctJoining());
        filters.put("finalStatus", stuRepo.findDistinctFinalStatus());

        return ResponseEntity.ok(filters);
    }
}