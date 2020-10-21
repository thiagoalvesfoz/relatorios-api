package br.com.desbravador.projetoacelera.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desbravador.projetoacelera.web.Model;

public class DefaultService<T extends Model, R extends JpaRepository<T, Long>>{

	@Autowired
	private R repository;
	
	
	public T save(T entity) {
		return repository.save(entity);
	}
	
	public T findOne(Long id) {
		return repository.findById(id).get();
	}
	
	public List<T> findAll() {
		return repository.findAll();
	}
	
	public void delete(T entity) {
		repository.delete(entity);
	}
}
