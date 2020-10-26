package br.com.desbravador.projetoacelera.web.exceptionhandler;

public class Field{
	private String name;
	private String message;
	
	public Field(String name, String message) {
		this.name = name;
		this.message = message;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
