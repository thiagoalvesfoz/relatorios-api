package br.com.desbravador.projetoacelera.application.user.repository;
import br.com.desbravador.projetoacelera.application.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

	User findByToken(String token);
}
