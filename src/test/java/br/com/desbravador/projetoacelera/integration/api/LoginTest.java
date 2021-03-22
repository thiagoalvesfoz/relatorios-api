package br.com.desbravador.projetoacelera.integration.api;


import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest extends BaseApiTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("deve realizar login com sucesso e retornar o token JWT no corpo da resposta")
    void test_should_log_in_successfully_and_return_the_JWT_token_in_the_response_body() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "user@desbravador.com");
        requestParams.put("password", "123456");

        given().body(requestParams.toString()).
                when().post("/login").
                then().statusCode(200).body(containsString("token")).
                log().all();
    }

    @Test
    @DisplayName("deve retornar acesso negado se o usuário tentar logar com um email ou senha inválido")
    void test_should_return_access_denied_when_the_user_tries_to_log_in_with_an_invalid_email_or_password() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "usuario@errado.com");
        requestParams.put("password", "WrongPassword123");

        given().body(requestParams.toString()).
                when().post("/login").
                then().statusCode(HttpStatus.UNAUTHORIZED.value()).body("message", equalTo("Bad credentials")).
                log().all();
    }

    @Test
    @DisplayName("deve retornar acesso negado quando o usuário não ativar sua conta pelo email antes de se autenticar")
    void test_should_return_access_denied_when_the_user_does_not_activate_their_account_via_email_before_authenticating() {

        User userDisabledInDatabase = new User();
        userDisabledInDatabase.setName("Test User");
        userDisabledInDatabase.setEmail("tester@tester.com");
        userDisabledInDatabase.setPassword(bCryptPasswordEncoder.encode("password"));
        userDisabledInDatabase.isActive(false);

        userRepository.save(userDisabledInDatabase);

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", userDisabledInDatabase.getEmail());
        requestParams.put("password", "password");

        given().body(requestParams.toString()).
                when().post("/login").
                then().statusCode(HttpStatus.UNAUTHORIZED.value()).body("message", equalTo("User is disabled")).
                log().all();
    }

    @Test
    @DisplayName("deve retornar erro forbidden se o usuário tentar logar sem enviar os dados de autenticação")
    void test_should_return_forbidden_error_when_the_user_tries_to_log_in_without_sending_authentication_data() {
        given().
                when().post("/login").
                then().statusCode(HttpStatus.FORBIDDEN.value()).
                log().all();
    }

}
