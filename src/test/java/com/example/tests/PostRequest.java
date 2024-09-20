package com.example.tests;




import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostRequest {

    private RequestSpecification requestSpec;
    private Faker faker;

    @BeforeClass
    public void setup() {
        // Create the request specification
        requestSpec = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json");
        
        // Initialize Faker
        faker = new Faker();
    }

    @Test
    public void createPost() {
        // Create a HashMap for the payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", faker.lorem().sentence());
        requestBody.put("body", faker.lorem().paragraph());
        requestBody.put("userId", faker.number().numberBetween(1, 10));

        // Send the POST request
        given(requestSpec)
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo(requestBody.get("title"))).log().all(); // Adjusting to check the correct title
    }
}
