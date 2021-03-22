package br.com.desbravador.projetoacelera.auth.controller;

import br.com.desbravador.projetoacelera.auth.ForgotPasswordDTO;
import br.com.desbravador.projetoacelera.auth.JWTUtil;
import br.com.desbravador.projetoacelera.auth.UserSecurity;
import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.config.Utility;
import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthService service;
    private final EmailService emailService;

    @Autowired
    public AuthController(JWTUtil jwtUtil, AuthService service, EmailService emailService){
        this.jwtUtil = jwtUtil;
        this.service = service;
        this.emailService = emailService;
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Map<String, String>> refreshToken() {

        UserSecurity user = AuthService.authenticated();

        if (user == null) {
            throw new ResourceNotFoundException("Ops, user not found!");
        }

        String tokenJWT = jwtUtil.generateToken(user);
        HashMap<String, String> json = new HashMap<>();
        json.put("token", tokenJWT);

        return ResponseEntity.ok().body(json);
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
