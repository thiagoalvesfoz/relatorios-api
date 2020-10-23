package br.com.desbravador.projetoacelera.fontededados.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desbravador.projetoacelera.fontededados.domain.FonteDeDados;

@Repository
public interface FonteDeDadosRepository extends JpaRepository<FonteDeDados, Long> {

}
