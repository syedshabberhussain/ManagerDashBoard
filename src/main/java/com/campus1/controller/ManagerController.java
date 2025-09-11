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
import com.campus1.dto.AdminCreateDto;
import com.campus1.dto.AdminDto;
import com.campus1.dto.AssignTaskDto;
import com.campus1.dto.LoginResponseDto;
import com.campus1.dto.ManagerProfileDto;
import com.campus1.dto.UserAssignDto;
import com.campus1.dto.UserModelDto;
import com.campus1.dto.UserPieModelDto;
import com.campus1.dto.UserTrackingDto;
import com.campus1.model.Manager;
import com.campus1.model.Users;
import com.campus1.repository.UserRepository;
import com.campus1.service.AdminService;
import com.campus1.service.ManagerService;
import com.campus1.service.UserService;



@Controller
@RequestMapping("/manager")
public class ManagerController {


    private final ManagerService managerService;
    private final UserService userService;
    private final UserRepository usersRepo;
    private final AdminService adminService;
    
    public ManagerController(ManagerService managerService,UserService userService,AdminService adminService,UserRepository usersRepo) {
		this.managerService = managerService;
		this.userService = userService;
		this.adminService = adminService;
		this.usersRepo= usersRepo;
    	
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
    public ResponseEntity<LoginResponseDto> login(@RequestParam String email,@RequestParam String password) {
        System.out.println(email);
    	Manager manager = managerService.getManagerByEmail(email);
        if (manager != null && manager.getPassword().equals(password)) {
            LoginResponseDto dto = new LoginResponseDto(
                "success",
                "Login successful",
                manager.getEmail()
            );
            return ResponseEntity.ok(dto);
        }
        LoginResponseDto dto = new LoginResponseDto(
            "error",
            "Invalid email or password",
            null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }
    
    
    @GetMapping("/profile")
    public ResponseEntity<ManagerProfileDto> getManagerProfile(@RequestParam String email) {
    	ManagerProfileDto dto = managerService.getManagerProfileByEmail(email);
        return ResponseEntity.ok(dto);
    }
    
    
    

    // Update profile + picture
    @PutMapping("/profile/update")
    public ResponseEntity<String> updateProfile(@RequestPart("manager") ManagerProfileDto dto,@RequestPart(value = "profilePicture", required = false) MultipartFile file) {
        try {
            managerService.updateProfileWithImage(dto, file);
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
            managerService.removeProfilePicture(email);
            return ResponseEntity.ok("Profile picture removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to remove profile picture.");
        }
    }
    
    @GetMapping("/api/admins")
    public ResponseEntity<List<AdminDto>> getAdmins(
            @RequestParam String managerEmail,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Manager manager = managerService.getManagerByEmail(managerEmail);
        List<AdminDto> admins = managerService.getAdminsWithRecruitCount(manager.getId(), date);
        return ResponseEntity.ok(admins);
    }
    
    
    @PostMapping("/api/saveadmin")
    public  ResponseEntity<String> createAdmin(@RequestBody AdminCreateDto adminDto) {
    	Manager m=managerService.getManagerByEmail(adminDto.getManagerMail());
        adminService.createAdminUnderManagerEmail(adminDto,m);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");
    }
    
    
    @GetMapping("/api/admin/{adminId}/users")
    @ResponseBody
    public ResponseEntity<List<UserPieModelDto>> getUsersByAdminAndDate(
            @PathVariable Long adminId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false)  String tab) {

        List<UserPieModelDto> users = userService.getUsersByAdminId(adminId, date, tab);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/api/user/{userId}")
    public ResponseEntity<UserModelDto> getUserDetails(@PathVariable Long userId) {
        UserModelDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    
    
    @GetMapping("/api/mytasks")
    public ResponseEntity<List<UserAssignDto>> getMyTasks(@RequestParam String managerEmail,@RequestParam(required = false) String tab) {
        Manager manager = managerService.getManagerByEmail(managerEmail);
        List<Users> users;
        if("assigned".equals(tab)) {
        	users=userService.getTaskAssignedToManagerToAdmin(manager.getId());
        	System.out.println(users);
        }
        else {
        	users=userService.getTasksAssignedToManager(manager.getId());
        }
        List<UserAssignDto> tasks = users.stream().map(UserAssignDto::new).toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/api/assignedtasks")
    public ResponseEntity<List<UserTrackingDto>> getTasks(@RequestParam String managerEmail,@RequestParam(required = false) String tab) {
        Manager manager = managerService.getManagerByEmail(managerEmail);
        List<Users> users;
        if ("fileClosed".equals(tab)) {
            users = userService.getClosedTasks(manager.getId());
        } else {
            users = userService.getUnClosedTasks(manager.getId());
        }
        List<UserTrackingDto> tasks = users.stream().map(UserTrackingDto::new).toList();
        return ResponseEntity.ok(tasks);
    }
    
    
    @PostMapping("/api/assign-multiple-tasks")
    public ResponseEntity<String> assignMultipleTasks(@RequestBody AssignTaskDto task) {
        managerService.assignMultipleTasks(task.getManagerEmail(),task.getAdminId(),task.getUserIds());
        return ResponseEntity.ok("Tasks assigned successfully");
    }
    
    
    @GetMapping("/filters")
    public ResponseEntity<Map<String, List<String>>> getFilterOptions() {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("callStatus", usersRepo.findDistinctCallStatus());
        filters.put("answeredCall", usersRepo.findDistinctAnsweredCall());
        filters.put("lookingForJob", usersRepo.findDistinctLookingForJob());
        filters.put("eligibleForClient", usersRepo.findDistinctEligibleForClient());
        filters.put("telephoneInterview", usersRepo.findDistinctTelephoneInterview());
        filters.put("hrInterview", usersRepo.findDistinctHrInterview());
        filters.put("operationRound", usersRepo.findDistinctOperationRound());
        filters.put("versantVoice", usersRepo.findDistinctVersantVoice());
        filters.put("documentation", usersRepo.findDistinctDocumentation());
        filters.put("offerLetter", usersRepo.findDistinctOfferLetter());
        filters.put("joining", usersRepo.findDistinctJoining());
        filters.put("finalStatus", usersRepo.findDistinctFinalStatus());

        return ResponseEntity.ok(filters);
    }
}