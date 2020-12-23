package br.com.desbravador.projetoacelera.users.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.web.DTO;

public class UserInput implements DTO<User> {  
	
	@NotBlank
	private String name;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		user.setPassword(new BCryptPasswordEncoder().encode(this.password));
		System.out.println(user.getPassword());
		user.setAdmin(this.admin);
		return user;
	}

}
