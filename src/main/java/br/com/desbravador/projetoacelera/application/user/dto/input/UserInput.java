package br.com.desbravador.projetoacelera.application.user.dto.input;

import br.com.desbravador.projetoacelera.application.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserInput {
	
	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;

	private boolean admin;

	public User toEntity() {
		User user = new User();
		user.setName(this.name);
		user.setEmail(this.email);
		user.setAdmin(this.admin);
		return user;
	}

}
