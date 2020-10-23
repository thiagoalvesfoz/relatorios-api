package br.com.desbravador.projetoacelera.dicionario.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desbravador.projetoacelera.dicionario.domain.Dicionario;

@Repository
public interface DicionarioRepository extends JpaRepository<Dicionario, Long> {

}
