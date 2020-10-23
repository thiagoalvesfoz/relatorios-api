package br.com.desbravador.projetoacelera.relatorio.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.desbravador.projetoacelera.web.Model;

@Entity
@Table(name = "relatorios")
public class Relatorio implements Model {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String formato;
	private String webhook;
	private String parametros;
	private String conteudo;
	
	public Relatorio() {
	}
	
	@Override
	public Long getId() {
		
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getWebhook() {
		return webhook;
	}

	public void setWebhook(String webhook) {
		this.webhook = webhook;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
