package com.ninadkhire.pocmynotesapp.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninadkhire.pocmynotesapp.models.*;
import com.ninadkhire.pocmynotesapp.payload.request.*;
import com.ninadkhire.pocmynotesapp.payload.response.*;
import com.ninadkhire.pocmynotesapp.repository.*;
import com.ninadkhire.pocmynotesapp.security.jwt.*;
import com.ninadkhire.pocmynotesapp.security.services.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(HttpServletRequest request){
		final String authorization = request.getHeader("Authorization");
		
		System.out.println("Signin request received");
		
		if(authorization != null && authorization.toLowerCase().startsWith("basic")) {
			System.out.println("Header found");
			
			String base64Credentials = authorization.substring("Basic".length()).trim();
			
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			
			String credentialsString = new String(credDecoded, StandardCharsets.UTF_8);
			
			//credentials = username:password
			final String[] credentials = credentialsString.split(":", 2);
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt, 
													 userDetails.getId(), 
													 userDetails.getUsername(), 
													 userDetails.getEmail(), 
													 roles));
		} else {
			System.out.println("Header NOT found");
			
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Invalid credentials"));
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(HttpServletRequest request) {
		//Get the credentials from the request header
		final String authorization = request.getHeader("Authorization");
		
		System.out.println("Signup request received");
		
		if(authorization != null && authorization.toLowerCase().startsWith("basic")) {
			System.out.println("Header found");
			
			String base64Credentials = authorization.substring("Basic".length()).trim();
			
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			
			String credentialsString = new String(credDecoded, StandardCharsets.UTF_8);
			
			//credentials = username:password
			final String[] credentials = credentialsString.split(":", 3);
			
			//Registering the user
			
			if (userRepository.existsByUsername(credentials[0])
					|| credentials[0].equals("tester")) {// tester is username of the user used for testing
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Username is already taken!"));
			}

			if (userRepository.existsByEmail(credentials[1])) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Email is already in use!"));
			}

			// Create new user's account
			User user = new User(credentials[0], 
					credentials[1],
					encoder.encode(credentials[2]));
			
			System.out.println(credentials[0]+" "+credentials[1]+" "+credentials[2]);

			Set<String> strRoles = null;//Not yet implemented
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			user.setRoles(roles);
			
			user.setNotes(null);
			
			userRepository.save(user);
			
			System.out.println("User registered successfully");

			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		} else {
			System.out.println("Header NOT found");
			
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Invalid credentials"));
		}
		
	}

}
