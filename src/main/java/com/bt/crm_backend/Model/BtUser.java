package com.bt.crm_backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class BtUser {

	@Id
	private int id;
	
	private String username;
	
//	@Column(name="email",length=225)
//	private String email;
	
	@Column(name="password",length=225)
	private String password;
	

		// Getter Setter Starts here
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}


//@Column(name="user_id",length=45)   //	@GeneratedValue(strategy = GenerationType.IDENTITY)


