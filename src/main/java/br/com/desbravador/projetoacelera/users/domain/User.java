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
@Table(name = "users")
public class User implements Model, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	private boolean admin;
	
	private boolean is_bloqued;
	
	private boolean is_active;
	
	private String token;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;
	
	public User() {
		
	}
	
	public User(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.admin = user.isAdmin();
		this.is_bloqued = user.isBloqued();
		this.is_active = user.isActive();
		this.token = user.getToken();
		this.created_at = user.getCreated_at();
		this.updated_at = user.getUpdated_at();
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
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




	public boolean isBloqued() {
		return is_bloqued;
	}




	public void setIsBloqued(boolean is_bloqued) {
		this.is_bloqued = is_bloqued;
	}




	public boolean isActive() {
		return is_active;
	}




	public void setIsActive(boolean is_active) {
		this.is_active = is_active;
	}




	public String getToken() {
		return token;
	}




	public void setToken(String token) {
		this.token = token;
	}




	public Date getCreated_at() {
		return created_at;
	}




	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}




	public Date getUpdated_at() {
		return updated_at;
	}




	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String getAuthority() {
		
		if(this.admin)
			return "ADMIN";
		
		return "USER";
	}
	
	
}
