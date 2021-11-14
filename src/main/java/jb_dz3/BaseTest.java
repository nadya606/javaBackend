package jb_dz3;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.*;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public abstract class BaseTest {
    private static final String PATH_TO_IMAGE = "/image";
    static Properties properties = new Properties();
    static String token;
    static String username;
    static ResponseSpecification positiveResponseSpecification;
    static RequestSpecification requestSpecificationWithAuth;

    @BeforeAll
    static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        getProperties();
        token = properties.getProperty("token");
        username = properties.getProperty("username");

        requestSpecificationWithAuth = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .build();

        positiveResponseSpecification = new ResponseSpecBuilder()
                .expectBody("status", equalTo(200))
                .expectBody("success", is(true))
//                .expectResponseTime(lessThanOrEqualTo(500L))
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();



    }

    private static void getProperties() {
        try (InputStream output = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getFileContent(String path) {
        byte [] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

}
