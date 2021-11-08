package jb_dz3;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import org.junit.jupiter.api.BeforeAll;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public abstract class BaseTest {
    static Properties properties = new Properties();
    static String token;
    static String username;

    @BeforeAll
    static void beforeAll(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        getProperties();
        token = properties.getProperty("token");
        username = properties.getProperty("username");
    }

    private static void getProperties() {
        try (InputStream output = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
