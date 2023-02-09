package tests;

import models.LoginBodyModel;
import models.LoginResponseModel;
import models.NewAccModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import specs.CreateSpecs;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.CreateSpecs.createRequestSpec;
import static specs.CreateSpecs.createResponseSpec;
import static specs.LoginSpecs.*;

public class ReqresTestsWithSpecs extends TestBase {

    @Test
    void loginTest() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponseModel response = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void missingEmailOrUsernameTest() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("");
        data.setPassword("cityslicka");

        LoginResponseModel response = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpecMissingData)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }

    @Test
    void missingPasswordTest() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("");

        LoginResponseModel response = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpecMissingData)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    void NotFound404ResourceTest() {

        get("/unknown/23")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void createNewAccountTest() {

        NewAccModel newAccModel = new NewAccModel();
        newAccModel.setName("morpheus");
        newAccModel.setJob("leader");


        NewAccModel response = given(createRequestSpec)
                .body(newAccModel)
                .when()
                .post("/users")
                .then()
                .spec(createResponseSpec)
                .extract().as(NewAccModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
    }

    @Test
    @Disabled ("Требуется доработка")
    void unSupportedMediaTypeTest() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponseModel response = given(loginRequestSpecNonTyped)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpecUnsupportedMedia)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getError()).isEqualTo("Unsupported Media Type");
    }
}
