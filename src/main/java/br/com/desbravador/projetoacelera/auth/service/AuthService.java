package br.com.desbravador.projetoacelera.auth.service;

import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow( () -> new ResourceNotFoundException("Email not found"));

        String newPass = newPassword();
        user.setPassword(bCryptPasswordEncoder.encode(newPass));

        userRepository.save(user);

        emailService.sendHtmlNewPasswordEmail(user, newPass);
    }

    private String newPassword() {
        char[] vet = new char[30];
        for (int i = 0; i < 30; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);

        if (opt == 0) {
            return (char) (rand.nextInt(10) + 48);
        }

        else if (opt == 1) {
            return (char) (rand.nextInt(26) + 65);
        }

        else {
            return (char) (rand.nextInt(26) + 97);
        }
    }

}
