package jb_dz3;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import jb_dz3.dto.ImageResponse;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImageUploadTests extends BaseTest {

    private static final String UPLOAD_IMAGE = "/image";
    static String encodedFile;
    String uploadedImageId;
    MultiPartSpecification base64MultiPartSpec;
    MultiPartSpecification multiPartSpecWithFile;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImage;
    static RequestSpecification requestSpecificationWithAuthWithBase64;


    @BeforeEach
    void beforeTest() {
        String PATH_TO_IMAGE = "src/main/resources/testimage.jpg";
        byte[] byteArray = getFileContent(PATH_TO_IMAGE);
        encodedFile = Base64.getEncoder().encodeToString(byteArray);

        base64MultiPartSpec = new MultiPartSpecBuilder(encodedFile)
                .controlName("image")
                .build();

        multiPartSpecWithFile = new MultiPartSpecBuilder(new File("src/main/resources/testimage.jpg"))
                .controlName("image")
                .build();

        requestSpecificationWithAuthAndMultipartImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "image")
                .addFormParam("type", "jpg")
                .addMultiPart(multiPartSpecWithFile)
                .build();

        requestSpecificationWithAuthWithBase64 = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addMultiPart(base64MultiPartSpec)
                .build();
    }

    @Test
    void uploadFileTest() {
        uploadedImageId = given(requestSpecificationWithAuthWithBase64, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getDeletehash();
    }

    @Test
    void uploadFileTestWithPath() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage)
                .expect()
                .statusCode(200)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                //.jsonPath()
                .getData().getDeletehash();
    }

    @Test // Проверка на загрузку файла
    void uploadFile() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage)
                .expect()
                .statusCode(200)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getDeletehash();

    }

    @Test
    void uploadFileTestName() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getDeletehash();

        }
    @Test //Загрузка без файла
    void uploadFileWithoutFile() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage)
                .expect()
                .statusCode(200)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getDeletehash();
    }
    @Test //Проверка на корректность типа файла
    void uploadFileTestType() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(ImageResponse.class)
                .getData().getDeletehash();
    }

    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", "testprogmath", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}
