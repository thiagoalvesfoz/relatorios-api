package br.com.desbravador.projetoacelera.model.entities;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;  
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	private boolean administrador;
	
	private boolean bloqueado;
	
	private boolean ativo;
	
	private String token;
	
	private Instant criado_em;
	
	private Instant atualizado_em;
	
	public Usuario() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public Instant getCriado_em() {
		return criado_em;
	}

	public void setCriado_em(Instant criado_em) {
		this.criado_em = criado_em;
	}

	public Instant getAtualizado_em() {
		return atualizado_em;
	}

	public void setAtualizado_em(Instant atualizado_em) {
		this.atualizado_em = atualizado_em;
	}
	
	
}
