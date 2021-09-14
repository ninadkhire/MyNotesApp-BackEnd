package com.ninadkhire.pocmynotesapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninadkhire.pocmynotesapp.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findNotesByUserEmail(String email);
	
}
