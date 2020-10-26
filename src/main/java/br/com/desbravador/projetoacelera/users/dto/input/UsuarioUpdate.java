package br.com.desbravador.projetoacelera.users.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.web.DTO;

public class UsuarioUpdate implements DTO<Usuario> {  
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String senha;
	
	@NotNull
	private Boolean administrador;
	
	public UsuarioUpdate() {
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	@Override
	public Usuario toEntity() {
		Usuario user = new Usuario();
		user.setNome(this.nome);
		user.setSenha(new BCryptPasswordEncoder().encode(this.senha));
		user.setAdministrador(this.administrador);
		return user;
	}

	
	
}
