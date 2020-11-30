package br.com.desbravador.projetoacelera.users.dto;

import br.com.desbravador.projetoacelera.users.domain.Usuario;

public class UsuarioDto {

	private Long id;  
	
	private String nome;
	
	private String email;	
	
	private String authority;

	
	public UsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.authority = usuario.getAuthority();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
