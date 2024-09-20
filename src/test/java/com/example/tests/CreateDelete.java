
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

public class CreateDelete {

    private RequestSpecification requestSpec;
    private Faker faker;
    private int createdPostId; // To store the ID of the created post

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

        // Send the POST request and capture the created post ID
        createdPostId = given(requestSpec)
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo(requestBody.get("title")))
            .extract().path("id"); // Extract the ID of the created post
    }

    @Test(dependsOnMethods = "createPost")
    public void deletePost() {
        // Send the DELETE request
        given(requestSpec)
            .pathParam("id", createdPostId) // Set the ID path parameter
        .when()
            .delete("/posts/{id}")
        .then()
            .statusCode(200); // Usually 200 for successful deletion or 204
    }
}
