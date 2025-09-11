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

import com.campus1.dto.AdminTicketDto;
import com.campus1.dto.ManagerTicketDto;
import com.campus1.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	

	    private final TicketService ticketsService;
	    
	    public TicketController(TicketService ticketsService) {
	    	this.ticketsService=ticketsService;
	    }
	    


	    @PostMapping("/manager/create")
	    public ResponseEntity<ManagerTicketDto> createManagerTicket(@RequestBody ManagerTicketDto ticketDto){
	       ManagerTicketDto ticket = ticketsService.createManagerTicket(ticketDto);
	       return new ResponseEntity<>(ticket,HttpStatus.CREATED);
	    }

	    @GetMapping("/manager")
	    public ResponseEntity<List<ManagerTicketDto>> getManagerTickets(@RequestParam String managerEmail) {
	        return new ResponseEntity<>(ticketsService.getManagerTickets(managerEmail), HttpStatus.OK);
	    }

	    @PatchMapping("/admin/update")
	    public ResponseEntity<AdminTicketDto> updateAdminTicket(@RequestBody AdminTicketDto dto) {
	    	System.out.println(dto);
	        return new ResponseEntity<>(ticketsService.updateAdminTicket(dto), HttpStatus.OK);
	    }

	    @GetMapping("/manager/admins")
	    public ResponseEntity<List<AdminTicketDto>> getAdminTicketsByManager(@RequestParam String managerEmail) {
	        return new ResponseEntity<>(ticketsService.getAdminTicketsUnderManager(managerEmail), HttpStatus.OK);
	    }
}
