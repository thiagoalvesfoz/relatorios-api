package br.com.desbravador.projetoacelera.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.desbravador.projetoacelera.model.entities.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
}
