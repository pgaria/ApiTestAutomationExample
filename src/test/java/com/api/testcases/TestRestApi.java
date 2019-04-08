package com.api.testcases;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class TestRestApi {

    public final String getTestUserUrl = "https://reqres.in/api/users/2";

    @Test
    public void simpleRestAssuredTestCase() {
        get(getTestUserUrl).then().statusCode(200).and().body("data.id", equalTo(2));
    }

    @Test
    public void simpleRestAssuredWithAsserts() {

        when().
                get(getTestUserUrl).
                then().
                statusCode(200).and()
                .body("data.id", is(2)
                        , "data.first_name", equalTo("Janet")
                        , "data.last_name", equalTo("Weaver")
                        , "data.avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg")
                );
    }

    @Test
    public void simpleRestAssuredWithAssertsPart2() {

        expect().statusCode(200).and()
                .body("data.id", is(2)
                        , "data.first_name", equalTo("Janet")
                        , "data.last_name", equalTo("Weaver")
                        , "data.avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"))

                .when().get(getTestUserUrl);
    }

    //TODO
    @Test
    public void simpleRestAssuredWithJsonFilePath() {
        Response response = get(getTestUserUrl);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void simpleRestAssuredWithSpecificationReuse() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200).rootPath("data")
                .expectBody("id", is(2))
                .expectBody("first_name", equalTo("Janet"))
                .expectBody("last_name", equalTo("Weaver"))
                .expectBody("avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"));
        ResponseSpecification responseSpec = builder.build();

        expect().spec(responseSpec).when().get(getTestUserUrl);
    }

}
