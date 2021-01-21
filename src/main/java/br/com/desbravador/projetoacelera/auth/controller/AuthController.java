package br.com.desbravador.projetoacelera.auth.controller;

import br.com.desbravador.projetoacelera.auth.ForgotPasswordDTO;
import br.com.desbravador.projetoacelera.auth.JWTUtil;
import br.com.desbravador.projetoacelera.auth.UserSecurity;
import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.config.Utility;
import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @Autowired
    private EmailService emailService;

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse res) {
        UserSecurity user = AuthService.authenticated();
        String token = jwtUtil.generateToken(user);
        res.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPasswordDTO dto, HttpServletRequest request) {

        String email = dto.getEmail();
        String token = RandomString.make(30);

        User user = service.updateResetPasswordToken(token, email);

        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        emailService.sendHtmlResetPasswordEmail(user, resetPasswordLink);

        return ResponseEntity.noContent().build();
    }

}
