package br.com.desbravador.projetoacelera.application.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
@Data
public class User implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  
	
	private String name;
	private String email;
	private boolean admin;
	private boolean blocked;
	private boolean active;
	private String token;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private Instant createdAt;

	@CreationTimestamp
	@Column(name = "updated_at")
	private Instant updatedAt;

	@JsonIgnore
	private String password;
	
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
