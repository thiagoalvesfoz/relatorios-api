package br.com.desbravador.projetoacelera.unit.auth.service;

import br.com.desbravador.projetoacelera.unit.BaseTests;
import br.com.desbravador.projetoacelera.application.auth.service.UserDetailsServiceImpl;
import br.com.desbravador.projetoacelera.application.user.entity.User;
import br.com.desbravador.projetoacelera.application.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceImplTest extends BaseTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("deve identificar o usuário por e-mail com sucesso.")
    public void test_must_successfully_identify_the_user_by_email() {

        User user = getUser();
        String username = user.getEmail();

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertEquals(username, result.getUsername());

    }

    @Test
    @DisplayName("deve lançar uma exceção se o email do usuario nao for encontrado para autenticação.")
    public void test_should_throw_an_exception_if_the_user_email_is_not_found_for_authentication() {

        String username = "email@inexistente.com";

        when(repository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

    }
}
