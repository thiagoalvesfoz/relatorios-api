package br.com.desbravador.projetoacelera.application.auth.service;

import br.com.desbravador.projetoacelera.application.auth.stream.UserSecurity;
import br.com.desbravador.projetoacelera.application.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = repository
                .findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException(email) );

        return new UserSecurity(user);
    }
}
