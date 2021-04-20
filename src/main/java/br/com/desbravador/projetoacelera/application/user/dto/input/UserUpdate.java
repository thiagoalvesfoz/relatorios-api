package br.com.desbravador.projetoacelera.application.user.dto.input;

import br.com.desbravador.projetoacelera.application.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserUpdate {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	
	@NotNull
	private Boolean admin;

	public User toEntity() {
		User user = new User();
		user.setName(this.name);
		user.setPassword(new BCryptPasswordEncoder().encode(this.password));
		user.setAdmin(this.admin);
		return user;
	}
}
