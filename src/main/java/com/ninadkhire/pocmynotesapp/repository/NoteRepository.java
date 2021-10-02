package com.ninadkhire.pocmynotesapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninadkhire.pocmynotesapp.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	
	List<Note> findByUser_Id(Long userId);
	
}
