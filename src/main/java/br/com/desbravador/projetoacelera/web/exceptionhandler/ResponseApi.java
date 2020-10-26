package br.com.desbravador.projetoacelera.web.exceptionhandler;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseApi {
	private Integer status;
	private Instant timestamp; 
	private String title;
	private String path;
	private List<Field> fields;
	
	public ResponseApi(Integer status, Instant timestamp, String title, String path) {
		this.status = status;
		this.timestamp = timestamp;
		this.title = title;
		this.path = path;
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Instant getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
