package br.com.desbravador.projetoacelera.integration.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class AuthControllerTest extends BaseApiTest {

    /*
     * endpoint: api/refresh_token
     */

    @Test
    @DisplayName("deve retornar um novo token JWT quando o usuário autenticado solicitar")
    void test_should_return_a_new_jwt_token_when_the_authenticated_user_requests() {

        RequestSpecification request = generateAndConfigureBearerToken();

        Response result = request.post("auth/refresh_token");

        result.then().
                log().all().
                statusCode(HttpStatus.OK.value()).
                body("token", notNullValue());

    }

    @Test
    @DisplayName("deve retornar acesso negado ao tentar solicitar um novo token JWT sem autenticação")
    void test_should_return_access_denied_when_trying_to_request_a_new_jwt_token_without_authentication() {
        when().post("auth/refresh_token").
        then().log().all().statusCode(HttpStatus.FORBIDDEN.value());
    }

    /*
     * endpoint: api/forgot_password
     */

    @Test
    @DisplayName("deve retornar sucesso ao passar email cadastrado para redefinição de senha")
    void test_forgot_password_success() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json").auth().none();

        JSONObject payload = new JSONObject();
        payload.put("email", "user@desbravador.com");

        Response result = request.body(payload.toJSONString()).post("/auth/forgot_password");
        result.then().log().all();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCode());
    }

    @Test
    @DisplayName("deve retornar status code 404 ao tentar passar um email não cadastrado para redefinição de senha")
    void test_should_return_status_code_404_when_trying_to_pass_an_unregistered_email_for_password_reset() {

        String emailNotRegistered = "email@naocadastrado.com";

        JSONObject payload = new JSONObject();
        payload.put("email", emailNotRegistered);

        given().
        header("Content-Type", "application/json").body(payload.toJSONString()).
        when().
        post("/auth/forgot_password").
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        body("title", equalTo("Could not find any user with the email " + emailNotRegistered));
    }

    @Test
    @DisplayName("deve retornar status code 400 se o usuario enviar um email vazio para redefinição de senha")
    void test_should_return_status_code_400_when_the_user_sends_an_empty_email_for_password_reset() {

        String emptyBody = "{}";

        given().
                header("Content-Type", "application/json").body(emptyBody).
        when().
                post("/auth/forgot_password").
        then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                body("title", equalTo("One or more fields are invalid. Fill it in correctly and try again")).
                body("fields[0].name", equalTo("email")).
                body("fields[0].message", equalTo("must not be empty"));
    }

    @Test
    @DisplayName("deve retornar status code 400 quando informar um email invalido no corpo da requisição")
    void test_should_return_status_code_400_when_informing_an_invalid_email_in_the_request_body() {

        String invalidEmail = "invalid_email@.com";

        JSONObject payload = new JSONObject();
        payload.put("email", invalidEmail);

        given().
                header("Content-Type", "application/json").body(payload).
                when().
                post("/auth/forgot_password").
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                body("title", equalTo("One or more fields are invalid. Fill it in correctly and try again")).
                body("fields[0].name", equalTo("email")).
                body("fields[0].message", equalTo("must be a well-formed email address"));
    }

    @Test
    @DisplayName("deve responder status code 400 se o usuário não enviar um corpo com o email para recuperação de senha")
    void test_should_answer_status_code_400_when_the__ser_does_not_send_a_body_with_the_email_for_password_recovery() {
        given().header("Content-Type", "application/json").
                when().post("/auth/forgot_password").
                then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
