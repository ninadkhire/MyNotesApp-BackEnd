package com.ninadkhire.pocmynotesapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninadkhire.pocmynotesapp.models.Note;
import com.ninadkhire.pocmynotesapp.models.User;
import com.ninadkhire.pocmynotesapp.payload.request.NoteRequest;
import com.ninadkhire.pocmynotesapp.payload.response.MessageResponse;
import com.ninadkhire.pocmynotesapp.repository.UserRepository;
import com.ninadkhire.pocmynotesapp.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class MainController {
	
	@Autowired
	UserRepository userRepository;
	
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@PostMapping("/notes")
	public ResponseEntity<?> createNote(@RequestBody NoteRequest note) {
		//retrieve existing user object
		User user=null;
		
		if(getCurrentUser() instanceof UserDetailsImpl) {
			Optional<User> found = userRepository.findById(((UserDetailsImpl)getCurrentUser()).getId());
			
			if(found.isPresent()) {
				user = found.get();
			}
		}
		
		//add new note to the user object
		if(user != null) {
			user.addNote(new Note(note.getTitle(), note.getNote()));
		}
		
		//save the user object
		Long id = userRepository.save(user).getId();
		
		System.out.println("Note stored at id "+id);
		
		return ResponseEntity.ok(new MessageResponse("Note stored at id "+id));
	}
	
	@PutMapping("/notes")
	public ResponseEntity<?> updateNote(@RequestBody NoteRequest updatedNote) {
		//retrieve existing user object
		User user=null;
		
		if(getCurrentUser() instanceof UserDetailsImpl) {
			Optional<User> found = userRepository.findById(((UserDetailsImpl)getCurrentUser()).getId());
			
			if(found.isPresent()) {
				user = found.get();
			}
		}
		
		if(user == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
		
		//retrieve the note to be updated
		ArrayList<Note> notes = (ArrayList<Note>)user.getNotes();
		
		//update the note
		notes.forEach((note)->{
			if(note.getId().equals(updatedNote.getId())) {
				note.setTitle(updatedNote.getTitle());
				note.setNote(updatedNote.getNote());
			}
		});
		
		//save the user object
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User updated"));
	}
	
	@DeleteMapping("/notes/{noteIdToBeDeleted}")
	public ResponseEntity<?> deleteNote(@PathVariable Long noteIdToBeDeleted) {
		//retrieve existing user object
		User user=null;
		
		if(getCurrentUser() instanceof UserDetailsImpl) {
			Optional<User> found = userRepository.findById(((UserDetailsImpl)getCurrentUser()).getId());
			
			if(found.isPresent()) {
				user = found.get();
			}
		}
		
		if(user == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
		
		//retrieve the note to be updated
		ArrayList<Note> notes = (ArrayList<Note>)user.getNotes();
		
		//Delete the required note
		notes.forEach((note)->{
			if(note.getId().equals(noteIdToBeDeleted)) {
				notes.remove(note);
			}
		});
		
		//save the user object
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("Deleted"));
	}
	
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		//retrieve the user object
		User user=null;
		
		if(getCurrentUser() instanceof UserDetailsImpl) {
			Optional<User> found = userRepository.findById(((UserDetailsImpl)getCurrentUser()).getId());
			
			if(found.isPresent()) {
				user = found.get();
			}
		}
		
		if(user == null) {
			return null;
		}
		
		//retrieve and return the notes object
		return user.getNotes();
	}
	
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable Long id) {
		//retrieve the user object
		User user=null;
		
		if(getCurrentUser() instanceof UserDetailsImpl) {
			Optional<User> found = userRepository.findById(((UserDetailsImpl)getCurrentUser()).getId());
			
			if(found.isPresent()) {
				user = found.get();
			}
		}
		
		if(user == null) {
			return null;
		}
		
		//retrieve the user's note list
		//ArrayList<Note> notes = new ArrayList<>(user.getNotes());
		
		Note noteById = null;
		
		for(Note note: user.getNotes()) {
			if(note.getId().equals(id)) {
				noteById = note;
			}
		}
		
		//return the requested note
		return noteById;
	} 

}
