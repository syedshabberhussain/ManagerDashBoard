package com.campus1.service;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.campus1.dto.AdminTicketDto;
import com.campus1.dto.ManagerTicketDto;
import com.campus1.exception.ManagerNotFoundException;
import com.campus1.model.AdminTicket;
import com.campus1.model.Manager;
import com.campus1.model.ManagerTicket;
import com.campus1.repository.AdminTicketRepository;
import com.campus1.repository.ManagerRepository;
import com.campus1.repository.ManagerTicketRepository;

@Service
public class TicketService {
	
	 
	    private final ManagerRepository managerRepository;
	 
	    private final ManagerTicketRepository managerTicketRepository;

	    private final AdminTicketRepository adminTicketRepository;
	    
	    public TicketService(ManagerRepository managerRepository,ManagerTicketRepository managerTicketRepository,AdminTicketRepository adminTicketRepository) {
	    	this.managerRepository=managerRepository;
	    	this.managerTicketRepository=managerTicketRepository;
	    	this.adminTicketRepository=adminTicketRepository;
	    }

	    // ---------------- Manager ----------------
	
	    public ManagerTicketDto createManagerTicket(ManagerTicketDto dto) {
	        Manager manager = managerRepository.findByEmail(dto.getManagerEmail());
	        if(manager==null) {
	        	throw new ManagerNotFoundException("Manager Not found");
	        }
	                

	        ManagerTicket ticket = new ManagerTicket();
	        ticket.setSubject(dto.getSubject());
	        ticket.setCategory(dto.getCategory());
	        ticket.setPriority(dto.getPriority());
	        ticket.setDescription(dto.getDescription());
	        ticket.setDecision("Pending");
	        ticket.setStatus("Pending");
	        ticket.setCreatedAt(LocalDateTime.now());
	        ticket.setManager(manager);

	        ManagerTicket saved = managerTicketRepository.save(ticket);
	        return mapToManagerDto(saved);
	    }


	    public List<ManagerTicketDto> getManagerTickets(String managerEmail) {
	        return managerTicketRepository.findByManagerEmail(managerEmail)
	                .stream().map(this::mapToManagerDto)
	                .collect(Collectors.toList());
	    }

	    
	    public AdminTicketDto updateAdminTicket(AdminTicketDto ticketDto) {
	    	// Validate manager
	        Manager manager = managerRepository.findByEmail(ticketDto.getManagerEmail());
	        		if(manager==null) {
	                	throw new ManagerNotFoundException("Manager not Found");
	                }

	        // Find existing ticket
	        AdminTicket ticket = adminTicketRepository.findById(ticketDto.getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not Found"));

	        // Ensure ticket belongs to the manager
	     // Ensure ticket belongs to the manager (via Admin -> Manager)
	        if (ticket.getAdmin() == null || ticket.getAdmin().getManager() == null ||
	            !ticket.getAdmin().getManager().getEmail().equals(ticketDto.getManagerEmail())) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized to update this ticket");
	        }

	        // Apply partial updates
	        if(ticketDto.getDecision()!=null) {
	        	if(ticketDto.getDecision().equalsIgnoreCase("Accepted")){
	        		ticket.setStatus("Open");
	        	}
	        	if(ticketDto.getDecision().equalsIgnoreCase("Rejected")){
	        		ticket.setStatus("Rejected");
	        	}
	        	ticket.setDecision(ticketDto.getDecision());
	        }
	        if (ticketDto.getStatus() != null) {
	            ticket.setStatus(ticketDto.getStatus());
	        }
	        
	        ticket.setUpdatedAt(LocalDateTime.now());
	        // Save updated ticket
	        ticket = adminTicketRepository.save(ticket);
	        return mapToAdminDto(ticket);
	    }

	    
	    public List<AdminTicketDto> getAdminTicketsUnderManager(String managerEmail) {
	        return adminTicketRepository.findByAdminManagerEmail(managerEmail)
	                .stream().map(this::mapToAdminDto)
	                .collect(Collectors.toList());
	    }

	    // ---------------- Mapping ----------------
	    private ManagerTicketDto mapToManagerDto(ManagerTicket ticket) {
	        ManagerTicketDto dto = new ManagerTicketDto();
	        dto.setId(ticket.getId());
	        dto.setSubject(ticket.getSubject());
	        dto.setPriority(ticket.getPriority());
	        dto.setDescription(ticket.getDescription());
	        dto.setStatus(ticket.getStatus());
	        dto.setCreatedAt(ticket.getCreatedAt());
	        dto.setUpdatedAt(ticket.getUpdatedAt());
	        dto.setManagerEmail(ticket.getManager() != null ? ticket.getManager().getEmail() : null);
	        return dto;
	    }

	    private AdminTicketDto mapToAdminDto(AdminTicket ticket) {
	        AdminTicketDto dto = new AdminTicketDto();
	        dto.setId(ticket.getId());
	        dto.setSubject(ticket.getSubject());
	        dto.setCategory(ticket.getCategory());
	        dto.setPriority(ticket.getPriority());
	        dto.setDescription(ticket.getDescription());
	        dto.setStatus(ticket.getStatus());
	        dto.setDecision(ticket.getDecision());
	        dto.setCreatedAt(ticket.getCreatedAt());
	        dto.setUpdatedAt(ticket.getUpdatedAt());
	        dto.setAdminEmail(ticket.getAdmin() != null ? ticket.getAdmin().getEmail() : null);
	        return dto;
	    }
}
