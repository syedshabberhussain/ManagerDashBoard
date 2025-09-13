package com.campus1.controller;



import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campus1.dto.ManagerAdminTicketDto;
import com.campus1.dto.ManagerManagerTicketDto;
import com.campus1.service.ManagerTicketService;

@Controller
@RequestMapping("/tickets")
public class ManagerTicketController {
	
	

	    private final ManagerTicketService ticketsService;
	    
	    public ManagerTicketController(ManagerTicketService ticketsService) {
	    	this.ticketsService=ticketsService;
	    }
	    


	    @PostMapping("/manager/create")
	    public ResponseEntity<ManagerManagerTicketDto> createManagerTicket(@RequestBody ManagerManagerTicketDto ticketDto){
	       ManagerManagerTicketDto ticket = ticketsService.createManagerTicket(ticketDto);
	       return new ResponseEntity<>(ticket,HttpStatus.CREATED);
	    }

	    @GetMapping("/manager")
	    public ResponseEntity<List<ManagerManagerTicketDto>> getManagerTickets(@RequestParam String managerEmail) {
	        return new ResponseEntity<>(ticketsService.getManagerTickets(managerEmail), HttpStatus.OK);
	    }

	    @PatchMapping("/admin/update")
	    public ResponseEntity<ManagerAdminTicketDto> updateAdminTicket(@RequestBody ManagerAdminTicketDto dto) {
	    	System.out.println(dto);
	        return new ResponseEntity<>(ticketsService.updateAdminTicket(dto), HttpStatus.OK);
	    }

	    @GetMapping("/manager/admins")
	    public ResponseEntity<List<ManagerAdminTicketDto>> getAdminTicketsByManager(@RequestParam String managerEmail) {
	        return new ResponseEntity<>(ticketsService.getAdminTicketsUnderManager(managerEmail), HttpStatus.OK);
	    }
}
