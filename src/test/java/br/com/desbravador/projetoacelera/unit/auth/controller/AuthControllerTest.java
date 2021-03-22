package br.com.desbravador.projetoacelera.unit.auth.controller;

import br.com.desbravador.projetoacelera.unit.BaseTests;
import br.com.desbravador.projetoacelera.auth.ForgotPasswordDTO;
import br.com.desbravador.projetoacelera.auth.JWTUtil;
import br.com.desbravador.projetoacelera.auth.controller.AuthController;
import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest extends BaseTests {

    private final String TOKEN_JWT =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                    "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ." +
                    "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @InjectMocks
    private AuthController authController;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private AuthService service;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setupMock() {
        when(jwtUtil.generateToken(any())).thenReturn(TOKEN_JWT);
        doNothing().when(emailService).sendHtmlResetPasswordEmail(any(), any());
    }

    @Test
    @DisplayName("deve retornar um novo token jwt quando o usuario autenticado solicitar")
    public void test_should_return_a_new_jwt_token_when_the_authenticated_user_requests() {

        authenticatedMock("user@teste.com", true);

        ResponseEntity<Map<String, String>> result = authController.refreshToken();

        Map<String, String> json = result.getBody();

        assertNotNull(json);

        assertEquals(TOKEN_JWT, json.get("token"));

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    @DisplayName("deveria lançar uma exceção ao tentar atualizar o token jwt com usuário não autenticado")
    public void test_should_throw_exception_when_trying_to_update_the_jwt_token_with_an_unauthenticated_user() {

        var thrown = assertThrows(ResourceNotFoundException.class, () -> authController.refreshToken());

        assertEquals("Ops, user not found!", thrown.getMessage());

    }

    @Test
    @DisplayName("deve enviar um e-mail com o link de redefinição de senha no e-mail do usuário")
    public void test_should_send_an_email_with_the_password_reset_link_in_the_user_email() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ForgotPasswordDTO dtoInput = new ForgotPasswordDTO();
        dtoInput.setEmail("teste@teste.com");

        final String[] expectedLinkResetPassword = {"http://localhost/reset_password?token="};

        when(service.updateResetPasswordToken(any(), any())).thenAnswer(
                invocation -> {
                    expectedLinkResetPassword[0] += invocation.getArgument(0);
                    return new User();
                }
        );

        ResponseEntity<Void> result = authController.forgotPassword(dtoInput, request);

        ArgumentCaptor<String> linkForPassword = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendHtmlResetPasswordEmail(any(), linkForPassword.capture());

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCodeValue());
        assertEquals(expectedLinkResetPassword[0], linkForPassword.getValue());
    }
}
