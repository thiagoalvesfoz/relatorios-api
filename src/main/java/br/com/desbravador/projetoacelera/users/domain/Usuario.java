package br.com.desbravador.projetoacelera.users.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.desbravador.projetoacelera.web.Model;

@Entity
@Table(name = "usuarios")
public class Usuario implements Model, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  
	
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String senha;
	
	private boolean administrador;
	
	private boolean bloqueado;
	
	private boolean ativo;
	
	private String token;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date criado_em;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizado_em;
	
	public Usuario() {
		
	}
	
	public Usuario(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.administrador = usuario.isAdministrador();
		this.bloqueado = usuario.isBloqueado();
		this.ativo = usuario.isAtivo();
		this.token = usuario.token;
	}
	
	@Override
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

	public Date getCriado_em() {
		return criado_em;
	}

	public void setCriado_em(Date criado_em) {
		this.criado_em = criado_em;
	}

	public Date getAtualizado_em() {
		return atualizado_em;
	}

	public void setAtualizado_em(Date atualizado_em) {
		this.atualizado_em = atualizado_em;
	}

	@Override
	public String getAuthority() {
		
		if(this.administrador)
			return "ADMIN";
		
		return "USER";
	}
	
	
}
