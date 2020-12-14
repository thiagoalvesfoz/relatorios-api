package br.com.desbravador.projetoacelera.users.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.web.DTO;

public class UserUpdate implements DTO<User> {  
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	
	@NotNull
	private Boolean admin;
	
	public UserUpdate() {
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



	public Boolean getAdmin() {
		return admin;
	}



	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}



	@Override
	public User toEntity() {
		User user = new User();
		user.setName(this.name);
		user.setPassword(new BCryptPasswordEncoder().encode(this.password));
		user.setAdmin(this.admin);
		return user;
	}

	
	
}
