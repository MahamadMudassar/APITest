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

public class NegativeEndToEndTests {
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
	public void createPostWithInvalidData() {
	    // Attempt to create a post with invalid data (e.g., missing title)
	    Map<String, Object> invalidRequestBody = new HashMap<>();
	    invalidRequestBody.put("body", faker.lorem().paragraph());
	    invalidRequestBody.put("userId", faker.number().numberBetween(1, 10));

	    given(requestSpec)
	        .body(invalidRequestBody)
	    .when()
	        .post("/posts")
	    .then()
	        .statusCode(400) // Expecting a 400 Bad Request
	        .body("error", equalTo("Title is required")); // Adjust based on actual error message
	}

	@Test
	public void updatePostWithInvalidId() {
	    // Attempt to update a post that does not exist
	    Map<String, Object> updateRequestBody = new HashMap<>();
	    updateRequestBody.put("title", faker.lorem().sentence());
	    updateRequestBody.put("body", faker.lorem().paragraph());
	    updateRequestBody.put("userId", faker.number().numberBetween(1, 10));

	    given(requestSpec)
	        .pathParam("id", 99999) // Using an ID that doesn't exist
	        .body(updateRequestBody)
	    .when()
	        .put("/posts/{id}")
	    .then()
	        .statusCode(404); // Expecting a 404 Not Found
	}

	@Test
	public void deletePostWithInvalidId() {
	    // Attempt to delete a post that does not exist
	    given(requestSpec)
	        .pathParam("id", 99999) // Using an ID that doesn't exist
	    .when()
	        .delete("/posts/{id}")
	    .then()
	        .statusCode(404); // Expecting a 404 Not Found
	}

	@Test
	public void createPostWithInvalidUserId() {
	    // Attempt to create a post with an invalid user ID
	    Map<String, Object> invalidRequestBody = new HashMap<>();
	    invalidRequestBody.put("title", faker.lorem().sentence());
	    invalidRequestBody.put("body", faker.lorem().paragraph());
	    invalidRequestBody.put("userId", -1); // Invalid user ID

	    given(requestSpec)
	        .body(invalidRequestBody)
	    .when()
	        .post("/posts")
	    .then()
	        .statusCode(400); // Expecting a 400 Bad Request
	}
}
