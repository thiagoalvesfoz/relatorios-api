package br.com.desbravador.projetoacelera.users.controller;

import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.dto.UserDto;
import br.com.desbravador.projetoacelera.users.dto.input.UserInput;
import br.com.desbravador.projetoacelera.users.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @Mock
    EmailService emailServiceMock;

    MockHttpServletRequest request;

    public UserControllerTest() {
        request = new MockHttpServletRequest();
    }

    @BeforeEach
    public void setUp() {

        request.setRequestURI("/api/users");
        request.setServletPath("/api/users");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(emailServiceMock).sendHtmlAccountRegistration(any(), anyString());
    }

    @Test
    @DisplayName("deve criar um novo usuario com sucesso")
    public void test_shouldCreateANewUserSuccessfully() throws URISyntaxException {

        UserInput userInput = new UserInput();
        userInput.setName("User Tester");
        userInput.setEmail("test@test.com");

        final String[] expectedLinkResetPassword = {"http://localhost/reset_password?token="};
        Long idUserExpected = 12L;

        when(userServiceMock.save(any(User.class)))
                .thenAnswer(accountUser -> {
                    User account = accountUser.getArgument(0);

                    Assertions.assertThat(account.getToken()).isNotNull();
                    Assertions.assertThat(account.getToken().length()).isEqualTo(30);

                    account.setId(idUserExpected);
                    account.setCreatedAt(Instant.now());
                    expectedLinkResetPassword[0] = expectedLinkResetPassword[0] + account.getToken();
                    return account;
                });

        ResponseEntity<UserDto> result = userController.createNewAccount(userInput, request);

        String location = result.getHeaders().getLocation() + "";

        Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(location).isEqualTo(request.getRequestURL() + "/" + idUserExpected);
        Assertions.assertThat(result.getBody()).isExactlyInstanceOf(UserDto.class);
        Assertions.assertThat(Objects.requireNonNull(result.getBody()).getId()).isEqualTo(idUserExpected);


        ArgumentCaptor<String> linkForPassword = ArgumentCaptor.forClass(String.class);
        verify(emailServiceMock).sendHtmlAccountRegistration(any(), linkForPassword.capture());

        Assertions.assertThat(linkForPassword.getValue()).contains(expectedLinkResetPassword[0]);
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuários")
    public void returnUserList() {
        List<User> userList = new ArrayList<>(Collections.singleton(getUser()));

        when(userServiceMock.findAll()).thenReturn(userList);

        ResponseEntity<List<UserDto>> result = userController.findAll();

        Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(result.getBody()).isNotEmpty();
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo id")
    public void testFindUserForOne() {

        Long id = 1L;

        when(userServiceMock.findOne(id)).thenReturn(getUser());

        ResponseEntity<UserDto> result = userController.getOne(id);

        Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(Objects.requireNonNull(result.getBody()).getId()).isEqualTo(id);
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