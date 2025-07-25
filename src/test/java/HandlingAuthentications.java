import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HandlingAuthentications {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test(priority = 1)
    void basicAuthentication()  //Basic Authentication using username & password
    {
        given()
                    .auth().basic("postman", "password")  //username = postman, password = password
                .when()
                    .get("https://postman-echo.com/basic-auth")
                .then()
                    .statusCode(200)
                    .body("authenticated", equalTo(true))
                    .log().body();
    }

    @Test(priority = 2)
    void digestAuthentication()  //Digest Authentication using username & password
    {
        given()
                    .auth().digest("postman", "password")  //username = postman, password = password
                .when()
                    .get("https://postman-echo.com/digest-auth")
                .then()
                    .statusCode(200)
                    .body("authenticated", equalTo(true))
                    .log().body();
    }

    @Test(priority = 3)
    void preemptiveAuthentication()  //Preemptive Authentication using username & password
    {
        given()
                    .auth().preemptive().basic("postman", "password")  //username = postman, password = password
                .when()
                    .get("https://postman-echo.com/basic-auth")
                .then()
                    .statusCode(200)
                    .body("authenticated", equalTo(true))
                    .log().all();
    }

    @Test(priority = 4)
    void bearerTokenAuthentication() //Bearer Token authentication using token
    {
        String bearertoken = "test";

        given()
                    .headers("Authorization", "Bearer " +bearertoken)
                .when()
                    .get("https://api.github.com/user/repos")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test(priority = 5)
    void oAuth1Authentication() // oauth1.0 authentication
    {
        given()
                    //The below values are not valid just for understanding given these
                    .auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret")
                .when()
                    .get("url")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test(priority = 6)
    void oAuth2Authentication() // oauth2.0 authentication
    {
        given()
                    .auth().oauth2("test")
                .when()
                    .get("https://api.github.com/user/repos")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    //If You Need to Programmatically Get the Token (e.g., Client Credentials Flow)
    public static String oAuth2TokenGeneratorCode()
    {
        Response tokenResponse = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "your-client-id")
                .formParam("client_secret", "your-client-secret")
                .when()
                .post("https://auth.example.com/oauth2/token");  //Test url, do not work

        return tokenResponse.jsonPath().getString("access_token");
    }

    @Test(priority = 7)
    void apiKeyAuthenticationApproach1() //API Key authentication
    {
        given()
                    .queryParam("appid", "fe9c5cddb7e01d747b4611c3fc9eaf2c") //appid i the API key
                .when()
                    .get("https://api.openweathermap.org/data/2.5/forecast/daily?q=Delhi&units=metric&cnt=7")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test(priority = 8)
    void apiKeyAuthenticationApproach2() //API Key authentication
    {
        given()
                    .queryParam("appid", "fe9c5cddb7e01d747b4611c3fc9eaf2c") //appid i the API key
                    .pathParam("mypath", "data/2.5/forecast/daily")
                    .queryParam("q", "Delhi")
                    .queryParam("units", "metric")
                    .queryParam("cnt", "7")
                .when()
                    .get("https://api.openweathermap.org/{mypath}")
                .then()
                    .statusCode(200)
                    .log().all();
    }

}
