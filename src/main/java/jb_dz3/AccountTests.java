package jb_dz3;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.digest.UnixCrypt;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
//import static org.apache.commons.codec.digest.UnixCrypt.body;
//import static org.apache.commons.codec.digest.UnixCrypt.body;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class AccountTests extends BaseTest{
    @Test
    void getAccountInfoTest2() {
        given(requestSpecificationWithAuth)
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoTest3() {
        given(requestSpecificationWithAuth, positiveResponseSpecification)
                .get("https://api.imgur.com/3/account/{username}",username)
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoTest() {
        given()
                .header("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .then()
                .statusCode(200);
    }



    @Test
    void getAccountInfoWithLoggingTest() {
        given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoWithAssertionsInGivenTest() {
        given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .expect()
                .body("data.url",equalTo(username))
                .body("success",equalTo(true))
                .body("status",equalTo(200))
                //.statusCode(200);
                //.body("data url",equalTo(username))
                //.body("success",equalTo(true))
                //.body("status",equalTo(200))
                .contentType("application/json")
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .prettyPeek();
    }
    @Test
    void getAccountInfoWithAssertionsAfterTest() {
        Response response = given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/{username}", username)
                .prettyPeek();

        assertThat(response.jsonPath().get("data.url"),equalTo(username));

    }



}



