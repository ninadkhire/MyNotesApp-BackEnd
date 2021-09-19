package com.ninadkhire.pocmynotesapp.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninadkhire.pocmynotesapp.exception.ResourceNotFoundException;
import com.ninadkhire.pocmynotesapp.model.Note;
import com.ninadkhire.pocmynotesapp.repository.NoteRepository;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/")
public class NotesController {
	
	@Autowired
	private NoteRepository noteRepository;
	
	@GetMapping("/ping")
	public String ping() {
		return "Access granted!";
	}
	
	@GetMapping("/")
	public String getHomePage(){
		return "Home Page";
	}
	
	@GetMapping("/notes/user")
	public List<Note> getUserNotes(@PathVariable String email){
		return noteRepository.findByUserEmail(email);
	}
	
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	@PostMapping("/notes")
	public Note createNote(@RequestBody Note note) {
		return noteRepository.save(note);
	}
	
	@GetMapping("/notes/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable Long id){
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note with id "+id+" does not exists"));
		return ResponseEntity.ok(note);
	}
	
	@PutMapping("/notes/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note updatedNote){
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note with id "+id+" does not exists"));
		
		note.setTitle(updatedNote.getTitle());
		note.setNote(updatedNote.getNote());
		
		updatedNote = noteRepository.save(note);
		
		return ResponseEntity.ok(updatedNote);
	}
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteNote(@PathVariable Long id){
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note with id "+id+" does not exists"));
		
		noteRepository.delete(note);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", true);
		
		return ResponseEntity.ok(response);
	}
	
}
