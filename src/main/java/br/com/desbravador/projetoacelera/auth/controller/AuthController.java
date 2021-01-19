package br.com.desbravador.projetoacelera.auth.controller;

import br.com.desbravador.projetoacelera.auth.ForgotPasswordDTO;
import br.com.desbravador.projetoacelera.auth.JWTUtil;
import br.com.desbravador.projetoacelera.auth.UserSecurity;
import br.com.desbravador.projetoacelera.auth.service.AuthService;
import br.com.desbravador.projetoacelera.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse res) {
        UserSecurity user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        res.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@RequestBody @Valid ForgotPasswordDTO dto) {
        service.sendNewPassword(dto.getEmail());
        return ResponseEntity.noContent().build();
    }

}
