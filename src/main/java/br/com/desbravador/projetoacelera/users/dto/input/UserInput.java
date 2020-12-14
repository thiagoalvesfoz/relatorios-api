package br.com.desbravador.projetoacelera.users.dto.input;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.web.DTO;

public class UserInput implements DTO<User> {  
	
	private String name;
	
	private String password;
	
	private boolean admin;
	
	private String email;

	public UserInput() {
	}
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	@Override
	public User toEntity() {
		User user = new User();
		user.setName(this.name);
		user.setEmail(this.email);
		user.setPassword(new BCryptPasswordEncoder().encode(this.password));
		user.setAdmin(this.admin);
		return user;
	}

	
	
}
