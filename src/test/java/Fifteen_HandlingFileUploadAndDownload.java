import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Fifteen_HandlingFileUploadAndDownload {

    @Test(priority = 1)
    void singleFileUpload()
    {
        File myfile = new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample1.txt");
        given()
                    .multiPart("file", myfile)  //body type for file upload(form-data)
                    .contentType("multipart/form-data")
                .when()
                    .post("http://localhost:8080/uploadFile")// C://uploads -> uploaded file is present in this path
                .then()
                    .statusCode(200)
                    .body("fileName", equalTo("Sample1.txt"))
                    .log().all();
    }

    @Test(priority = 2, dependsOnMethods = "singleFileUpload")
    void fileDownload()
    {
        given()
                .when()
                .get("http://localhost:8080/downloadFile/Sample1.txt")// This URL is copied from the resposne of the file upload API - fileDownloadUri key value. On opening this url in browser, the file will get downloaded
                .then()
                    .statusCode(200)
                    .log().body();
    }

    @Test(priority = 3)
    void MultipleFileUploadApproach1()
    {
        File myfile1 = new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample1.txt");
        File myfile2 = new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample2.txt");

        given()
                    .multiPart("files", myfile1)
                    .multiPart("files", myfile2)
                    .contentType("multipart/form-data")
                .when()
                    .post("http://localhost:8080/uploadMultipleFiles")
                .then()
                    .statusCode(200)
                    .body("[0].fileName", equalTo("Sample1.txt"))
                    .body("[1].fileName", equalTo("Sample2.txt"))
                    .log().all();
    }

   @Test(priority = 4)
    void MultipleFileUploadApproach2() //This test will not work due the capability issue with API
    {
        File myfile1 = new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample1.txt");
        File myfile2 = new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample2.txt");
        File filearr[] = {myfile1, myfile2};  //This approach do not work in this API as wellas in many other APIs

        given()
                    .multiPart("files", filearr)
                    .contentType("multipart/form-data")
                .when()
                    .post("http://localhost:8080/uploadMultipleFiles")
                .then()
                    .statusCode(200) // SInce array approach do not work the validation won't work
                    .body("[0].fileName", equalTo("Sample1.txt"))
                    .body("[1].fileName", equalTo("Sample2.txt"))
                    .log().all();
    }

    @Test(priority = 5)
    void uploadMultipleFiles()
    {
        given()
                .baseUri("http://localhost:8080")
                .basePath("/uploadMultipleFiles")
                .multiPart("files", new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample1.txt"))
                .multiPart("files", new File("C:\\RestAssuredAutomationProjects\\Sample file for upload\\Sample2.txt"))
                .contentType(ContentType.MULTIPART)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("[0].fileName", equalTo("Sample1.txt"))
                .body("[1].fileName", equalTo("Sample2.txt"))
                .log().body();
    }

    @Test(priority = 6, dependsOnMethods = "uploadMultipleFiles")
    void downloadMultipleFiles()
    {
        Map<String, String> filesToDownload = Map.of(
                "Sample1.txt", "http://localhost:8080/downloadFile/Sample1.txt",
                "Sample2.txt", "http://localhost:8080/downloadFile/Sample2.txt"
        );

        for (Map.Entry<String, String> file : filesToDownload.entrySet()) {
            Response response = given()
                    .when()
                    .get(file.getValue())
                    .then()
                    .statusCode(200)
                    .log().all()
                    .extract()
                    .response();

            byte[] fileBytes = response.getBody().asByteArray();

            // Validate that file content is not empty
            assertThat(file.getKey() + " should not be empty", fileBytes.length, greaterThan(0));

            // Optional: validate content-type or content-disposition header
            String contentType = response.getHeader("Content-Type");
            System.out.println(file.getKey() + " content-type: " + contentType);
        }
    }
}
