package br.com.desbravador.projetoacelera.integration.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void config() {
        RestAssured.reset();
        RestAssured.given().contentType(ContentType.JSON);
        RestAssured.port = port;
    }

    protected RequestSpecification generateAndConfigureBearerToken() {
        return generateAndConfigureBearerToken(false);
    }

    protected RequestSpecification generateAndConfigureBearerToken(boolean admin) {

        String email = "user@desbravador.com";

        if (admin) {
            email = "admin@desbravador.com";
        }

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", "123456");

        Response response = request.body(payload.toJSONString()).post("/login");

        String tokenGenerated = response.getBody().jsonPath().get("token");
        request.header("Authorization", "Bearer " + tokenGenerated);

        return request;
    }

}
