package br.com.desbravador.projetoacelera.users.controller;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.dto.UserDto;
import br.com.desbravador.projetoacelera.users.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() {
        List<User> userList = new ArrayList<>(Collections.singleton(getUser()));
        BDDMockito.when(userServiceMock.findAll()).thenReturn(userList);

    }

    @Test
    @DisplayName("Deve retornar uma lista de usuários")
    public void returnUserList() {
        String expectedName = getUser().getName();

        List<UserDto> userDtoList = userController.findAll().getBody();

        Assertions.assertThat(userDtoList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(userDtoList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo id")
    public void testFindUserForOne() {
        User userExpected = getUser();
        BDDMockito.when(userServiceMock.findOne(ArgumentMatchers.anyLong())).thenReturn(getUser());

        UserDto result = userController.getOne(1L).getBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(userExpected.getName());
        Assertions.assertThat(result.getEmail()).isEqualTo(userExpected.getEmail());
        Assertions.assertThat(result.getAuthority()).isEqualTo(userExpected.getAuthority());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Thiago Alves");
        user.setEmail("test@test.com");
        user.setPassword("test@123");
        return user;
    }
}