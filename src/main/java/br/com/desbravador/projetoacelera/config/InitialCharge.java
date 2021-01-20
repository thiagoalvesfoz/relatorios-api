package br.com.desbravador.projetoacelera.config;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class InitialCharge implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public InitialCharge(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@desbravador.com");
        admin.setPassword(bCryptPasswordEncoder.encode("123456"));
        admin.setAdmin(true);
        admin.setActive(true);
        userRepository.save(admin);

        User user = new User();
        user.setName("Usuário");
        user.setEmail("user@desbravador.com");
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        user.setAdmin(false);
        user.setActive(true);
        userRepository.save(user);

    }
}
