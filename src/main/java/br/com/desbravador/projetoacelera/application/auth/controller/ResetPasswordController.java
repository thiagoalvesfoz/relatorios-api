package br.com.desbravador.projetoacelera.application.auth.controller;

import br.com.desbravador.projetoacelera.application.auth.dto.ChangePasswordDTO;
import br.com.desbravador.projetoacelera.application.auth.service.AuthService;
import br.com.desbravador.projetoacelera.application.user.entity.User;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reset_password")
public class ResetPasswordController {

    private static final String RESET_PASSWORD = "reset_password";

    private final AuthService service;
    private final Zxcvbn zxcvbn;

    @Autowired
    public ResetPasswordController(AuthService service, Zxcvbn zxcvbn) {
        this.service = service;
        this.zxcvbn = zxcvbn;
    }

    @GetMapping
    public String renderFormResetPassword(@RequestParam(value = "token") String token, Model model) {

        model.addAttribute("pageTitle", "Desbravador - Forgot Password");

        User user = service.getUserByToken(token);

        if (user == null) {
            model.addAttribute("invalidToken", "Este é um link de confirmação inválido.");
            return RESET_PASSWORD;
        }

        model.addAttribute("confirmationToken", token);

        return RESET_PASSWORD;
    }

    @PostMapping
    public ModelAndView processResetPassword(ModelAndView modelAndView, BindingResult bindingResult, RedirectAttributes redirect, ChangePasswordDTO passwd) {

        modelAndView.setViewName(RESET_PASSWORD);
        User user = service.getUserByToken(passwd.getToken());

        boolean weakPassword = zxcvbn.measure(passwd.getPassword()).getScore() < 2;

        if (passwd.notEquals()) {
            bindingResult.reject("password");
            redirect.addFlashAttribute("errorMessage", "Ambas as senhas devem ser idênticas.");
            modelAndView.setViewName("redirect:"+ RESET_PASSWORD + "?token=" + passwd.getToken());
            return modelAndView;

        } else if (weakPassword) {
            bindingResult.reject("password");
            redirect.addFlashAttribute("errorMessage", "Sua senha é muito fraca. Escolha uma senha mais forte.");
            modelAndView.setViewName("redirect:"+ RESET_PASSWORD + "?token=" + passwd.getToken());
            return modelAndView;

        } else if (user == null) {
            redirect.addFlashAttribute("invalidToken", "Ocorreu um erro inesperado, tente novamente mais tarde.");
            return modelAndView;
        }

        service.updatePassword(user, passwd.getPassword());

        modelAndView.addObject("successMessage", "Sua senha foi salva!");
        modelAndView.addObject("pageTitle", "Desbravador - Sucesso");

        return modelAndView;
    }
}
