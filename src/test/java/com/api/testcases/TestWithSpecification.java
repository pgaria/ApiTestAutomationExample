package com.api.testcases;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestWithSpecification {

    RequestSpecification requestUrlSpec;

    @BeforeClass
    public void createRequestSpec() {

        requestUrlSpec =
                new RequestSpecBuilder().
                        setBaseUri("https://reqres.in").
                        build();
    }

    @Test
    public void useRequestSpecTest()
    {
        given().spec(requestUrlSpec).when().get("/api/users/2").then().assertThat().statusCode(200).and().body("data.id", equalTo(2));
    }
}
