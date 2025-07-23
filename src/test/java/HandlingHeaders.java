import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class HandlingHeaders {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test(priority = 1)
    void verifyHeader()
    {
        given()
                .when()
                    .get("https://www.google.com/")
                .then()
                    .header("Content-Type", "text/html; charset=ISO-8859-1")
                    .and()
                    .header("content-encoding", "gzip")
                    .and()
                    .header("server", "gws")
                    .log().headers();
    }

    @Test(priority = 2)
    void getSingleHeader()
    {
        Response res = given()
                .when()
                    .get("https://www.google.com/");

        //Get single header info
        String header_value = res.getHeader("Content-Type");
        System.out.println("Value of Content-Type header: " +header_value);
    }

    @Test(priority = 3)
    void getMultipleHeaders()
    {
        Response res = given()
                .when()
                    .get("https://www.google.com/");

        //Get multiple header info
       Headers header_name = res.getHeaders();
       for (Header header : header_name)
       {
           System.out.println("Header name: " +header.getName()+ " | Header value: " +header.getValue());
       }
    }
}
