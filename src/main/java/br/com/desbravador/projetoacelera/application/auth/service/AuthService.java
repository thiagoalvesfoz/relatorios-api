package br.com.desbravador.projetoacelera.application.auth.service;

import br.com.desbravador.projetoacelera.application.auth.stream.UserSecurity;
import br.com.desbravador.projetoacelera.application.user.entity.User;
import br.com.desbravador.projetoacelera.application.user.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    public static UserSecurity authenticated() {
        try {
            return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            return null;
        }
    }

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User updateResetPasswordToken(String token, String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find any user with the email " + email));

        user.setToken(token);
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    public User getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

    public void updatePassword(User user, String newPassword) {

        if(!user.isActive()) {
            user.setActive(true);
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));

        user.setToken(null);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
    }
}
