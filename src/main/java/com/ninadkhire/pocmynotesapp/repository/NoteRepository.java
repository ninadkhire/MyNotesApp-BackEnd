package com.ninadkhire.pocmynotesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninadkhire.pocmynotesapp.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	
}
