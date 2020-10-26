package br.com.desbravador.projetoacelera.users.dto.input;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.web.DTO;

public class UsuarioInput implements DTO<Usuario> {  
	
	private String nome;
	
	private String senha;
	
	private String email;
		
	private String token;

	public UsuarioInput() {
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Usuario toEntity() {
		Usuario user = new Usuario();
		user.setNome(this.nome);
		user.setEmail(this.email);
		user.setSenha(new BCryptPasswordEncoder().encode(this.senha));
		return user;
	}

	
}
