package br.com.desbravador.projetoacelera.integration;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DatabaseTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("teste conexao com postgres")
    public void testDatabaseConnection() {
        Assertions.assertTrue(postgreDBContainer.isRunning());
    }

    @Test
    @DisplayName("teste carga inicial de usuarios")
    public void testInitialChargeForUser() {

        String emailAdmin = "admin@desbravador.com";
        String emailUser = "user@desbravador.com";

        Optional<User> admin = userRepository.findByEmail(emailAdmin);
        Optional<User> user = userRepository.findByEmail(emailUser);

        Assertions.assertTrue(admin.isPresent());
        Assertions.assertTrue(user.isPresent());
        Assertions.assertTrue(admin.get().isAdmin());
        Assertions.assertFalse(user.get().isAdmin());

    }

}
