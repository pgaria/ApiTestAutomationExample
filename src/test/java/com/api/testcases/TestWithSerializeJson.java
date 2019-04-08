package com.api.testcases;

import com.api.model.CreateUser;
import com.api.model.CreateUserResponse;
import com.api.model.Data;
import com.api.model.ResponseData;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static io.restassured.RestAssured.given;

public class TestWithSerializeJson {

    @Test
    public void testSerializeJsonTestData()
    {
        Data data = new Data();
                data.setId(2);
                data.setFirstName("Janet");
                data.setLastName("Weaver");
                data.setAvatar("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg");
        ResponseData  responseUserDataExpected = new ResponseData();
        responseUserDataExpected.setData(data);
        //Test
        ResponseData responseUserDataActual = given().when().get("https://reqres.in/api/users/2").as(ResponseData.class);
        //System.out.println(responseUserDataActual.getData().toString());
        Assert.assertEquals(responseUserDataActual.getData().getId(),responseUserDataExpected.getData().getId());
        Assert.assertEquals(responseUserDataActual.getData().getFirstName(),responseUserDataExpected.getData().getFirstName());
    }

    @Test
    public void testCreateUserPostRequest()
    {
        CreateUser createUser = new CreateUser();
        createUser.setName("A1");
        createUser.setJob("leader");

        //given().body(createUser).when().post("https://reqres.in/api/users").then().statusCode(201);
        Response responseBody=   given().body(createUser).when().post("https://reqres.in/api/users");
        System.out.println(responseBody.body().prettyPrint());
        CreateUserResponse createUserResponse = responseBody.getBody().as(CreateUserResponse.class);
        Assert.assertTrue(createUserResponse.getName().equalsIgnoreCase(createUser.getName()));
    }
    @Test
    public void testCreateFromJsonFileUserPostRequest() throws FileNotFoundException {

        //Read the Json Data from File
        CreateUser createUser = new Gson().fromJson(new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\newUserData.json"), CreateUser.class);
        // Create post request
        CreateUserResponse createUserResponse=   given().body(createUser).when().post("https://reqres.in/api/users").as(CreateUserResponse.class);
        System.out.println(createUserResponse);
        Assert.assertTrue(createUserResponse.getName().equalsIgnoreCase(createUser.getName()));
    }


}
