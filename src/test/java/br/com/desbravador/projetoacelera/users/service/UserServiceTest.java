package br.com.desbravador.projetoacelera.users.service;

import br.com.desbravador.projetoacelera.auth.UserSecurity;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.AuthorizationException;
import br.com.desbravador.projetoacelera.web.exception.BusinessRuleException;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(getUser()));
        when(userRepositoryMock.save(ArgumentMatchers.any())).thenReturn(getUser());
    }

    private void authenticatedMock(String email, boolean admin){

        User user = new User();
        user.setId(1L);
        user.setName("User Tester");
        user.setEmail(email);
        user.setAdmin(admin);
        user.setActive(true);

        UserSecurity principal = new UserSecurity(user);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
    }

    @Test
    @DisplayName("O administrador deve registrar um novo usuario com sucesso")
    public void test_ShouldSaveSuccessfullySaveTheNewUser() {

        authenticatedMock("admin@desbravador.com", true);

        User newUser = new User();
        newUser.setName("Foo");
        newUser.setEmail("foo@test.com");
        newUser.setPassword("foo123");

        when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        when(userRepositoryMock.save(ArgumentMatchers.any())).thenReturn(newUser);

        User result = userService.save(newUser);

        Assertions.assertNotNull(result);

    }

    @Test
    @DisplayName("Não deve registrar um novo usuario se o usuario não estiver autenticado")
    public void test_ShouldNotSave_WhenTheUserIsNotAuthenticated() {

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(null);

        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.save(getUser());
        });
    }

    @Test
    @DisplayName("Não deve registrar um novo usuario se o usuario autenticado não for administrador")
    public void test_ShouldNotSave_WhenTheAuthenticatedUserIsNotAdmin() {

        authenticatedMock("user@desbravador.com", false);

        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.save(getUser());
        });
    }

    @Test
    @DisplayName("Não deve registrar um novo usuario quando o email informado já estiver registrado")
    public void test_ShouldNotSave_WhenTheEmailIsAlreadyRegistered() {

        authenticatedMock("admin@desbravador.com", true);

        User newUser = getUser();

        when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(newUser));

        Assertions.assertThrows(BusinessRuleException.class, () -> {
            userService.save(newUser);
        });
    }

    @Test
    @DisplayName("Não deveria buscar um usuario pelo id se não estiver autenticado")
    public void shouldNotFind_WhenTheUserIsNotAuthenticated() {

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(null);

        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.findOne(1L);
        });

    }

    @Test
    @DisplayName("Não deve buscar outro usuario pelo id quando o usuario autenticado não for administrador")
    public void shouldNotFindAnotherUser_WhenTheAuthenticatedUserIsNotAdmin() {

        User user = new User();
        user.setId(1L);
        user.setEmail("tester@desbravador.com");
        user.setAdmin(false);

        UserSecurity principal = new UserSecurity(user);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);

        Long findAnotherUserById = 123344L;

        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.findOne(findAnotherUserById);
        });

    }

    @Test
    @DisplayName("O usuário autenticado deve ser capaz de encontrar seu próprio cadastro")
    public void test_TheUserMustBeAbleToFindTheirOwnIdSuccessfully() {

        User user = getUser();

        authenticatedMock(user.getEmail(), false);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));

        User result = userService.findOne(user.getId());

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("O administrador deve ser capaz de encontrar qualquer usuario")
    public void test_TheAdminMustBeAbleToFindAnyUser() {

        authenticatedMock("admin@desbravador", true);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(getUser()));

        User result = userService.findOne(123145L);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Deve lançar uma exceção caso não exista um usuario registrado com id informado")
    public void test_ShouldThrowExceptionIfTheUserIsNotFound() {

        authenticatedMock("admin@desbravador.com", true);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.findOne(123415L);
        });
    }

    @Test
    @DisplayName("O usuario autenticado deve conseguir atualizar o próprio cadastro")
    public void test_ShouldUpdateUserData() {

        User user = getUser();
        Long idCurrentUser = user.getId();

        authenticatedMock(user.getEmail(), false);

        User inputUser = new User();
        inputUser.setName("Jhonny");
        inputUser.setAdmin(false);

        when(userRepositoryMock.findById(ArgumentMatchers.eq(idCurrentUser))).thenReturn(Optional.of(user));

        User result = userService.update(idCurrentUser, inputUser);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Não deve atualizar os dados cadastrais sem autenticação")
    public void test_ShouldNotUpdateRegistrationDataWithoutAuthentication() {

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(null);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.findOne(1L);
        });

    }

    @Test
    @DisplayName("Não deve atualizar dados cadastrais de outro usuario sem permissão de admin")
    public void test_ShouldNotUpdateAnotherUsersRegistrationDataWithoutAdminPermission() {

        authenticatedMock("user@desbravador.com", false);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.findOne(1234L);
        });

    }


    @Test
    @DisplayName("O administrador deve conseguir atualizar os dados de outros usuarios")
    public void test_theAdministratorMustBeAbleToUpdateTheDataOfOtherUsers() {

        authenticatedMock("admin@desbravador.com", true);

        User inputUser = new User();
        inputUser.setName("Jhonny");
        inputUser.setAdmin(false);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(getUser()));

        User result = userService.update(14234L, inputUser);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Deveria lançar uma exceção ao tentar atualizar o cadastro de um usuario inexistente")
    public void test_ShouldThrowAnExceptionWhenTryingToUpdateANonExistentUserRecord() {

        authenticatedMock("admin@desbravador.com", true);

        User inputUser = new User();
        inputUser.setName("Jhonny");
        inputUser.setAdmin(false);

        when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.update(123415L, inputUser);
        });
    }


    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Foo");
        user.setEmail("test@test.com");
        user.setPassword("password");
        return user;
    }

}
