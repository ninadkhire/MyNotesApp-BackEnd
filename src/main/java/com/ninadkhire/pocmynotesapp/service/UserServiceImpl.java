/*
 * package com.ninadkhire.pocmynotesapp.service;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * 
 * import com.ninadkhire.pocmynotesapp.model.User; import
 * com.ninadkhire.pocmynotesapp.repository.NoteRepository; import
 * com.ninadkhire.pocmynotesapp.web.dto.UserRegistrationDto;
 * 
 * public class UserServiceImpl implements UserService {
 * 
 * private NoteRepository userRepository;
 * 
 * @Autowired private BCryptPasswordEncoder passwordEncoder;
 * 
 * public UserServiceImpl(NoteRepository userRepository) { super();
 * this.userRepository = userRepository; }
 * 
 * @Override public UserDetails loadUserByUsername(String username) throws
 * UsernameNotFoundException { // TODO Auto-generated method stub return null; }
 * 
 * @Override public User save(UserRegistrationDto userDto) { User user=new
 * User(userDto.getUsername(), userDto.getEmail(),
 * passwordEncoder.encode(userDto.getPassword())); return null; }
 * 
 * 
 * 
 * }
 */