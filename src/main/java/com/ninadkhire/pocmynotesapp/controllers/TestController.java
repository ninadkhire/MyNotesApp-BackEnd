package com.ninadkhire.pocmynotesapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninadkhire.pocmynotesapp.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user_notes")
	public String getUserNotes() {
		return null;
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String userDetails="LOL";
		
		if(principal instanceof UserDetailsImpl) {
			userDetails = ((UserDetailsImpl)principal).getUsername()+", "+((UserDetailsImpl)principal).getEmail();
		} else {
			userDetails = "Invalid User: "+principal.toString();
		}
		return "User Content. User Details = "+userDetails;
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
