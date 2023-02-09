package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

        /*
        1. Make POST request to https://reqres.in/api/login
            with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
        2. Get response { "token": "QpwL5tke4Pnpja7X4" }
        3. Check token is QpwL5tke4Pnpja7X4
     */

        @Test
        void loginTest() {
            String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

            given()
                    .log().uri()
                    .contentType(JSON)
                    .body(data)
                    .when()
                    .post("https://reqres.in/api/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("token", is("QpwL5tke4Pnpja7X4"));
        }

        @Test
        void unSupportedMediaTypeTest() {
            given()
                    .log().uri()
                    .when()
                    .post("https://reqres.in/api/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(415);
        }

        @Test
        void missingEmailOrUsernameTest() {
            given()
                    .log().uri()
                    .body("123")
                    .when()
                    .post("https://reqres.in/api/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .body("error", is("Missing email or username"));
        }

        @Test
        void missingPasswordTest() {
            String data = "{ \"email\": \"eve.holt@reqres.in\"}";

            given()
                    .log().uri()
                    .contentType(JSON)
                    .body(data)
                    .when()
                    .post("https://reqres.in/api/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .body("error", is("Missing password"));
        }

         /*
        1. Make GET request to https://reqres.in/api/unknown/23
        2. Check response { "status code": "404" }
     */

    @Test
    void NotFound404ResourceTest() {
        get("https://reqres.in/api/unknown/23")
                .then()
                .log().status()
                .statusCode(404);
    }
 /*
        1. Make POST request to https://reqres.in/api/users
            with body { "name": "morpheus", "job": "leader" }
        2. Get response { "name": "morpheus", "job": "leader", "id": "334", "createdAt": "2023-02-07T08:54:55.982Z" }
        3. Check name , job, id
     */

    @Test
    void createNewAccountTest() {

        String newAccount = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

       given()
                .log().all()
                .body(newAccount)
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }
}
