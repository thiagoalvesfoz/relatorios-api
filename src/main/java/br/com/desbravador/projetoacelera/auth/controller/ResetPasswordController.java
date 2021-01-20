package br.com.desbravador.projetoacelera.auth.controller;

import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reset_password")
public class ResetPasswordController {

    @Autowired
    private AuthService service;

    @GetMapping
    public String renderFormResetPassword(@RequestParam(value = "token") String token, Model model) {
        User user = service.getUserByToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = service.getUserByToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        user.setActive(true);
        service.updatePassword(user, password);
        model.addAttribute("message", "You have successfully changed your password.");
        return "message";
    }
}
