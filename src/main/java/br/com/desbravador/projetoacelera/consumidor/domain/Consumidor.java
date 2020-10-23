package br.com.desbravador.projetoacelera.consumidor.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.desbravador.projetoacelera.web.Model;

@Entity
@Table(name = "consumidores")
public class Consumidor implements Model {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long id_produto;
	private String client_secret;
	
	public Consumidor() {
	}
	
	@Override
	public Long getId() {
		
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId_produto() {
		return id_produto;
	}

	public void setId_produto(Long id_produto) {
		this.id_produto = id_produto;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

}
