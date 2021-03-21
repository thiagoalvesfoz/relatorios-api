package br.com.desbravador.projetoacelera;

import br.com.desbravador.projetoacelera.users.domain.User;

public class UserSingleton {

    private static User USER_INSTANCE = null;

    private UserSingleton() {
    }

    public static User getInstance() {

        if (USER_INSTANCE == null) {
            USER_INSTANCE = new User();
            USER_INSTANCE.setId(1L);
            USER_INSTANCE.setName("Foo");
            USER_INSTANCE.setEmail("teste@teste.com");
            USER_INSTANCE.setPassword("password");
        }

        return USER_INSTANCE;
    }
}
