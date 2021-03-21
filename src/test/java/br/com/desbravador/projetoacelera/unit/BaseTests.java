package br.com.desbravador.projetoacelera.unit;


import br.com.desbravador.projetoacelera.UserSingleton;
import br.com.desbravador.projetoacelera.auth.UserSecurity;
import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseTests {

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
        return UserSingleton.getInstance();
    }
}
