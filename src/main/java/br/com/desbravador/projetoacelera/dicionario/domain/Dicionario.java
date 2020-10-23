package br.com.desbravador.projetoacelera.dicionario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.desbravador.projetoacelera.web.Model;

@Entity
@Table(name = "dicionarios")
public class Dicionario implements Model {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	private String nome;
	
	public Dicionario() {
	}
	
	@Override
	public Long getId() {
		
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
