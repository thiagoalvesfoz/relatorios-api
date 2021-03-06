package br.com.desbravador.projetoacelera.config;

import br.com.desbravador.projetoacelera.application.user.entity.User;
import br.com.desbravador.projetoacelera.application.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class InitialCharge implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    @Autowired
    public InitialCharge(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {

        var admins = userRepository.findAll().stream().filter(User::isAdmin).collect(Collectors.toList());

        if (admins.isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@desbravador.com");
            admin.setPassword(bCryptPasswordEncoder.encode("123456"));
            admin.setAdmin(true);
            admin.setActive(true);
            userRepository.save(admin);
        }

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            User user = new User();
            user.setName("User");
            user.setEmail("user@desbravador.com");
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setAdmin(false);
            user.setActive(true);
            userRepository.save(user);
        }
    }
}
