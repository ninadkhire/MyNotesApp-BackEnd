package com.ninadkhire.pocmynotesapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private String password;
	
	/*@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name="users_notes",
		joinColumns=@JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id")
	)
	private Collection<Note> notes;*/

	public User() {
	}

	public User(String username, String email, String password/* , Collection<Note> notes */) {
		this.username = username;
		this.email = email;
		this.password = password;
//		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * public Collection<Note> getNotes() { return notes; }
	 * 
	 * public void setNotes(Collection<Note> notes) { this.notes = notes; }
	 */
	
}
