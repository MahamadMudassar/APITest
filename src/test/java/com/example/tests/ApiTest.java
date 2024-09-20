package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        // Create the request specification
        requestSpec = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json");
    }

    @Test
    public void exampleTest() {
        given(requestSpec)
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .body("userId", equalTo(1));
    }

    @Test
    public void anotherExampleTest() {
        given(requestSpec)
            .when()
                .get("/posts/2")
            .then()
                .statusCode(200)
                .body("id", equalTo(2));
    }
}
