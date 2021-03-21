package br.com.desbravador.projetoacelera;

import br.com.desbravador.projetoacelera.auth.UserSecurity;
import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseTests {

    private final User user = new User();

    public BaseTests() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    protected void authenticatedMock(String email, boolean admin) {

        User user = new User();
        user.setId(1L);
        user.setName("User Tester");
        user.setEmail(email);
        user.setAdmin(admin);
        user.setActive(true);

        UserSecurity principal = new UserSecurity(user);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
    }

    public User getUser() {
//        User user = new User();
        user.setId(1L);
        user.setName("Foo");
        user.setEmail("teste@teste.com");
        user.setPassword("password");
        return user;
    }
}
