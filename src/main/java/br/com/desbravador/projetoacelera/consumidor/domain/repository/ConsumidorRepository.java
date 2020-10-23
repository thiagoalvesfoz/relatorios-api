package br.com.desbravador.projetoacelera.consumidor.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desbravador.projetoacelera.consumidor.domain.Consumidor;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {
	
}
