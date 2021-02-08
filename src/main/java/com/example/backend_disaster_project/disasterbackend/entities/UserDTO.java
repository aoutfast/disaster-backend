package com.example.backend_disaster_project.disasterbackend.entities;

public class UserDTO {
	private String username;
	private String password;

	public UserDTO(String password, String username){
		this.password = password;
		this.username = username;
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
}