package br.com.desbravador.projetoacelera.auth.service;

import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Spy
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        when(userRepositoryMock.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("deve salvar um token para atualizar a senha")
    public void test_should_save_a_token_to_update_password() {

        User user = getUser();

        String token = RandomString.make(30);
        String email = user.getEmail();

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));

        User userResult = authService.updateResetPasswordToken(token, email);

        Assertions.assertEquals(token, userResult.getToken());
        Assertions.assertEquals(email, userResult.getEmail());

    }

    @Test
    @DisplayName("deve lançar uma exceção ao tentar atualizar a senha de um usuário inexistente")
    public void test_should_throw_an_exception_when_trying_to_update_the_password_of_a_non_existent_user() {

        String token = RandomString.make(30);
        String email = "email@inexistente.com";

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            authService.updateResetPasswordToken(token, email);
        });

    }

    @Test
    @DisplayName("deve atualizar a senha do usuario")
    public void test_should_update_the_user_password() {

        User user = getUser();
        user.setToken(RandomString.make(30));

        String newPassword = "senha123";

        authService.updatePassword(user, newPassword);

        ArgumentCaptor<User> result = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock).save(result.capture());

        Assertions.assertNull(result.getValue().getToken());
        Assertions.assertTrue(encoder.matches(newPassword, result.getValue().getPassword()));
    }


    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Thiago Alves");
        user.setEmail("test@test.com");
        user.setPassword("test@123");
        return user;
    }

}
