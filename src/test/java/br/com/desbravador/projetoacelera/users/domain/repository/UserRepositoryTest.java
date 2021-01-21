package br.com.desbravador.projetoacelera.users.domain.repository;

import br.com.desbravador.projetoacelera.users.domain.User;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for User Repository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Find User By Token")
    public void findUserByToken() {

        String token = RandomString.make(30);

        User user = getUser();
        user.setToken(token);
        userRepository.save(user);

        User result = userRepository.findByToken(token);

        Assertions.assertThat(result.getToken()).isEqualTo(token);
    }


    private User getUser() {
        User user = new User();
        user.setName("Thiago Alves");
        user.setEmail("test@test.com");
        user.setPassword("test@123");
        return user;
    }

}