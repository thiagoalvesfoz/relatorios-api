package br.com.desbravador.projetoacelera.integration.api;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.Matchers.*;

public class UserControllerTest extends BaseApiTest {

    private final String URI = "/api/users";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    @DisplayName("o administrador deve conseguir criar novo usuario com sucesso")
    void test_the_admin_should_be_able_to_successfully_create_a_new_user() {

        RequestSpecification requestWithAdminUser = generateAndConfigureBearerToken(true);

        JSONObject newUser = new JSONObject();
        newUser.put("name", "Jane Doe");
        newUser.put("email", "user@teste.com");

        Response result = requestWithAdminUser.body(newUser.toJSONString()).post(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.CREATED.value()).
                body("id", notNullValue()).
                body("name", equalTo(newUser.get("name"))).
                body("email", equalTo(newUser.get("email"))).
                body("authority", equalTo("ROLE_USER"));
    }

    @Test
    @DisplayName("O usuário autenticado sem permissão de admin não deve ser capaz de criar uma nova conta de usuario")
    void test_the_authenticated_user_without_admin_permission_should_not_be_able_to_create_a_new_user_account() {

        RequestSpecification requestWithCommonUser = generateAndConfigureBearerToken();

        JSONObject newUser = new JSONObject();
        newUser.put("name", "Jane Doe");
        newUser.put("email", "user2021@teste.com");

        Response result = requestWithCommonUser.body(newUser.toJSONString()).post(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("deve retornar o acesso negado ao tentar criar uma nova conta sem estar previamente autenticado")
    void test_should_return_access_denied_when_trying_to_create_a_new_account_without_being_previously_authenticated() {

        RequestSpecification requestWithoutAuthentication = RestAssured.given();
        requestWithoutAuthentication.header("Content-Type", "application/json");

        JSONObject newUser = new JSONObject();
        newUser.put("name", "Jane Doe");
        newUser.put("email", "user2021@teste.com");

        Response result = requestWithoutAuthentication.body(newUser.toJSONString()).post(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("deve retornar um erro ao tentar cadastrar uma nova conta com algum email já registrado")
    void test_should_return_an_error_when_trying_to_register_a_new_account_with_an_already_registered_email() {

        String emailAlreadyRegistered = "unregistered_email@test.com";

        User previouslyRegisteredUser = new User();
        previouslyRegisteredUser.setName("Test User");
        previouslyRegisteredUser.setEmail(emailAlreadyRegistered);
        previouslyRegisteredUser.setPassword(bCryptPasswordEncoder.encode("password"));
        previouslyRegisteredUser.setActive(true);
        userRepository.save(previouslyRegisteredUser);

        RequestSpecification request = generateAndConfigureBearerToken(true);

        JSONObject newUser = new JSONObject();
        newUser.put("name", "Jane Doe");
        newUser.put("email", emailAlreadyRegistered);

        Response result = request.body(newUser.toJSONString()).post(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value()).
                body("title", equalTo("E-mail already registered!"));
    }

    @Test
    @DisplayName("não deveria ser capaz de criar uma nova conta de usuário com campos inválidos")
    void test_should_not_be_able_to_create_a_new_user_account_with_invalid_fields() {

        RequestSpecification requestWithAdminUser = generateAndConfigureBearerToken(true);

        String invalidBody = "{}";

        Response result = requestWithAdminUser.body(invalidBody).post(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                body("title", equalTo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")).
                body("fields.name", hasItems("email", "name")).
                body("fields.message", hasItem("não deve estar em branco"));
    }

    @Test
    @DisplayName("não deveria ser capaz de criar uma nova conta de usuário com campo de email inválido")
    void test_should_not_be_able_to_create_a_new_user_account_with_an_invalid_email_field() {

        RequestSpecification requestWithAdminUser = generateAndConfigureBearerToken(true);

        JSONObject newUserWithInvalidEmailField = new JSONObject();
        newUserWithInvalidEmailField.put("name", "Jane Doe");
        newUserWithInvalidEmailField.put("email", "invalid@.email@field");

        Response result = requestWithAdminUser.body(newUserWithInvalidEmailField).post(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                body("title", equalTo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")).
                body("fields.name", hasItem("email")).
                body("fields.message", hasItem("deve ser um endereço de e-mail bem formado"));
    }

    @Test
    @DisplayName("o administrador deve ser capaz de listar todos os usuários do sistema.")
    void test_the_admin_should_be_able_to_list_all_users_on_the_system() {

        RequestSpecification requestWithAdminUser = generateAndConfigureBearerToken(true);

        Response result = requestWithAdminUser.get(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.OK.value()).
                body("id", notNullValue()).
                body("name", notNullValue()).
                body("email", notNullValue()).
                body("authority", notNullValue()).
                extract().as(JSONArray.class);
    }

    @Test
    @DisplayName("O usuário comum não deveria listar todos os usuários do sitema")
    void test_the_common_user_should_be_not_able_to_list_all_users_on_the_system() {

        RequestSpecification requestWithCommonUser = generateAndConfigureBearerToken();

        Response result = requestWithCommonUser.get(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.FORBIDDEN.value()).
                body("title", equalTo("Access Denied!"));
    }

    @Test
    @DisplayName("Não deveria listar todos os usuários sem uma prévia autenticação no sistema")
    void test_should_not_list_all_users_without_prior_authentication_on_the_system() {

        RequestSpecification requestWithoutAuthentication = RestAssured.given();
        requestWithoutAuthentication.header("Content-Type", "application/json");

        Response result = requestWithoutAuthentication.get(URI);

        result.then().
                log().all().
                statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("o administrador deve ser capaz de pesquisar por um usuário específico no sistema")
    void test_the_admin_should_be_able_to_search_for_specific_user_in_the_system() {

        User user = new User(null,"Usuário","usuario@tester.com","password",true,true);

        user = userRepository.save(user);

        int idExpected = user.getId().intValue();

        RequestSpecification requestWithAdminUser = generateAndConfigureBearerToken(true);

        Response result = requestWithAdminUser.get(URI + "/{id}", idExpected);

        result.then().log().all().
                statusCode(HttpStatus.OK.value()).
                body("id", equalTo(idExpected)).
                body("name", equalTo(user.getName())).
                body("email", equalTo(user.getEmail())).
                body("authority", equalTo(user.getAuthority()));
    }
    @Test
    @DisplayName("Um usuário não deve ser capaz de pesquisar por outros usuários no sistema")
    void test_the_common_user_should_not_be_able_to_search_for_others_users_in_the_system() {

        User userCommon = userRepository.findByEmail("user@desbravador.com").orElse(null);
        Assertions.assertNotNull(userCommon);
        int otherUserId = userCommon.getId().intValue() + 1;

        RequestSpecification requestWithUserCommon = generateAndConfigureBearerToken();

        Response result = requestWithUserCommon.get(URI + "/{id}", otherUserId);

        result.then().log().all().
                statusCode(HttpStatus.FORBIDDEN.value()).
                body("title", equalTo("Access Denied!"));

    }
    @Test
    @DisplayName("Um usuário comum deve ser capaz de recuperar seus próprios dados no sistema")
    void test_the_common_user_should_only_be_able_to_recovery_their_own_data_on_the_system() {

        User userCommon = userRepository.findByEmail("user@desbravador.com").orElse(null);
        Assertions.assertNotNull(userCommon);
        int idExpected = userCommon.getId().intValue();

        RequestSpecification requestWithUserCommon = generateAndConfigureBearerToken();

        Response result = requestWithUserCommon.get(URI + "/{id}", idExpected);

        result.then().log().all().
                statusCode(HttpStatus.OK.value()).
                body("id", equalTo(idExpected)).
                body("name", equalTo(userCommon.getName())).
                body("email", equalTo(userCommon.getEmail())).
                body("authority", equalTo(userCommon.getAuthority()));

    }

    @Test
    @DisplayName("Um usuário não deve ser capaz de pesquisar por outros usuários no sistema")
    void test_an_unauthenticated_user_should_not_be_able_to_search_for_users_by_id_in_the_system() {

        RequestSpecification requestWithoutAuthentication = RestAssured.given();
        requestWithoutAuthentication.header("Content-Type", "application/json");

        int id = 1;

        Response result = requestWithoutAuthentication.get(URI + "/{id}", id);

        result.then().log().all().
                statusCode(HttpStatus.FORBIDDEN.value());

    }
}
