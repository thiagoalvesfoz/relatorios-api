package br.com.desbravador.projetoacelera.users.domain;

import br.com.desbravador.projetoacelera.web.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User implements Model, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  
	
	private String name;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private boolean admin;
	
	private boolean blocked;
	
	private boolean active;
	
	private String token;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	
	public User() {
		
	}
	
	public User(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.admin = user.isAdmin();
		this.blocked = user.isBlocked();
		this.active = user.isActive();
		this.token = user.getToken();
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
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
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String getAuthority() {
		
		if(this.admin)
			return "ADMIN";
		
		return "USER";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("REGISTRADO COM SUCESSO!").append("\n\n");
		builder.append("Username: ");
		builder.append(getName()).append("\n");
		builder.append("Email: ");
		builder.append(getEmail()).append("\n\n");
		builder.append("Você deve ativar sua conta antes para ter o acesso completo no sistema!");
		return builder.toString();
	}
}
