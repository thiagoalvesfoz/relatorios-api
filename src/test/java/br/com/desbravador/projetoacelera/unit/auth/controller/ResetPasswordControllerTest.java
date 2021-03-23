package br.com.desbravador.projetoacelera.unit.auth.controller;

import br.com.desbravador.projetoacelera.auth.ChangePasswordDTO;
import br.com.desbravador.projetoacelera.auth.controller.ResetPasswordController;
import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.unit.BaseTests;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ResetPasswordControllerTest extends BaseTests {

    private final Model model = new ExtendedModelMap();

    @InjectMocks
    private ResetPasswordController resetPasswordController;

    @Mock
    private AuthService authServiceMock;

    @Spy
    private Zxcvbn zxcvbn;

    @Test
    @DisplayName("deve retornar o formulário para redefinição de senha quando o token for válido")
    public void test_should_return_the_password_reset_form_when_the_token_is_valid() {

        String token = RandomString.make(30);
        String viewExpected = "reset_password";

        when(authServiceMock.getUserByToken(token)).thenReturn(getUser());

        String result = resetPasswordController.renderFormResetPassword(token, model);

        assertEquals(viewExpected, result);
        assertEquals(token, model.getAttribute("confirmationToken"));
    }

    @Test
    @DisplayName("deve retornar mensagem de erro ao tentar acessar o formulario com token invalido")
    public void test_should_return_error_message_when_trying_to_access_the_form_with_invalid_token() {

        String token = RandomString.make(30);
        String viewExpected = "reset_password";
        String expectedMessage = "Este é um link de confirmação inválido.";

        when(authServiceMock.getUserByToken(token)).thenReturn(null);

        String result = resetPasswordController.renderFormResetPassword(token, model);

        assertEquals(viewExpected, result);
        assertEquals(expectedMessage, model.getAttribute("invalidToken"));
    }

    @Test
    @DisplayName("deve retornar a pagina de sucesso após a redefinição de senha")
    public void test_should_return_to_the_success_page_after_resetting_the_password() {

        ChangePasswordDTO passwd = new ChangePasswordDTO("teste@313", "teste@313", RandomString.make(30));
        String expectedMessage = "Sua senha foi salva!";
        String viewExpected = "reset_password";

        RedirectAttributes redirect = Mockito.mock(RedirectAttributes.class);
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = mock(BindingResult.class);

        when(authServiceMock.getUserByToken(passwd.getToken())).thenReturn(getUser());
        when(zxcvbn.measure(anyString()).getScore()).thenCallRealMethod();
        doNothing().when(authServiceMock).updatePassword(any(), anyString());
        doNothing().when(bindingResult).reject(anyString());

        ModelAndView result = resetPasswordController.processResetPassword(modelAndView, bindingResult, redirect, passwd);

        assertEquals(viewExpected, result.getViewName());
        assertEquals(expectedMessage, result.getModel().get("successMessage"));
    }

    @Test
    @DisplayName("deve retornar a mensagem de erro solicitando que as senhas sejam identicas")
    public void test_should_return_to_the_success_page_after_resetting_the_passwor2() {

        ChangePasswordDTO passwd = new ChangePasswordDTO("@senhas", "senhaDiferente", RandomString.make(30));
        String expectedMessage = "Ambas as senhas devem ser idênticas.";
        String viewExpected = "reset_password?token=" + passwd.getToken();

        RedirectAttributes redirect = Mockito.mock(RedirectAttributes.class);
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = mock(BindingResult.class);

        when(authServiceMock.getUserByToken(passwd.getToken())).thenReturn(getUser());
        when(zxcvbn.measure(anyString()).getScore()).thenCallRealMethod();
        doNothing().when(bindingResult).reject(anyString());

        when(redirect.addFlashAttribute(anyString())).thenAnswer(invoke -> {
                String message = invoke.getArgument(0);
                assertEquals(expectedMessage, message);
                return invoke;
            }
        );

        ModelAndView result = resetPasswordController.processResetPassword(modelAndView, bindingResult, redirect, passwd);

        assertTrue(Objects.requireNonNull(result.getViewName()).contains(viewExpected));
    }

    @Test
    @DisplayName("deve retornar a mensagem de erro informando que a senha é fraca")
    public void test_should_return_to_the_success_page_after_resetting_the_passwor4() {

        String weakPassword = "senha@3021";

        ChangePasswordDTO passwd = new ChangePasswordDTO(weakPassword, weakPassword, RandomString.make(30));
        String expectedMessage = "Ocorreu um erro inesperado, tente novamente mais tarde.";
        String viewExpected = "reset_password";

        RedirectAttributes redirect = Mockito.mock(RedirectAttributes.class);
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = mock(BindingResult.class);

        when(authServiceMock.getUserByToken(passwd.getToken())).thenReturn(null);
        when(zxcvbn.measure(anyString()).getScore()).thenCallRealMethod();
        doNothing().when(bindingResult).reject(anyString());

        when(redirect.addFlashAttribute(anyString())).thenAnswer(invoke -> {
                    String message = invoke.getArgument(0);
                    assertEquals(expectedMessage, message);
                    return invoke;
                }
        );

        ModelAndView result = resetPasswordController.processResetPassword(modelAndView, bindingResult, redirect, passwd);

        System.out.println(result.getViewName());

        assertTrue(Objects.requireNonNull(result.getViewName()).contains(viewExpected));
    }

    @Test
    @DisplayName("deve retornar a mensagem de erro informando que ocorreu um erro inesperado ao tentar resetar a senha")
    public void test_should_return_to_the_success_page_after_resetting_the_passwor3() {

        String weakPassword = "123456";

        ChangePasswordDTO passwd = new ChangePasswordDTO(weakPassword, weakPassword, RandomString.make(30));
        String expectedMessage = "Sua senha é muito fraca. Escolha uma senha mais forte.";
        String viewExpected = "reset_password?token=" + passwd.getToken();

        RedirectAttributes redirect = Mockito.mock(RedirectAttributes.class);
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = mock(BindingResult.class);

        when(authServiceMock.getUserByToken(passwd.getToken())).thenReturn(getUser());
        when(zxcvbn.measure(anyString()).getScore()).thenCallRealMethod();
        doNothing().when(bindingResult).reject(anyString());

        when(redirect.addFlashAttribute(anyString())).thenAnswer(invoke -> {
                    String message = invoke.getArgument(0);
                    assertEquals(expectedMessage, message);
                    return invoke;
                }
        );

        ModelAndView result = resetPasswordController.processResetPassword(modelAndView, bindingResult, redirect, passwd);

        assertTrue(Objects.requireNonNull(result.getViewName()).contains(viewExpected));
    }

}
