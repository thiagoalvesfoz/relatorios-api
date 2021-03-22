package br.com.desbravador.projetoacelera.users.domain;

import br.com.desbravador.projetoacelera.web.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.Instant;

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
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;
	
	public User() {
		
	}
	
	public User(User user) {
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

	public User(Long id, String user, String email, String password, boolean admin, boolean active) {
		this.id = id;
		this.name = user;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.active = active;
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
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String getAuthority() {
		if(this.admin)
			return "ROLE_ADMIN";
		
		return "ROLE_USER";
	}

	@Override
	public String toString() {
		return "\n" + "[ " 		+
				"id: " 			+ 	getId() 	+ ", " +
				"Username: " 	+ 	getName() 	+ ", " +
				"Email: " 		+ 	getEmail() 	+ ", " +
				"active: " 		+ 	isActive() 	+ ", " +
				"authority: "	+	getAuthority() + " ]\n";
	}

}
