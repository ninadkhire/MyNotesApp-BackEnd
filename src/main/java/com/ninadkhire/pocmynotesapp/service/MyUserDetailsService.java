
package com.ninadkhire.pocmynotesapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ninadkhire.pocmynotesapp.model.MyUserDetails;
import com.ninadkhire.pocmynotesapp.model.User;
import com.ninadkhire.pocmynotesapp.repository.UserRepository;
import com.ninadkhire.pocmynotesapp.web.dto.UserRegistrationDto;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	//User save(UserRegistrationDto userDto);

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(userName);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Username Not found: "+userName));
		
		return user.map(MyUserDetails::new).get();
	}
}
