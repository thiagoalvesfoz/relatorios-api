package br.com.desbravador.projetoacelera.users.dto;

import br.com.desbravador.projetoacelera.users.domain.User;

import java.io.Serializable;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;  
	
	private String name;
	
	private String email;	
	
	private String authority;

	
	public UserDto(User user) {
		this.name = user.getName();
		this.id = user.getId();
		this.email = user.getEmail();
		this.authority = user.getAuthority();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
