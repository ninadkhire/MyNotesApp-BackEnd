package com.ninadkhire.pocmynotesapp.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ninadkhire.pocmynotesapp.models.ERole;
import com.ninadkhire.pocmynotesapp.models.Note;
import com.ninadkhire.pocmynotesapp.models.Role;
import com.ninadkhire.pocmynotesapp.models.User;
import com.ninadkhire.pocmynotesapp.repository.NoteRepository;
import com.ninadkhire.pocmynotesapp.repository.RoleRepository;
import com.ninadkhire.pocmynotesapp.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	User testUser;
	Long testNoteID;
	
	@BeforeAll
	void setup() {
		
		User tester = new User("tester", "tester@mynotesapp.com", "123456");
		
		Set<Role> roles = new HashSet<>();
		
		Optional<Role> role_admin = roleRepository.findByName(ERole.ROLE_ADMIN);
		
		if(role_admin.isPresent())
			roles.add(role_admin.get());
		//roles.add(new Role(ERole.ROLE_MODERATOR));
		//roles.add(new Role(ERole.ROLE_USER));
		
		tester.setRoles(roles);
		
		tester.setNotes(null);
		
		Assertions.assertDoesNotThrow(()->{
			testUser = userRepository.save(tester);
			
			System.out.println("Tester user added at id "+testUser.getId());
		});
	}
	
	@AfterAll
	void tearDown() {
		
		
		Assertions.assertDoesNotThrow(()->{
			userRepository.deleteById(testUser.getId());
		});
		System.out.println("Tester user deleted");
		
	}

	@Test
	@DisplayName("Create new note")
	@Order(1)
	void testCreateNewNote() {
		Assertions.assertDoesNotThrow(()->{
			Note testNote=new Note("Test Note Title", "This is a test note!");
			//testNote.setUser(testUser);
			//noteRepository.save(testNote);
			testUser.addNote(testNote);
			testUser = userRepository.save(testUser);			
		});
		
		Assertions.assertTrue(testUser.getNotes().size() > 0);
	}
	
	@Test
	@DisplayName("Update a note")
	@Order(2)
	void testUpdateNote() {
		
		Assertions.assertDoesNotThrow(()->{
			testUser.getNotes().forEach((note)->{
				note.setTitle("Updated Title");
				note.setNote("Updated note");
				System.out.println("Note updated "+note.getId());
			});
			
			testUser = userRepository.save(testUser);
		});
		
	}
	
	@Test
	@DisplayName("Delete note")
	@Order(3)
	void testDeleteNote() {
		
		Assertions.assertDoesNotThrow(()->{
			for(Note note: testUser.getNotes()) {
				System.out.println(note.getId()+" "+note.getTitle()+" "+note.getNote());
				noteRepository.deleteById(note.getId());
			}
			
			testUser.setNotes(null);
			
			testUser = userRepository.save(testUser);
		});
		
		Assertions.assertTrue(noteRepository.findByUser_Id(testUser.getId()).size() == 0);
		
	}

}
