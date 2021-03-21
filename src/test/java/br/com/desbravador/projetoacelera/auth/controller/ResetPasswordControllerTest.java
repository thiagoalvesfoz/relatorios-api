package br.com.desbravador.projetoacelera.auth.controller;

import br.com.desbravador.projetoacelera.BaseTests;
import br.com.desbravador.projetoacelera.auth.service.AuthService;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ResetPasswordControllerTest extends BaseTests {

    private final Model model = new ExtendedModelMap();
    @InjectMocks
    private ResetPasswordController resetPasswordController;
    @Mock
    private AuthService authServiceMock;

    @Test
    @DisplayName("deve retornar o formulário para redefinição de senha quando o token for válido")
    public void test_should_return_the_password_reset_form_when_the_token_is_valid() {

        String token = RandomString.make(30);
        String viewExpected = "reset_password_form";

        when(authServiceMock.getUserByToken(token)).thenReturn(getUser());

        String result = resetPasswordController.renderFormResetPassword(token, model);

        assertEquals(viewExpected, result);
        assertEquals(token, model.getAttribute("token"));
    }

    @Test
    @DisplayName("deve retornar mensagem de erro ao tentar acessar o formulario com token invalido")
    public void test_should_return_error_message_when_trying_to_access_the_form_with_invalid_token() {

        String token = RandomString.make(30);
        String viewExpected = "message";

        when(authServiceMock.getUserByToken(token)).thenReturn(null);

        String result = resetPasswordController.renderFormResetPassword(token, model);

        assertEquals(viewExpected, result);
        assertEquals("Invalid Token", model.getAttribute("message"));
    }

    @Test
    @DisplayName("deve retornar a pagina de sucesso após a redefinição de senha")
    public void test_should_return_to_the_success_page_after_resetting_the_password() {

        String token = RandomString.make(30);
        String messageExpect = "You have successfully changed your password.";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("token", token);
        request.setParameter("password", "teste@123");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(authServiceMock.getUserByToken(token)).thenReturn(getUser());
        doNothing().when(authServiceMock).updatePassword(any(), anyString());

        resetPasswordController.processResetPassword(request, model);

        assertEquals(messageExpect, model.getAttribute("message"));
    }

    @Test
    @DisplayName("deve retornar mensagem de erro ao tentar resetar senha com token invalido")
    public void test_should_return_invalid_token_when_trying_to_reset_password_with_invalid_token() {

        String token = RandomString.make(30);
        String messageExpect = "Invalid Token";

        when(authServiceMock.getUserByToken(token)).thenReturn(null);

        resetPasswordController.renderFormResetPassword(token, model);

        assertEquals(messageExpect, model.getAttribute("message"));
    }

}
