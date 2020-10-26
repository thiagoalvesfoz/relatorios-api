package br.com.desbravador.projetoacelera.users.dto.input;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.web.DTO;

public class UsuarioInput implements DTO<Usuario> {  
	
	private String nome;
	
	private String senha;
	
	private boolean administrador;
	
	private String email;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	@Override
	public Usuario toEntity() {
		Usuario user = new Usuario();
		user.setNome(this.nome);
		user.setEmail(this.email);
		user.setSenha(new BCryptPasswordEncoder().encode(this.senha));
		user.setAdministrador(this.administrador);
		return user;
	}

	
	
}
