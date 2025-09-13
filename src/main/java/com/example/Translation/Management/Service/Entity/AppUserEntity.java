package com.example.Translation.Management.Service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUserEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq_gen")
	    @SequenceGenerator(name = "app_user_seq_gen", sequenceName = "app_user_seq", allocationSize = 1)
	    private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password; // store encoded password
	
    @Column(nullable = false)
    private String role = "USER"; // simple role handling

    public AppUserEntity() {}

    public AppUserEntity(String username, String password) {
        this.username = username;
        this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
 
    
	
}
