package br.com.desbravador.projetoacelera.users.dto.input;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.web.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserInput implements DTO<User> {  
	
	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;
	
	private boolean admin;

	public UserInput() {
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public User toEntity() {
		User user = new User();
		user.setName(this.name);
		user.setEmail(this.email);
		user.setAdmin(this.admin);
		return user;
	}

}
